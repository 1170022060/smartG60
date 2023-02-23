package com.pingok.external.service.primary.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.external.domain.primary.TblOwInfo;
import com.pingok.external.mapper.primary.TblOWInfoMapper;
import com.pingok.external.service.primary.ITblOwService;
import com.ruoyi.system.api.RemoteIdProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TblOwServiceImpl implements ITblOwService {

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblOWInfoMapper tblOWInfoMapper;
    @Override
    public void getOwInfo(JSONObject obj) {

        TblOwInfo OWInfo = new TblOwInfo();

        OWInfo.setId(remoteIdProducerService.nextId());
        OWInfo.setUniqueId(obj.getString("uniqueId"));
        OWInfo.setSiteName(obj.getString("siteName"));
        OWInfo.setSiteCode(obj.getString("siteCode"));
        OWInfo.setEquipCode(obj.getString("equipCode"));
        OWInfo.setCheckTime(obj.getString("checkTime"));
        OWInfo.setVehicleNo(obj.getString("vehicleNo"));
        OWInfo.setPlateColor(obj.getInteger("plateColor"));
        OWInfo.setVehicleAxlesType(obj.getString("vehicleAxlesType"));
        OWInfo.setTotal(obj.getBigDecimal("total"));
        OWInfo.setAxles(obj.getLong("axles"));
        OWInfo.setSpeed(obj.getBigDecimal("speed"));
        OWInfo.setLimitWeight(obj.getBigDecimal("limitWeight"));
        OWInfo.setOverWeight(obj.getBigDecimal("overWeight"));
        OWInfo.setOverRate(obj.getBigDecimal("overRate"));
        OWInfo.setFlowStatus(obj.getInteger("flowStatus"));
        OWInfo.setPlatePic(obj.getString("platePic"));
        OWInfo.setFirstHeaderPic(obj.getString("firstHeaderPic"));
        OWInfo.setDegree45Pic(obj.getString("degree45Pic"));
        OWInfo.setSidePic(obj.getString("sidePic"));
        OWInfo.setVideo(obj.getString("video"));

        tblOWInfoMapper.insert(OWInfo);

    }
}
