package com.pingok.devicemonitor.service.smartToilet.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.smartToilet.TblSmartToiletCubicle;
import com.pingok.devicemonitor.domain.smartToilet.TblSmartToiletHealth;
import com.pingok.devicemonitor.domain.smartToilet.TblSmartToiletInfo;
import com.pingok.devicemonitor.domain.smartToilet.model.ToiletSensorData;
import com.pingok.devicemonitor.domain.smartToilet.model.ToiletSensorData_cubicle;
import com.pingok.devicemonitor.domain.smartToilet.model.ToiletSensorInfo;
import com.pingok.devicemonitor.mapper.smartToilet.TblSmartToiletCubicleMapper;
import com.pingok.devicemonitor.mapper.smartToilet.TblSmartToiletHealthMapper;
import com.pingok.devicemonitor.mapper.smartToilet.TblSmartToiletInfoMapper;
import com.pingok.devicemonitor.service.smartToilet.ISmartToiletService;
import com.ruoyi.common.core.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author
 * @time 2022/4/13 16:32
 */
@Slf4j
@Service
public class SmartToiletServiceImpl implements ISmartToiletService {

    @Autowired
    private TblSmartToiletInfoMapper tblSmartToiletInfoMapper;
    @Autowired
    private TblSmartToiletHealthMapper tblSmartToiletHealthMapper;
    @Autowired
    private TblSmartToiletCubicleMapper tblSmartToiletCubicleMapper;

    /**
     * 处理厕所传感器数据
     *
     * @param sensorData 厕所传感器数据
     */
    @Override
    public void sensorData(JSONObject sensorData) {
        ToiletSensorInfo toiletData = JSON.parseObject(JSON.toJSONString(sensorData), ToiletSensorInfo.class);
        if (toiletData != null) {
            ToiletSensorData sensor = toiletData.getSensor();
            // 1,查询传感器信息；2，存储健康状态（上报数据）；3，存储占用情况、报警状态
            TblSmartToiletInfo toiletInfo = getToiletInfoBySernum(toiletData.getSer_num());
            if (toiletInfo != null) {
                TblSmartToiletHealth healthInfo = getHealthInfoBySerid(toiletInfo.getId());
                switch (sensor.getType()) {
                    case "idtk": {
                        log.info("idtk-----" + JSON.toJSONString(sensor));
//                        toiletInfo.setRateIn(sensor.getIdtk().getRate_in());
//                        toiletInfo.setRateOut(sensor.getIdtk().getRate_out());
//                        if (2 == sensor.getIdtk().getFocus()) {
//                            toiletInfo.setStatus(0);
//                            addStatusDesc(toiletInfo.getStatusDesc(), "聚焦状态异常");
//                        }
//                        toiletInfo.setUpdateTime(DateTime.now());
//                        tblSmartToiletInfoMapper.updateByPrimaryKeySelective(toiletInfo);
                        break;
                    }
                    case "nh3": {
                        healthInfo.setNh3(sensor.getNh3());
                        tblSmartToiletHealthMapper.updateByPrimaryKeySelective(healthInfo);
                        break;
                    }
                    case "h2s": {
                        healthInfo.setH2s(sensor.getH2s());
                        tblSmartToiletHealthMapper.updateByPrimaryKeySelective(healthInfo);
                        break;
                    }
                    case "hum": {
                        healthInfo.setHum(sensor.getHum());
                        tblSmartToiletHealthMapper.updateByPrimaryKeySelective(healthInfo);
                        break;
                    }
                    case "temp": {
                        healthInfo.setTemp(sensor.getTemp());
                        tblSmartToiletHealthMapper.updateByPrimaryKeySelective(healthInfo);
                        break;
                    }
                    case "co2": {
                        healthInfo.setCo2(sensor.getCo2().getValue());
                        tblSmartToiletHealthMapper.updateByPrimaryKeySelective(healthInfo);
                        break;
                    }
                    case "pm25": {
                        healthInfo.setPm25(sensor.getPm25());
                        tblSmartToiletHealthMapper.updateByPrimaryKeySelective(healthInfo);
                        break;
                    }
                    case "voc": {
                        healthInfo.setVoc(sensor.getVoc());
                        tblSmartToiletHealthMapper.updateByPrimaryKeySelective(healthInfo);
                        break;
                    }
                    case "cubicle": {
                        List<ToiletSensorData_cubicle> cubicles = sensor.getCubicles();
                        int count = (int) cubicles.stream().filter(q -> q.getStatus() == 0).count();
                        toiletInfo.setSurplus(count);
                        for (ToiletSensorData_cubicle c : cubicles) {
                            updateCubicleInfo(toiletInfo.getId(), c);
                        }
                        break;
                    }
                    case "alarm": {
                        log.info("alarm-----" + JSON.toJSONString(sensor));
                        break;
                    }
                    case "smk_alarm": {
                        healthInfo.setSmkAlarm(sensor.getSmk_alarm().getStatus());
                        tblSmartToiletHealthMapper.updateByPrimaryKeySelective(healthInfo);
                        break;
                    }
                    case "wm": {
                        healthInfo.setWm(sensor.getWm().getValue());
                        tblSmartToiletHealthMapper.updateByPrimaryKeySelective(healthInfo);
                        break;
                    }
                    case "em": {
                        healthInfo.setEm(sensor.getEm().getValue());
                        tblSmartToiletHealthMapper.updateByPrimaryKeySelective(healthInfo);
                        break;
                    }
                    case "pm": {
                        healthInfo.setPm(sensor.getPm().getValue());
                        tblSmartToiletHealthMapper.updateByPrimaryKeySelective(healthInfo);
                        break;
                    }
                    case "evl": {
                        healthInfo.setEvl(JSON.toJSONString(sensor.getEvl()));
                        tblSmartToiletHealthMapper.updateByPrimaryKeySelective(healthInfo);
                        break;
                    }
                }
            } else {
                log.error("未匹配到厕所序列号：" + toiletData.getSer_num());
            }
        } else {
            throw new ServiceException("厕所传感器报文解析异常");
        }
    }

    /**
     * 根据厕所序列号查询厕所信息
     *
     * @param serNum 厕所序列号
     * @return 厕所信息
     */
    private TblSmartToiletInfo getToiletInfoBySernum(String serNum) {
        Example example = new Example(TblSmartToiletInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("serNum", serNum);
        TblSmartToiletInfo tblSmartToiletInfo = tblSmartToiletInfoMapper.selectOneByExample(example);
        return tblSmartToiletInfo;
    }

    /**
     * 根据厕所ID查询厕所健康状态
     *
     * @param serId 厕所ID
     * @return 厕所健康状态
     */
    private TblSmartToiletHealth getHealthInfoBySerid(Long serId) {
        Example example = new Example(TblSmartToiletHealth.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("serId", serId);
        TblSmartToiletHealth tblSmartToiletHealth = tblSmartToiletHealthMapper.selectOneByExample(example);
        return tblSmartToiletHealth;
    }

    /**
     * 根据厕所ID，坑位id查询厕所坑位状态
     *
     * @param serId   厕所序号
     * @param cubicle 坑位信息
     * @return 厕所健康状态
     */
    private void updateCubicleInfo(Long serId, ToiletSensorData_cubicle cubicle) {
        Example example = new Example(TblSmartToiletCubicle.class);
        example.createCriteria().andEqualTo("serId", serId).andEqualTo("indexId", cubicle.getIndex());
        TblSmartToiletCubicle tblSmartToiletCubicle = tblSmartToiletCubicleMapper.selectOneByExample(example);
        tblSmartToiletCubicle.setStatus(cubicle.getStatus());
        tblSmartToiletCubicleMapper.updateByPrimaryKeySelective(tblSmartToiletCubicle);
    }

    /**
     * 更新厕所信息中的 statusDesc
     *
     * @param desc
     * @param addString
     */
    private void addStatusDesc(String desc, String addString) {
        if (!desc.contains(addString)) desc.concat(";").concat(addString);
    }
}
