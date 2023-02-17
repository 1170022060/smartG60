package com.pingok.external.service.primary.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.external.domain.primary.TblPrimaryGpsInfo;
import com.pingok.external.domain.primary.TblPrimaryGpsInfoLog;
import com.pingok.external.domain.primary.TblVehicleTrailInfo;
import com.pingok.external.mapper.primary.TblPrimaryGpsLogMapper;
import com.pingok.external.mapper.primary.TblPrimaryGpsMapper;
import com.pingok.external.service.primary.ITblPrimaryGpsService;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

@Slf4j
@Service
public class TblPrimaryGpsServiceImpl implements ITblPrimaryGpsService {

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Autowired
    private TblPrimaryGpsMapper tblPrimaryGpsMapper;
    @Autowired
    private TblPrimaryGpsLogMapper tblPrimaryGpsLogMapper;


    @Override
    public void getPrimaryGps(JSONObject result) {
        Example example = new Example(TblVehicleTrailInfo.class);
        example.createCriteria().andEqualTo("license", result.getString("license"));
        boolean isExsit = true;
        TblPrimaryGpsInfo primaryGps = tblPrimaryGpsMapper.selectOneByExample(example);
            if (StringUtils.isNull(primaryGps)){
                primaryGps = new TblPrimaryGpsInfo();
                primaryGps.setId(remoteIdProducerService.nextId());
                isExsit = false;
            }
        primaryGps.setLicense(result.getString("license"));
        primaryGps.setColor(result.getString("color"));
        primaryGps.setLon(result.getLong("lon"));
        primaryGps.setLat(result.getLong("lat"));
        primaryGps.setTime(result.getDate("time"));
        primaryGps.setSpeed(result.getLong("speed"));
        primaryGps.setDirection(result.getLong("direction"));
        primaryGps.setAltitude(result.getLong("altitude"));
        primaryGps.setAcc(result.getInteger("acc"));

        if (isExsit) tblPrimaryGpsMapper.updateByPrimaryKey(primaryGps);
        else tblPrimaryGpsMapper.insert(primaryGps);

        TblPrimaryGpsInfoLog primaryGpsLog = new TblPrimaryGpsInfoLog();
        primaryGpsLog.setId(remoteIdProducerService.nextId());
        primaryGpsLog.setLicense(result.getString("license"));
        primaryGpsLog.setColor(result.getString("color"));
        primaryGpsLog.setLon(result.getLong("lon"));
        primaryGpsLog.setLat(result.getLong("lat"));
        primaryGpsLog.setTime(result.getDate("time"));
        primaryGpsLog.setSpeed(result.getLong("speed"));
        primaryGpsLog.setDirection(result.getLong("direction"));
        primaryGpsLog.setAltitude(result.getLong("altitude"));
        primaryGpsLog.setAcc(result.getInteger("acc"));
        tblPrimaryGpsLogMapper.insert(primaryGpsLog);
    }
}
