package com.pingok.vocational.service.report.impl;

import com.pingok.vocational.mapper.report.TblVdHistoryRecordMapper;
import com.pingok.vocational.service.report.IVdHistoryRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class VdHistoryRecordServiceImpl implements IVdHistoryRecordService {
    @Autowired
    private TblVdHistoryRecordMapper tblVdHistoryRecordMapper;

    @Override
    public List<Map> selectVdHistory(String deviceName, Integer statisticsType, Date startDate, Date endTime) {
        return tblVdHistoryRecordMapper.selectVdHistory(deviceName,statisticsType,startDate,endTime);
    }

    @Override
    public List<Map> selectPileNo() {
        return tblVdHistoryRecordMapper.selectPileNo();
    }
}
