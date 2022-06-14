package com.pingok.algorithm.event.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.algorithm.event.domain.StationLineRecordDto;
import com.pingok.algorithm.event.entity.TblAlgEvent;
import com.pingok.algorithm.event.entity.TblAlgEventEffectStatus;
import com.pingok.algorithm.event.mapper.TblAlgEventEffectStatusMapper;
import com.pingok.algorithm.event.service.TblAlgEventEffectStatusService;
import com.pingok.algorithm.event.service.TblAlgEventService;
import com.pingok.algorithm.trafficStatus.mapper.TblAlgTrafficStatusSpeedMapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteEventService;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.domain.algorithm.LaneAvgSpeed;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TblAlgEventEffectStatusServiceImpl implements TblAlgEventEffectStatusService {

    @Resource
    private TblAlgEventEffectStatusMapper algEventEffectStatusMapper;

    @Resource
    private TblAlgEventService algEventService;

    @Resource
    private TblAlgTrafficStatusSpeedMapper algTrafficStatusSpeedMapper;

    @Resource
    private RemoteIdProducerService remoteIdProducerService;

    @Resource
    private RemoteEventService remoteEventService;

    /**
     * 查询事件影响状态列表
     * @param tblAlgEventEffectStatus
     * @return
     */
    @Override
    public List<TblAlgEventEffectStatus> listByBean(TblAlgEventEffectStatus tblAlgEventEffectStatus){
        return algEventEffectStatusMapper.select(tblAlgEventEffectStatus);
    }

    /**
     * 查询最新事件影响状态列表
     * @param tblAlgEventEffectStatus
     * @return
     */
    @Override
    public List<TblAlgEventEffectStatus> selectEventEffectStatusListByBean(TblAlgEventEffectStatus tblAlgEventEffectStatus){
        return algEventEffectStatusMapper.selectEventEffectStatusListByBean(tblAlgEventEffectStatus);
    }

    /**
     * 查询事件影响状态详情
     * @param tblAlgEventEffectStatus
     * @return
     */
    @Override
    public TblAlgEventEffectStatus selectByBean(TblAlgEventEffectStatus tblAlgEventEffectStatus){
        return algEventEffectStatusMapper.selectOne(tblAlgEventEffectStatus);
    }

    /**
     * 保存事件影响状态记录
     *
     * @param tblAlgEventEffectStatus
     * @return Boolean
     */
    @Override
    public Boolean saveTblAlgEventEffectStatus(TblAlgEventEffectStatus tblAlgEventEffectStatus){
        tblAlgEventEffectStatus.setId(remoteIdProducerService.nextId());
        algEventEffectStatusMapper.insert(tblAlgEventEffectStatus);
        return true;
    }

    /**
     * 自动保存事件影响状态记录
     *
     * @return Boolean
     */
    @Override
    public Boolean autoSaveEventEffectStatus(){
        // 获取未结束的事件记录
        List<TblAlgEvent> eventList = algEventService.selectNoFinishEventList();
        // 当前时间
        String currentTimeStr = DateUtils.getTime();
        Date currentDate = new Date();
        TblAlgEventEffectStatus tempEventEffectStatus = null;
        for (TblAlgEvent tblAlgEvent : eventList) {
            tempEventEffectStatus = new TblAlgEventEffectStatus();
            // 事件已经开始
            if (DateUtils.compareTo(currentTimeStr, tblAlgEvent.getStartTime())){
                // 更新事件执行中
                tblAlgEvent.setStatus(1);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("mileage", tblAlgEvent.getStationNum());
                jsonObject.put("laneNo", tblAlgEvent.getLineNum());
                jsonObject.put("dir", tblAlgEvent.getTrafficDirection());
                R<List<LaneAvgSpeed>> result = remoteEventService.getLaneAvgSpeed(jsonObject);
                if (result == null){
                    continue;
                }
                // 根据桩号、车道号查询车道平均速度
                List<LaneAvgSpeed> laneAvgSpeedList = result.getData();
                // 根据事件id查询第一条影响状态记录
                TblAlgEventEffectStatus firstEventEffectStatus = algEventEffectStatusMapper.getFirstEventEffectStatusByEventId(tblAlgEvent.getId());
                // 产生影响
                LaneAvgSpeed laneAvgSpeed = getStationLineRecord(laneAvgSpeedList, firstEventEffectStatus);
                // 速度变化率
                double speedRate = 0.0;
                if (laneAvgSpeed != null && laneAvgSpeed.getIsEffect() == 1){
                    // 根据事件id查询最新影响状态记录
                    TblAlgEventEffectStatus latestEventEffectStatus = algEventEffectStatusMapper.getLatestEventEffectStatusByEventId(tblAlgEvent.getId());
                    // 计算速度变化率
                    speedRate = (Double.parseDouble(latestEventEffectStatus.getLineSpeed()) - Double.parseDouble(laneAvgSpeed.getLineSpeed())) / 2;
                    // 获取该限速下轻度拥堵、中度拥堵、重度拥堵速度
                    int mildCongSpeed = algTrafficStatusSpeedMapper.getMaxSpeed(laneAvgSpeed.getLimitSpeed(), 1);
                    int medCongSpeed = algTrafficStatusSpeedMapper.getMaxSpeed(laneAvgSpeed.getLimitSpeed(), 2);
                    int sevCongSpeed = algTrafficStatusSpeedMapper.getMaxSpeed(laneAvgSpeed.getLimitSpeed(), 3);
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    // 如果当前速度低于轻度拥堵速度
                    if (Double.parseDouble(laneAvgSpeed.getLineSpeed()) <= mildCongSpeed){
                        tempEventEffectStatus.setMildCongTime("0");
                    } else {
                        // 车道速度降低到轻度拥堵速度的时长（min）
                        int mildCongDuration = new BigDecimal((Double.parseDouble(laneAvgSpeed.getLineSpeed()) - mildCongSpeed) / speedRate).setScale(0, RoundingMode.CEILING).intValue();
                        calendar.setTime(currentDate);
                        calendar.add(Calendar.MINUTE, mildCongDuration);
                        String mildCongTime = simpleDateFormat.format(calendar.getTime());
                        tempEventEffectStatus.setMildCongTime(mildCongTime);
                    }
                    if (Double.parseDouble(laneAvgSpeed.getLineSpeed()) <= medCongSpeed){
                        tempEventEffectStatus.setMedCongTime("0");
                    } else {
                        // 车道速度降低到中度拥堵速度的时长（min）
                        int medCongDuration = new BigDecimal((Double.parseDouble(laneAvgSpeed.getLineSpeed()) - medCongSpeed) / speedRate).setScale(0, RoundingMode.CEILING).intValue();
                        calendar.setTime(currentDate);
                        calendar.add(Calendar.MINUTE, medCongDuration);
                        String medCongTime = simpleDateFormat.format(calendar.getTime());
                        tempEventEffectStatus.setMedCongTime(medCongTime);
                    }
                    if (Double.parseDouble(laneAvgSpeed.getLineSpeed()) <= sevCongSpeed){
                        tempEventEffectStatus.setSevCongTime("0");
                    } else {
                        // 车道速度降低到重度拥堵速度的时长（min）
                        int sevCongDuration = new BigDecimal((Double.parseDouble(laneAvgSpeed.getLineSpeed()) - sevCongSpeed) / speedRate).setScale(0, RoundingMode.CEILING).intValue();
                        calendar.setTime(currentDate);
                        calendar.add(Calendar.MINUTE, sevCongDuration);
                        String sevCongTime = simpleDateFormat.format(calendar.getTime());
                        tempEventEffectStatus.setSevCongTime(sevCongTime);
                    }
                }
                tempEventEffectStatus.setId(remoteIdProducerService.nextId());
                tempEventEffectStatus.setEventId(tblAlgEvent.getId());
                tempEventEffectStatus.setLineSpeed(laneAvgSpeed != null? laneAvgSpeed.getLineSpeed(): null);
                tempEventEffectStatus.setSampleTime(currentTimeStr);
                tempEventEffectStatus.setEffectFlg(laneAvgSpeed != null? laneAvgSpeed.getIsEffect(): null);
                tempEventEffectStatus.setCreateTime(currentDate);
                algEventEffectStatusMapper.insert(tempEventEffectStatus);

                // 已经达到结束时间
                if (StringUtils.isNotEmpty(tblAlgEvent.getEndTime()) && DateUtils.compareTo(currentTimeStr, tblAlgEvent.getEndTime())){
                    // 到达结束时间后，速度连续上升5次，或者采样次数超过60次，事件结束
                    tblAlgEvent.setSampleNum(tblAlgEvent.getSampleNum() + 1);
                    tblAlgEvent.setRiseNum(speedRate < 0? (tblAlgEvent.getRiseNum() + 1): 0);
                    if (tblAlgEvent.getSampleNum() >= 60 || tblAlgEvent.getRiseNum() >= 5){
                        tblAlgEvent.setStatus(2);
                    }
                }
            }
            algEventService.modifyTblAlgEvent(tblAlgEvent);
        }
        return true;
    }

    /**
     * 获取车道影响状态结果
     * @param laneAvgSpeedList 车道数据
     * @param tblAlgEventEffectStatus 第一次采样记录
     * @return
     */
    public LaneAvgSpeed getStationLineRecord(List<LaneAvgSpeed> laneAvgSpeedList, TblAlgEventEffectStatus tblAlgEventEffectStatus){
        if (laneAvgSpeedList == null || laneAvgSpeedList.size() == 0 || laneAvgSpeedList.size() == 1){
            return null;
        }
        // 当前车道速度
        Double currentLineSpeed = null;
        // 相邻车道速度
        List<Double> adjacentLineSpeed = new ArrayList<>();
        LaneAvgSpeed recordDto = new LaneAvgSpeed();
        for (LaneAvgSpeed laneAvgSpeed : laneAvgSpeedList) {
            if (laneAvgSpeed.getLineType() == 0){
                currentLineSpeed = Double.parseDouble(laneAvgSpeed.getLineSpeed());
                recordDto = laneAvgSpeed;
            } else {
                adjacentLineSpeed.add(Double.parseDouble(laneAvgSpeed.getLineSpeed()));
            }
        }
        if (currentLineSpeed == null){
            return null;
        }
        // 相比Ts时刻车道速度下降值超过15km/h
        if (tblAlgEventEffectStatus != null && (Double.parseDouble(tblAlgEventEffectStatus.getLineSpeed()) - currentLineSpeed > 15)){
            recordDto.setIsEffect(1);
        }
        // 当前一条相邻车道
        if (adjacentLineSpeed.size() == 1 && currentLineSpeed < adjacentLineSpeed.get(0)){
            recordDto.setIsEffect(1);
        }
        // 当前两条相邻车道
        if (adjacentLineSpeed.size() == 2 && currentLineSpeed < adjacentLineSpeed.get(0) && currentLineSpeed < adjacentLineSpeed.get(1)){
            recordDto.setIsEffect(1);
        }
        return recordDto;
    }

    /**
     * 修改事件影响状态记录
     *
     * @param tblAlgEventEffectStatus
     * @return Boolean
     */
    @Override
    public Boolean modifyTblAlgEventEffectStatus(TblAlgEventEffectStatus tblAlgEventEffectStatus){
        algEventEffectStatusMapper.updateByPrimaryKey(tblAlgEventEffectStatus);
        return true;
    }

    /**
     * 删除事件影响状态记录
     *
     * @param id
     * @return Boolean
     */
    @Override
    public Boolean deleteTblAlgEventEffectStatus(Long id){
        algEventEffectStatusMapper.deleteByPrimaryKey(id);
        return true;
    }
}
