package com.pingok.monitor.service.videoEvent.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.pingok.monitor.config.HostConfig;
import com.pingok.monitor.domain.event.*;
import com.pingok.monitor.mapper.event.*;
import com.pingok.monitor.service.videoEvent.IVideoEventService;
import com.pingok.monitor.service.videoEvent.IVideoService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author
 * @time 2022/5/6 15:56
 */
@Slf4j
@Service
public class VideoEventServiceImpl implements IVideoEventService {

    @Autowired
    private TblEventFluxMapper tblEventFluxMapper;
    @Autowired
    private TblEventParkingEventMapper tblEventParkingEventMapper;
    @Autowired
    private TblEventPassengerFlowMapper tblEventPassengerFlowMapper;
    @Autowired
    private TblEventVehicleEventMapper tblEventVehicleEventMapper;
    @Autowired
    private TblEventPlateInfoMapper tblEventPlateInfoMapper;
    @Autowired
    private IVideoService iVideoService;


    @Override
    public void fluxData(TblEventFlux tblEventFlux) {
        tblEventFluxMapper.insert(tblEventFlux);
    }


    @Override
    public void plateInfo(TblEventPlateInfo tblEventPlateInfo) {
        tblEventPlateInfoMapper.insert(tblEventPlateInfo);
    }


    @Override
    public void vehicleEvent(TblEventVehicleEvent tblEventVehicleEvent) {
        tblEventVehicleEventMapper.insert(tblEventVehicleEvent);
    }


    @Override
    public void passengerFlow(TblEventPassengerFlow tblEventPassengerFlow) {
        tblEventPassengerFlowMapper.insert(tblEventPassengerFlow);
    }


    @Override
    public void parkingEvent(TblEventParkingEvent tblEventParkingEvent) {
        tblEventParkingEventMapper.insert(tblEventParkingEvent);
    }

    @Override
    public void updateEventVideo(Long ubiLogicId) {
        String url = iVideoService.getEventVideoById(ubiLogicId, 2, 2);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ubiLogicId", ubiLogicId);
        paramMap.put("url", url);
        try {
            String post = HttpUtil.post(HostConfig.DASSHOST + "/event/eventControl/updateEventVideo", paramMap);
            if (!StringUtils.isEmpty(post)) {
                if (post.startsWith("{")) {
                    R ret = JSON.parseObject(post, R.class);
                    if (R.FAIL == ret.getCode()) {
                        log.error(ubiLogicId + " 事件视频上传失败：" + ret.getMsg());
                    }
                } else {
                    log.error(ubiLogicId + " 事件视频上传状态未知");
                }
            }
        } catch (Exception e) {
            log.error(ubiLogicId + " 事件视频上传失败：" + e.getMessage());
        }

    }

    @Async
    @Override
    public void updateFluxData(TblEventFlux tblEventFlux) {
        int time = 1;
        while (true) {
            try {
                String post = HttpUtil.post(HostConfig.DASSHOST + "/event/eventControl/flux", JSON.toJSONString(tblEventFlux));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        R ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(tblEventFlux.getUbiLogicId() + " 流量统计上报失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(tblEventFlux.getUbiLogicId() + " 流量统计上报状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (Exception e) {
                log.error(tblEventFlux.getUbiLogicId() + " 流量统计上报失败：" + e.getMessage());
            }
            time += 2;
        }
        tblEventFluxMapper.delete(tblEventFlux);
    }

    @Async
    @Override
    public void updatePlateInfo(TblEventPlateInfo tblEventPlateInfo) {
        int time = 1;
        while (true) {
            try {
                String post = HttpUtil.post(HostConfig.DASSHOST + "/event/eventControl/plateInfo", JSON.toJSONString(tblEventPlateInfo));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        R ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(tblEventPlateInfo.getUbiLogicId() + " 过车数据上报失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(tblEventPlateInfo.getUbiLogicId() + " 过车数据上报状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (Exception e) {
                log.error(tblEventPlateInfo.getUbiLogicId() + " 过车数据上报失败：" + e.getMessage());
            }
            time += 2;
        }
        tblEventPlateInfoMapper.delete(tblEventPlateInfo);
    }

    @Async
    @Override
    public void updateVehicleEvent(TblEventVehicleEvent tblEventVehicleEvent) {
        int time = 1;
        String post;
        while (true) {
            try {
                post = HttpUtil.post(HostConfig.DASSHOST + "/event/eventControl/vehicleEvent", JSON.toJSONString(tblEventVehicleEvent));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        R ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(tblEventVehicleEvent.getUbiLogicId() + " 交通事件上报失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(tblEventVehicleEvent.getUbiLogicId() + " 交通事件上报状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (Exception e) {
                log.error(tblEventVehicleEvent.getUbiLogicId() + " 交通事件上报失败：" + e.getMessage());
            }
            time += 2;
        }

        tblEventVehicleEventMapper.delete(tblEventVehicleEvent);

        updateEventVideo(tblEventVehicleEvent.getUbiLogicId());
    }

    @Async
    @Override
    public void updatePassengerFlow(TblEventPassengerFlow tblEventPassengerFlow) {
        int time = 1;
        while (true) {
            try {
                String post = HttpUtil.post(HostConfig.DASSHOST + "/event/eventControl/passengerFlow", JSON.toJSONString(tblEventPassengerFlow));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        R ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(tblEventPassengerFlow.getUiType() + " 客流量上报失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(tblEventPassengerFlow.getUiType() + " 客流量上报状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (Exception e) {
                log.error(tblEventPassengerFlow.getUiType() + " 客流量上报失败：" + e.getMessage());
            }
            time += 2;
        }
        tblEventPassengerFlowMapper.delete(tblEventPassengerFlow);
    }

    @Async
    @Override
    public void updateParkingEvent(TblEventParkingEvent tblEventParkingEvent) {
        int time = 1;
        while (true) {
            try {
                String post = HttpUtil.post(HostConfig.DASSHOST + "/event/eventControl/parkingEvent", JSON.toJSONString(tblEventParkingEvent));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        R ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(tblEventParkingEvent.getUbiLogicId() + " 货车检查事件上报失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(tblEventParkingEvent.getUbiLogicId() + " 货车检查事件上报状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (Exception e) {
                log.error(tblEventParkingEvent.getUbiLogicId() + " 货车检查事件上报失败：" + e.getMessage());
            }
            time += 2;
        }
        tblEventParkingEventMapper.delete(tblEventParkingEvent);
    }

}
