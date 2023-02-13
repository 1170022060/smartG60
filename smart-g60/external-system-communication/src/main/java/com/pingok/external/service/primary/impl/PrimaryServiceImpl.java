package com.pingok.external.service.primary.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.domain.primary.TblOlFlowInfo;
import com.pingok.external.domain.primary.TblOwInfo;
import com.pingok.external.domain.primary.TblTruckOwInfo;
import com.pingok.external.domain.primary.TblVehicleTrailInfo;
import com.pingok.external.domain.roadDoctor.TblRoadPatrolInspection;
import com.pingok.external.mapper.primary.TblOWInfoMapper;
import com.pingok.external.mapper.primary.TblOlFlowInfoMapper;
import com.pingok.external.mapper.primary.TblTruckOWInfoMapper;
import com.pingok.external.mapper.primary.TblVehicleTrailMapper;
import com.pingok.external.service.primary.IPrimaryService;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

public class PrimaryServiceImpl implements IPrimaryService {

    @Autowired
    private IPrimaryService iPrimaryService;

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Autowired
    private TblVehicleTrailMapper tblVehicleTrailInfoMapper;
    @Autowired
    private TblTruckOWInfoMapper tblTruckOwInfoMapper;
    @Autowired
    private TblOWInfoMapper tblOwInfoMapper;
    @Autowired
    private TblOlFlowInfoMapper tblOlFlowMapper;

    @Override
    public void getPrimaryGps(JSONArray result) {
        for (int i = 0; i < result.size(); ++i) {
            JSONObject obj = result.getJSONObject(i);
            TblVehicleTrailInfo vehTrailInfo = new TblVehicleTrailInfo();
            Example example = new Example(TblVehicleTrailInfo.class);
            example.createCriteria().andEqualTo("license", obj.getLong("license"));
            boolean isExsit = true;
            if (tblVehicleTrailInfoMapper.selectOneByExample(example) == null){
                vehTrailInfo = new TblVehicleTrailInfo();
                vehTrailInfo.setId(remoteIdProducerService.nextId());
                isExsit = false;
            }
            vehTrailInfo.setLicense(obj.getString("license"));
            vehTrailInfo.setColor(obj.getInteger("color"));
            vehTrailInfo.setLon(obj.getLong("lon"));
            vehTrailInfo.setLat(obj.getLong("lat"));
            vehTrailInfo.setTime(obj.getDate("time"));
            vehTrailInfo.setSpeed(obj.getLong("speed"));
            vehTrailInfo.setDirection(obj.getLong("direction"));
            vehTrailInfo.setAltitude(obj.getLong("altitude"));
            vehTrailInfo.setAcc(obj.getInteger("acc"));

            if (isExsit) tblVehicleTrailInfoMapper.updateByPrimaryKey(vehTrailInfo);
            else tblVehicleTrailInfoMapper.insert(vehTrailInfo);
        }
    }

    @Override
    public void getTruckOverWeight(JSONArray result) {
        for (int i = 0; i < result.size(); ++i) {
            JSONObject obj = result.getJSONObject(i);

            TblTruckOwInfo truckOwInfo = new TblTruckOwInfo();
            Example example = new Example(TblVehicleTrailInfo.class);
            example.createCriteria().andEqualTo("license", obj.getLong("license"));
            boolean isExsit = true;
            if (tblTruckOwInfoMapper.selectOneByExample(example) == null){
                truckOwInfo = new TblTruckOwInfo();
                truckOwInfo.setId(remoteIdProducerService.nextId());
                isExsit = false;
            }
            truckOwInfo.setStatdate(obj.getDate("statdate"));
            truckOwInfo.setStatdateHour(obj.getDate("statdateHour"));
            truckOwInfo.setNodeName(obj.getString("nodeName"));
            truckOwInfo.setNodeId(obj.getString("nodeId"));
            truckOwInfo.setSumTaotalWeight(obj.getLong("sumTaotalWeight"));

            if (isExsit) tblTruckOwInfoMapper.updateByPrimaryKey(truckOwInfo);
            else tblTruckOwInfoMapper.insert(truckOwInfo);

        }
    }

    @Override
    public void getOWInfo(JSONArray result) {
        for (int i = 0; i < result.size(); ++i) {
            JSONObject obj = result.getJSONObject(i);

            TblOwInfo OWInfo = new TblOwInfo();
            Example example = new Example(TblVehicleTrailInfo.class);
            example.createCriteria().andEqualTo("license", obj.getLong("license"));
            boolean isExsit = true;
            if (tblTruckOwInfoMapper.selectOneByExample(example) == null){
                OWInfo = new TblOwInfo();
                OWInfo.setId(remoteIdProducerService.nextId());
                isExsit = false;
            }
            OWInfo.setUniqueId(obj.getLong("uniqueId"));
            OWInfo.setSiteName(obj.getString("siteName"));
            OWInfo.setSiteCode(obj.getString("siteCode"));
            OWInfo.setEquipCode(obj.getString("equipCode"));
            OWInfo.setCheckTime(obj.getDate("checkTime"));
            OWInfo.setVehicleNo(obj.getString("vehicleNo"));
            OWInfo.setPlateColor(obj.getLong("plateColor"));
            OWInfo.setVehicleAxlesType(obj.getString("vehicleAxlesType"));
            OWInfo.setTotal(obj.getLong("total"));
            OWInfo.setAxles(obj.getLong("axles"));
            OWInfo.setSpeed(obj.getLong("speed"));
            OWInfo.setLimitWeight(obj.getLong("limitWeight"));
            OWInfo.setOverWeight(obj.getLong("overWeight"));
            OWInfo.setOverRate(obj.getLong("overRate"));
            OWInfo.setFlowStatus(obj.getInteger("flowStatus"));
            OWInfo.setPlatePic(obj.getString("platePic"));
            OWInfo.setFirstHeaderPic(obj.getString("firstHeaderPic"));
            OWInfo.setDegree45Pic(obj.getString("degree45Pic"));
            OWInfo.setSidePic(obj.getString("sidePic"));
            OWInfo.setVideo(obj.getString("video"));

            if (isExsit) tblOwInfoMapper.updateByPrimaryKey(OWInfo);
            else tblOwInfoMapper.insert(OWInfo);

        }
    }

    @Override
    public void getOLFlowInfo(JSONArray result) {
        for (int i = 0; i < result.size(); ++i) {
            JSONObject obj = result.getJSONObject(i);

            TblOlFlowInfo OLFlowInfo = new TblOlFlowInfo();
            Example example = new Example(TblVehicleTrailInfo.class);
            example.createCriteria().andEqualTo("license", obj.getLong("license"));
            boolean isExsit = true;
            if (tblTruckOwInfoMapper.selectOneByExample(example) == null){
                OLFlowInfo = new TblOlFlowInfo();
                OLFlowInfo.setId(remoteIdProducerService.nextId());
                isExsit = false;
            }
            OLFlowInfo.setStatdate(obj.getDate("statdate"));
            OLFlowInfo.setNodeName(obj.getString("nodeName"));
            OLFlowInfo.setNodeId(obj.getString("nodeId"));
            OLFlowInfo.setTotalFlow(obj.getLong("totalFlow"));
            OLFlowInfo.setOverLoadFlow(obj.getLong("overLoadFlow"));
            OLFlowInfo.setRate(obj.getLong("rate"));
            OLFlowInfo.setInterceptFlow(obj.getLong("interceptFlow"));
            OLFlowInfo.setSecondReviewFlow(obj.getLong("secondReviewFlow"));
            OLFlowInfo.setSecondReviewRate(obj.getLong("secondReviewRate"));

            if (isExsit) tblOlFlowMapper.updateByPrimaryKey(OLFlowInfo);
            else tblOlFlowMapper.insert(OLFlowInfo);

        }
    }
}
