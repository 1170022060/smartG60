package com.pingok.external.service.primary.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.domain.primary.*;
import com.pingok.external.mapper.primary.*;
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
    @Autowired
    private TblStationLLInfoMapper tblStationLLInfoMapper;
    @Autowired
    private TblOlFlowAndRateMapper tblOlFlowAndRateMapper;
    @Autowired
    private TblOlWeightAndRateMapper tblOlWeightAndRateMapper;
    @Autowired
    private TblTotalWeightOver100Mapper tblTotalWeightOver100Mapper;
    @Autowired
    private TblLargeTransportMapper tblLargeTransportMapper;

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
            Example example = new Example(TblTruckOwInfo.class);
            example.createCriteria().andEqualTo("nodeName", obj.getLong("nodeName"));
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
            Example example = new Example(TblOwInfo.class);
            example.createCriteria().andEqualTo("uniqueId", obj.getLong("uniqueId"));
            boolean isExsit = true;
            if (tblOwInfoMapper.selectOneByExample(example) == null){
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
            Example example = new Example(TblOlFlowInfo.class);
            example.createCriteria().andEqualTo("nodeId", obj.getLong("nodeId"));
            boolean isExsit = true;
            if (tblOlFlowMapper.selectOneByExample(example) == null){
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

    @Override
    public void getStationLLInfo(JSONArray result) {
        for (int i = 0; i < result.size(); ++i) {
            JSONObject obj = result.getJSONObject(i);

            TblStationLLInfo stationInfo = new TblStationLLInfo();
            Example example = new Example(TblStationLLInfo.class);
            example.createCriteria().andEqualTo("nodeId", obj.getLong("nodeId"));
            boolean isExsit = true;
            if (tblStationLLInfoMapper.selectOneByExample(example) == null){
                stationInfo = new TblStationLLInfo();
                stationInfo.setId(remoteIdProducerService.nextId());
                isExsit = false;
            }

            stationInfo.setNodeName(obj.getString("nodeName"));
            stationInfo.setNodeId(obj.getString("nodeId"));
            stationInfo.setLatitude(obj.getLong("latitude"));
            stationInfo.setLongitude(obj.getLong("longitude"));
            stationInfo.setNodeType(obj.getInteger("nodeType"));

            if (isExsit) tblStationLLInfoMapper.updateByPrimaryKey(stationInfo);
            else tblStationLLInfoMapper.insert(stationInfo);
        }
    }

    @Override
    public void getOLFlowAndRate(JSONArray result) {
        /**
         * 是否存入日期
         */
        for (int i = 0; i < result.size(); ++i) {
            JSONObject obj = result.getJSONObject(i);

            TblOlFlowandrateInfo OlFlowandRateInfo = new TblOlFlowandrateInfo();
            Example example = new Example(TblOlFlowandrateInfo.class);
            example.createCriteria().andEqualTo("Name", obj.getLong("Name"));
            boolean isExsit = true;
            if (tblOlFlowAndRateMapper.selectOneByExample(example) == null){
                OlFlowandRateInfo = new TblOlFlowandrateInfo();
                OlFlowandRateInfo.setId(remoteIdProducerService.nextId());
                isExsit = false;
            }

            OlFlowandRateInfo.setName(obj.getString("Name"));
            OlFlowandRateInfo.setData(obj.getString("data"));
            OlFlowandRateInfo.setRate(obj.getString("rate"));

            if (isExsit) tblOlFlowAndRateMapper.updateByPrimaryKey(OlFlowandRateInfo);
            else tblOlFlowAndRateMapper.insert(OlFlowandRateInfo);
        }
    }

    @Override
    public void getOLWeightAndRate(JSONArray result) {
        /**
         * 待确认
         */
        for (int i = 0; i < result.size(); ++i) {
            JSONObject obj = result.getJSONObject(i);

            TblOlWeightandrateInfo OLWeightInfo = new TblOlWeightandrateInfo();
//            Example example = new Example(TblOlWeightandrateInfo.class);
//            example.createCriteria().andEqualTo("license", obj.getLong("license"));
            boolean isExsit = true;
//            if (tblTruckOwInfoMapper.selectOneByExample(example) == null){
//                OLWeightInfo = new TblOlWeightandrateInfo();
//                OLWeightInfo.setId(remoteIdProducerService.nextId());
//                isExsit = false;
//            }
//            OLWeightInfo.setOverLoadFlow(obj.getLong("overLoadFlow"));
//            OLWeightInfo.setTotalFlow(obj.getLong("totalFlow"));
//            OLWeightInfo.setOverLoadTonflow0to5(obj.getLong("overLoadTonflow0to5"));
//            OLWeightInfo.setOverloadtonflow5to10(obj.getLong("overloadtonflow5to10"));
//            OLWeightInfo.setOverLoadTonflow10to30(obj.getLong("overLoadTonflow10to30"));
//            OLWeightInfo.setOverLoadTonflow30to55(obj.getLong("overLoadTonflow30to55"));
//            OLWeightInfo.setOverLoadTonflow55to100(obj.getLong("overLoadTonflow55to100"));
//            OLWeightInfo.setOverLoadTonflow100(obj.getLong("overLoadTonflow100"));
//            OLWeightInfo.setOverLoadTonrate0to50(obj.getLong("overLoadTonrate0to50"));
//            OLWeightInfo.setOverLoadTonerate50(obj.getLong("overLoadTonerate50"));

            if (isExsit) tblOlWeightAndRateMapper.updateByPrimaryKey(OLWeightInfo);
            else tblOlWeightAndRateMapper.insert(OLWeightInfo);

        }
    }

    @Override
    public void getTotalWeightOver100(JSONArray result) {
        for (int i = 0; i < result.size(); ++i) {
            JSONObject obj = result.getJSONObject(i);

            TblTotalWeightOver100Info over100Info = new TblTotalWeightOver100Info();
            Example example = new Example(TblTotalWeightOver100Info.class);
            example.createCriteria().andEqualTo("stationName", obj.getLong("stationName"));
            boolean isExsit = true;

            if (tblTotalWeightOver100Mapper.selectOneByExample(example) == null){
                over100Info = new TblTotalWeightOver100Info();
                over100Info.setId(remoteIdProducerService.nextId());
                isExsit = false;
            }
            over100Info.setStationName(obj.getString("stationName"));
            over100Info.setOverLoadList(obj.getString("overLoadList"));
            over100Info.setReviewFlowList(obj.getString("reviewFlowList"));

            if (isExsit) tblTotalWeightOver100Mapper.updateByPrimaryKey(over100Info);
            else tblTotalWeightOver100Mapper.insert(over100Info);
        }
    }

    @Override
    public void getLargeTransportInfo(JSONArray result) {
        for (int i = 0; i < result.size(); ++i) {
            JSONObject obj = result.getJSONObject(i);

            TblLargeTransportVehinfo LargeTransportInfo = new TblLargeTransportVehinfo();
            Example example = new Example(TblLargeTransportVehinfo.class);
            example.createCriteria().andEqualTo("vehicleLicense", obj.getLong("vehicleLicense"));
            boolean isExsit = true;

            if (tblLargeTransportMapper.selectOneByExample(example) == null){
                LargeTransportInfo = new TblLargeTransportVehinfo();
                LargeTransportInfo.setId(remoteIdProducerService.nextId());
                isExsit = false;
            }
            LargeTransportInfo.setMainId(obj.getString("mainId"));
            LargeTransportInfo.setRecordNo(obj.getString("recordNo"));
            LargeTransportInfo.setCertNo(obj.getString("certNo"));
            LargeTransportInfo.setTransportUnit(obj.getString("transportUnit"));
            LargeTransportInfo.setVehicleLicense(obj.getString("vehicleLicense"));
            LargeTransportInfo.setVehicleType(obj.getString("vehicleType"));
            LargeTransportInfo.setVehicleWeight(obj.getString("vehicleWeight"));
            LargeTransportInfo.setGoodsName(obj.getString("goodsName"));
            LargeTransportInfo.setGoodsWeight(obj.getString("goodsWeight"));
            LargeTransportInfo.setBodyLenght(obj.getString("bodyLenght"));
            LargeTransportInfo.setBodyWidth(obj.getString("bodyWidth"));
            LargeTransportInfo.setBodyHeight(obj.getString("bodyHeight"));
            LargeTransportInfo.setAxleLoadDist(obj.getString("axleLoadDist"));
            LargeTransportInfo.setDateEnd(obj.getString("dateEnd"));
            LargeTransportInfo.setRoute(obj.getString("route"));
            LargeTransportInfo.setCertificatUnit(obj.getString("certificatUnit"));
            LargeTransportInfo.setApplicationDate(obj.getString("applicationDate"));
            LargeTransportInfo.setOperator(obj.getString("operator"));
            LargeTransportInfo.setFzDate(obj.getString("fzDate"));
            LargeTransportInfo.setCreateTime(obj.getDate("createTime"));
            LargeTransportInfo.setUpdateTime(obj.getDate("updateTime"));
            LargeTransportInfo.setIsValid(obj.getLong("isValid"));
            LargeTransportInfo.setLoadWeight(obj.getString("loadWeight"));
            LargeTransportInfo.setUnionKey(obj.getString("unionKey"));

            if (isExsit) tblLargeTransportMapper.updateByPrimaryKey(LargeTransportInfo);
            else tblLargeTransportMapper.insert(LargeTransportInfo);
        }
    }
}
