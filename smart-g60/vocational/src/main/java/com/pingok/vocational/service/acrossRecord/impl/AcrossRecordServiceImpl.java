package com.pingok.vocational.service.acrossRecord.impl;

import com.pingok.vocational.mapper.acrossRecord.acrossRecordManaMapper;
import com.pingok.vocational.service.acrossRecord.IAcrossRecordService;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class AcrossRecordServiceImpl implements IAcrossRecordService {

    @Autowired
    private acrossRecordManaMapper acrossRecordManaMapper;

    @Override
    public List<Map> selectAcrossRecord(String vehPlate, Date startTime, Date endTime) {
        return acrossRecordManaMapper.selectAcrossRecord(DateUtils.getTimeDay(startTime).substring(0,4),vehPlate,startTime,endTime);
    }

    @Override
    public List<Map> selectRecord(Date time, String passId) {
        return acrossRecordManaMapper.selectRecord(DateUtils.getTimeDay(time).substring(0,4),passId);
    }
}
