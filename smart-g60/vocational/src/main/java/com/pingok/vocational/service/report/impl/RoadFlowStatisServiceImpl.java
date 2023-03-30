package com.pingok.vocational.service.report.impl;

import com.pingok.vocational.mapper.report.RoadFlowStatisMapper;
import com.pingok.vocational.service.report.IRoadFlowStatisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RoadFlowStatisServiceImpl implements IRoadFlowStatisService {
    @Autowired
    private RoadFlowStatisMapper roadFlowStatisMapper;

    @Override
    public List<Map> roadFlowStatis(Date startDate, Date endDate) {
        return roadFlowStatisMapper.roadFlowStatis(startDate,endDate);
    }
}
