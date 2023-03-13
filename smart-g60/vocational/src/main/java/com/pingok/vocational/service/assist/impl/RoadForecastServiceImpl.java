package com.pingok.vocational.service.assist.impl;

import com.pingok.vocational.mapper.assist.TblRoadForecastMapper;
import com.pingok.vocational.service.assist.IRoadForecastService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RoadForecastServiceImpl implements IRoadForecastService {
    @Autowired
    private TblRoadForecastMapper tblRoadForecastMapper;

    @Override
    public List<Map> roadForecast(Date startDate, Date endDate) {
        return tblRoadForecastMapper.roadForecast(startDate,endDate);
    }
}
