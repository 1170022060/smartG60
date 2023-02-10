package com.pingok.devicemonitor.service.smartToilet.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import com.pingok.devicemonitor.mapper.smartToilet.WeatherMapper;
import com.pingok.devicemonitor.service.smartToilet.ISmartToiletService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

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
    @Autowired
    private RemoteKafkaService remoteKafkaService;

    @Autowired
    private WeatherMapper weatherMapper;

    @Override
    public void setSensor(String serNum, Integer index, Integer status) {
        JSONArray array = new JSONArray();
        JSONObject object;
        JSONObject payload;
        object = new JSONObject();
        object.put("message_id", DateUtils.getNowTimestampLong());
        object.put("ser_num", serNum);
        object.put("command_id", "set_sensor");
        payload = new JSONObject();
        payload.put("type", "cubicle");
        payload.put("index", index);
        Integer value = 0;
        switch (status) {
            case 0:
                value = -2;
                break;
            case 3:
                value = 2;
                break;
        }
        payload.put("value", value);
        object.put("payload", payload.toJSONString());
        array.add(object);
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SMART_TOILET);
        kafkaEnum.setData(array.toJSONString());
        remoteKafkaService.send(kafkaEnum);
    }

    @Override
    public void marqueeText() {
        List<TblSmartToiletInfo> list = tblSmartToiletInfoMapper.selectAll();
        if (list != null && list.size() > 0) {
            JSONArray array = new JSONArray();
            JSONObject object;
            JSONObject payload;
            JSONArray texts;
            JSONObject text;
            Map schedule;
            for (TblSmartToiletInfo i : list) {
                object = new JSONObject();
                object.put("message_id", DateUtils.getNowTimestampLong());
                object.put("ser_num", i.getSerNum());
                object.put("command_id", "marquee_text");

                schedule = tblSmartToiletInfoMapper.schedule(i.getId());
                texts = new JSONArray();
                if (schedule != null) {
                    text = new JSONObject();
                    text.put("id", schedule.get("id"));
                    text.put("content", schedule.get("content"));
                    texts.add(text);
                }
                payload = new JSONObject();
                payload.put("texts", texts);
                object.put("payload", payload.toJSONString());
                array.add(object);
            }
            KafkaEnum kafkaEnum = new KafkaEnum();
            kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SMART_TOILET);
            kafkaEnum.setData(array.toJSONString());
            remoteKafkaService.send(kafkaEnum);
        }
    }

    @Override
    public void weather() {
        Object weather = weatherMapper.weather(DateUtils.getDate());
        if (StringUtils.isNotNull(weather)) {
            List<TblSmartToiletInfo> list = tblSmartToiletInfoMapper.selectAll();
            if (list != null && list.size() > 0) {
                JSONArray array = new JSONArray();
                JSONObject object;
                JSONObject payload;
                for (TblSmartToiletInfo i : list) {
                    object = new JSONObject();
                    object.put("message_id", DateUtils.getNowTimestampLong());
                    object.put("ser_num", i.getSerNum());
                    object.put("command_id", "weather");
                    payload = new JSONObject();
                    payload.put("weather", weather);
                    object.put("payload", payload.toJSONString());
                    array.add(object);
                }
                KafkaEnum kafkaEnum = new KafkaEnum();
                kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SMART_TOILET);
                kafkaEnum.setData(array.toJSONString());
                remoteKafkaService.send(kafkaEnum);
            }
        }
    }

    @Override
    public void timeCalibration() {
        List<TblSmartToiletInfo> list = tblSmartToiletInfoMapper.selectAll();
        if (list != null && list.size() > 0) {
            JSONArray array = new JSONArray();
            JSONObject object;
            JSONObject payload;
            for (TblSmartToiletInfo i : list) {
                object = new JSONObject();
                object.put("message_id", DateUtils.getNowTimestampLong());
                object.put("ser_num", i.getSerNum());
                object.put("command_id", "time_calibration");
                payload = new JSONObject();
                payload.put("time_stamp", DateUtils.getNowShortTimestamp());
                object.put("payload", payload.toJSONString());
                array.add(object);
            }
            KafkaEnum kafkaEnum = new KafkaEnum();
            kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SMART_TOILET);
            kafkaEnum.setData(array.toJSONString());
            remoteKafkaService.send(kafkaEnum);
        }
    }

    /**
     * 处理厕所传感器数据
     *
     * @param sensorData 厕所传感器数据
     */
    @Override
    public void sensorData(JSONObject sensorData) {
        log.info("智慧厕所系统上报数据-----" + sensorData.toJSONString());
        TblSmartToiletInfo toiletInfo;
        TblSmartToiletHealth healthInfo;
        ToiletSensorInfo toiletData = JSON.parseObject(JSON.toJSONString(sensorData), ToiletSensorInfo.class);
        if (toiletData != null) {
            ToiletSensorData sensor = toiletData.getSensor();
            // 1,查询传感器信息；2，存储健康状态（上报数据）；3，存储占用情况、报警状态
            if (sensor.getType().equals("cubicle")) {
                List<ToiletSensorData_cubicle> cubicles = sensor.getCubicles();
                toiletInfo = getToiletInfoByserNumAndSerType(toiletData.getSer_num(), cubicles.get(0).getGender());
                for (ToiletSensorData_cubicle c : cubicles) {
                    updateCubicleInfo(toiletInfo.getId(), c);
                }
                updateToiletInfoSurplus(toiletInfo.getId());
            } else {
                toiletInfo = getToiletInfoByserNumAndSerType(toiletData.getSer_num(), sensor.getIndex());
                if (toiletInfo != null) {
                    healthInfo = getHealthInfoBySerid(toiletInfo.getId());
                    switch (sensor.getType()) {
                        case "idtk": {
//                        toiletInfo.setRateIn(sensor.getIdtk().getRate_in());
//                        toiletInfo.setRateOut(sensor.getIdtk().getRate_out());
//                        if (2 == sensor.getIdtk().getFocus()) {
//                            toiletInfo.setStatus(0);
//                            addStatusDesc(toiletInfo.getStatusDesc(), "聚焦状态异常");
//                        }
//                        toiletInfo.setUpdateTime(DateTime.now());
//                        tblSmartToiletInfoMapper.updateByPrimaryKey(toiletInfo);
                            break;
                        }
                        case "nh3": {
                            healthInfo.setNh3(sensor.getNh3());
                            tblSmartToiletHealthMapper.updateByPrimaryKey(healthInfo);
                            break;
                        }
                        case "h2s": {
                            healthInfo.setH2s(sensor.getH2s());
                            tblSmartToiletHealthMapper.updateByPrimaryKey(healthInfo);
                            break;
                        }
                        case "hum": {
                            healthInfo.setHum(sensor.getHum());
                            tblSmartToiletHealthMapper.updateByPrimaryKey(healthInfo);
                            break;
                        }
                        case "temp": {
                            healthInfo.setTemp(sensor.getTemp());
                            tblSmartToiletHealthMapper.updateByPrimaryKey(healthInfo);
                            break;
                        }
                        case "co2": {
                            healthInfo.setCo2(sensor.getCo2());
                            tblSmartToiletHealthMapper.updateByPrimaryKey(healthInfo);
                            break;
                        }
                        case "pm25": {
                            healthInfo.setPm25(sensor.getPm25());
                            tblSmartToiletHealthMapper.updateByPrimaryKey(healthInfo);
                            break;
                        }
                        case "voc": {
                            healthInfo.setVoc(sensor.getVoc());
                            tblSmartToiletHealthMapper.updateByPrimaryKey(healthInfo);
                            break;
                        }
                        case "alarm": {
                            break;
                        }
                        case "smk_alarm": {
                            healthInfo.setSmkAlarm(sensor.getSmk_alarm().getStatus());
                            tblSmartToiletHealthMapper.updateByPrimaryKey(healthInfo);
                            break;
                        }
                        case "wm": {
                            healthInfo.setWm(sensor.getWm().getValue());
                            tblSmartToiletHealthMapper.updateByPrimaryKey(healthInfo);
                            break;
                        }
                        case "em": {
                            healthInfo.setEm(sensor.getEm().getValue());
                            tblSmartToiletHealthMapper.updateByPrimaryKey(healthInfo);
                            break;
                        }
                        case "pm": {
                            healthInfo.setPm(sensor.getPm().getValue());
                            tblSmartToiletHealthMapper.updateByPrimaryKey(healthInfo);
                            break;
                        }
                        case "evl": {
                            healthInfo.setEvl(JSON.toJSONString(sensor.getEvl()));
                            tblSmartToiletHealthMapper.updateByPrimaryKey(healthInfo);
                            break;
                        }
                    }
                } else {
                    log.error("未匹配到厕所序列号：" + toiletData.getSer_num()+"传感器编号:"+sensor.getIndex()+"类型:"+sensor.getType());
                }
            }
        } else {
            throw new ServiceException("厕所传感器报文解析异常");
        }
    }


    private void updateToiletInfoSurplus(Long id) {
        Example example = new Example(TblSmartToiletCubicle.class);
        example.createCriteria().andEqualTo("serId", id)
                .andEqualTo("status", 0);
        List<TblSmartToiletCubicle> list = tblSmartToiletCubicleMapper.selectByExample(example);
        if (list != null) {
            TblSmartToiletInfo info = tblSmartToiletInfoMapper.selectByPrimaryKey(id);
            if (info != null) {
                info.setSurplus(list.size());
                tblSmartToiletInfoMapper.updateByPrimaryKey(info);
            }
        }
    }

    /**
     * 根据厕所序列号查询厕所信息
     *
     * @param serNum 厕所序列号
     * @return 厕所信息
     */
    private TblSmartToiletInfo getToiletInfoByserNumAndSerType(String serNum, Integer serType) {
        Example example = new Example(TblSmartToiletInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("serNum", serNum);
        criteria.andEqualTo("serType", serType);
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
        if (tblSmartToiletCubicle != null && tblSmartToiletCubicle.getStatus() != 3) {
            tblSmartToiletCubicle.setUpdateTime(DateUtils.getNowDate());
            tblSmartToiletCubicle.setStatus(cubicle.getStatus());
            tblSmartToiletCubicleMapper.updateByPrimaryKey(tblSmartToiletCubicle);
        }
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

    @Override
    public TblSmartToiletCubicle updateToiletStatus(TblSmartToiletCubicle tblSmartToiletCubicle) {
        TblSmartToiletCubicle tblSmartToiletCubicle1 = tblSmartToiletCubicleMapper.selectByPrimaryKey(tblSmartToiletCubicle.getId());
        tblSmartToiletCubicle1.setUpdateTime(DateUtils.getNowDate());
        tblSmartToiletCubicle1.setUpdateUserId(SecurityUtils.getUserId());
        tblSmartToiletCubicle1.setStatus(tblSmartToiletCubicle.getStatus());
        tblSmartToiletCubicle1.setRemark(tblSmartToiletCubicle.getRemark());
        tblSmartToiletCubicleMapper.updateByPrimaryKey(tblSmartToiletCubicle1);
        return tblSmartToiletCubicle1;
    }

    @Override
    public TblSmartToiletInfo selectById(Long id) {
        return tblSmartToiletInfoMapper.selectByPrimaryKey(id);
    }
}
