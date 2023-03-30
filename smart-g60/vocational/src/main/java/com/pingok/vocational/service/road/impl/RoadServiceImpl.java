package com.pingok.vocational.service.road.impl;

import com.pingok.vocational.mapper.road.TblRoadStatisEventMapper;
import com.pingok.vocational.mapper.road.TblRoadVideoEventMapper;
import com.pingok.vocational.service.road.IRoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RoadServiceImpl implements IRoadService {
    @Autowired
    private TblRoadVideoEventMapper tblRoadVideoEventMapper;

    @Autowired
    private TblRoadStatisEventMapper tblRoadStatisEventMapper;

    @Override
    public List<Map> selectRoadVideoEvent(Integer type) {
        return tblRoadVideoEventMapper.selectRoadVideoEvent(type);
    }

    @Override
    public List<Map> selectRoadStatisEvent(String vehPlate, Date startTime,Date endTime) {
        return tblRoadStatisEventMapper.selectRoadStatisEvent(vehPlate,startTime,endTime);
    }
}
