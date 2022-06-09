package com.pingok.vocational.service.assist.impl;

import com.pingok.vocational.mapper.assist.TblTollForecastRecordMapper;
import com.pingok.vocational.service.assist.ITollForecastRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通行费预测记录表 服务层处理
 *
 * @author ruoyi
 */
@Service
public class TollForecastRecordServiceImpl implements ITollForecastRecordService {

    @Autowired
    private TblTollForecastRecordMapper tblTollForecastRecordMapper;

    @Override
    public List<Map> selectTollForecast(Integer forecastType, Date startDate, Date endDate) {
        return tblTollForecastRecordMapper.selectTollForecast(forecastType,  startDate,  endDate);
    }
}
