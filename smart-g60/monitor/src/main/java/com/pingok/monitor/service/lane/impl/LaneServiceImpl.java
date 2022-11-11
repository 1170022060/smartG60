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

import java.util.Date;
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
        return tblLaneStatusMapper.getStationInfo();
    }
}
