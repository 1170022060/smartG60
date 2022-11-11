package com.pingok.monitor.service.lane.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.domain.lane.TblLaneStatus;
import com.pingok.monitor.domain.lane.TblSpecialRecord;
import com.pingok.monitor.domain.lane.vo.LaneEnum;
import com.pingok.monitor.mapper.gantry.TblGantryStatusMapper;
import com.pingok.monitor.mapper.lane.TblLaneStatusMapper;
import com.pingok.monitor.mapper.lane.TblSpecialRecordMapper;
import com.pingok.monitor.service.lane.ILaneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 车道 服务层处理
 *
 * @author qiumin
 */
@Service
public class LaneServiceImpl implements ILaneService {
    @Autowired
    private TblLaneStatusMapper tblLaneStatusMapper;
    @Autowired
    private TblSpecialRecordMapper tblSpecialRecordMapper;
    @Autowired
    private TblGantryStatusMapper tblGantryStatusMapper;

    @Override
    public LaneEnum findByStationId(String stationId) {
        LaneEnum laneEnum = new LaneEnum();

        Example example;
        Example.Criteria criteria;
        List<Map> enLane = tblLaneStatusMapper.findByStationId(stationId, 1);
        int size = enLane.size();
        for (int i=0;i<size;i++) {
            example = new Example(TblSpecialRecord.class);
            criteria = example.createCriteria();
            criteria.andEqualTo("stationId", stationId);
            criteria.andEqualTo("status", 0);
            criteria.andEqualTo("laneId", enLane.get(i).get("laneId"));
            enLane.get(i).put("specialRecords",tblSpecialRecordMapper.selectByExample(example));
        }
        laneEnum.setEnLane(enLane);

        List<Map> exLane = tblLaneStatusMapper.findByStationId(stationId, 2);
        size = exLane.size();
        for (int i=0;i<size;i++) {
            example = new Example(TblSpecialRecord.class);
            criteria = example.createCriteria();
            criteria.andEqualTo("stationId", stationId);
            criteria.andEqualTo("status", 0);
            criteria.andEqualTo("laneId", exLane.get(i).get("laneId"));
            exLane.get(i).put("specialRecords",tblSpecialRecordMapper.selectByExample(example));
        }
        laneEnum.setExtLane(exLane);
        return laneEnum;
    }

    @Override
    public List<Map> getStationFlowUpload() {
        return tblLaneStatusMapper.getStationFlowUpload();
    }

    @Override
    public Object getStationTotalFlow(String currentDate) {
        JSONObject obj = new JSONObject();
        obj.put("stationToTalFlow",tblLaneStatusMapper.getTotalFlow(currentDate));
        obj.put("stationNotUploadFlow",tblLaneStatusMapper.getNotUploadTotalFlow(currentDate));
        obj.put("gantryTotalFlow",tblGantryStatusMapper.getGantryTotalFlow(currentDate));
        obj.put("gantryNotUploadFlow",tblGantryStatusMapper.getGantryNotUploadFlow(currentDate));
        return obj;
    }

    @Override
    public List<Map> getStationInfo() {
        int faultTotal = 0;
        List<Map> stationInfo = tblLaneStatusMapper.getStationInfo();
        for (int n=0;n<stationInfo.size();n++){
            Iterator iter1 = stationInfo.get(n).keySet().iterator();
            while (iter1.hasNext()){
                String key1 = (String) iter1.next();
                if (key1.equals("stationHex")){
                    List<Map> deviceTypeStatus=tblLaneStatusMapper.getDeviceTypeTotal(stationInfo.get(n).get(key1).toString());
                    for (int i=0;i<deviceTypeStatus.size();i++){
                        Iterator iter = deviceTypeStatus.get(i).keySet().iterator();
                        while (iter.hasNext()){
                            String key = (String) iter.next();
                            if (key.equals("fault")){
                                Integer temp=Integer.parseInt(deviceTypeStatus.get(i).get(key).toString());
                                faultTotal += temp;
                            }
                        }
                    }
                }
            }
            if (faultTotal>0){
                stationInfo.get(n).put("status",1);
            }else {stationInfo.get(n).put("status",0);}
        }
        return stationInfo;
    }

    @Override
    public Object getDeviceStatus(String stationHex) {
        JSONObject obj = new JSONObject();
        obj.put("faultList",tblLaneStatusMapper.getFaultList(stationHex));
        obj.put("deviceTotal",tblLaneStatusMapper.getDeviceTotal(stationHex));
        int normalTotal = 0;
        int faultTotal = 0;
        List<Map> deviceTypeStatus=tblLaneStatusMapper.getDeviceTypeTotal(stationHex);
        for (int i=0;i<deviceTypeStatus.size();i++){
            Iterator iter = deviceTypeStatus.get(i).keySet().iterator();
            while (iter.hasNext()){
                String key = (String) iter.next();
                if (key.equals("normal")){
                   Integer temp=Integer.parseInt(deviceTypeStatus.get(i).get(key).toString());
                    normalTotal += temp;
                }else if (key.equals("fault")){
                    Integer temp=Integer.parseInt(deviceTypeStatus.get(i).get(key).toString());
                    faultTotal += temp;
                }
            }
        }
        obj.put("normalTotal",normalTotal);
        obj.put("faultTotal",faultTotal);
        obj.put("OnlineRate",(normalTotal/(double)tblLaneStatusMapper.getDeviceTotal(stationHex))*100);
        obj.put("deviceTypeStatus",tblLaneStatusMapper.getDeviceTypeTotal(stationHex));
        obj.put("laneList",tblLaneStatusMapper.getLaneListByStation(stationHex));
        return obj;
    }
}
