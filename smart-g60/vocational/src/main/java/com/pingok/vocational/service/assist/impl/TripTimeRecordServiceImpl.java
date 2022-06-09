package com.pingok.vocational.service.assist.impl;

import com.pingok.vocational.mapper.assist.TblTripTimeRecordMapper;
import com.pingok.vocational.service.assist.ITripTimeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 行程时间预测记录表 服务层处理
 *
 * @author ruoyi
 */
@Service
public class TripTimeRecordServiceImpl implements ITripTimeRecordService {
    @Autowired
    private TblTripTimeRecordMapper tblTripTimeRecordMapper;

    @Override
    public List<Map> selectTripTime(Date startDate, Date endDate) {
        return tblTripTimeRecordMapper.selectTripTime(startDate,endDate);
    }
}
