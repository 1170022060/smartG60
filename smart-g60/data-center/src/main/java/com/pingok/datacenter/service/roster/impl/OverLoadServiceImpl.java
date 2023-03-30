package com.pingok.datacenter.service.roster.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.roster.TblOverLoadAlarm;
import com.pingok.datacenter.mapper.roster.TblOverLoadAlarmMapper;
import com.pingok.datacenter.service.roster.IOverLoadService;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 超限车辆名单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class OverLoadServiceImpl implements IOverLoadService {
    @Autowired
    private TblOverLoadAlarmMapper tblOverLoadAlarmMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public void overLoad(JSONObject obj) {
        TblOverLoadAlarm overLoadAlarm = new TblOverLoadAlarm();
        overLoadAlarm.setId(remoteIdProducerService.nextId());
        overLoadAlarm.setSearchResult(obj.getInteger("searchResult"));
        overLoadAlarm.setCheckTime(obj.getDate("checkTime"));
        overLoadAlarm.setEquipCode(obj.getString("equipCode"));
        overLoadAlarm.setVehicleNo(obj.getString("vehicleNo"));
        overLoadAlarm.setPlateColor(obj.getInteger("plateColor"));
        overLoadAlarm.setVehicleType(obj.getInteger("vehicleType"));
        overLoadAlarm.setUniqueId(obj.getString("uniqueId"));
        overLoadAlarm.setSpeed(obj.getInteger("speed"));
        overLoadAlarm.setTotal(obj.getInteger("total"));
        overLoadAlarm.setAxles(obj.getInteger("axles"));
        overLoadAlarm.setLimitWeight(obj.getInteger("limitWeight"));
        overLoadAlarm.setOverWeight(obj.getInteger("overWeight"));
        overLoadAlarm.setOverRate(obj.getDouble("overRate"));
        overLoadAlarm.setIsBulkVehicle(obj.getInteger("isBulkVehicle"));
        overLoadAlarm.setBulkOverWeight(obj.getInteger("bulkOverWeight"));
        overLoadAlarm.setBulkOverRate(obj.getDouble("bulkOverRate"));
        overLoadAlarm.setLicNo(obj.getString("licNo"));
        overLoadAlarm.setUploadStatus(obj.getInteger("uploadStatus"));
        tblOverLoadAlarmMapper.insert(overLoadAlarm);
    }
}
