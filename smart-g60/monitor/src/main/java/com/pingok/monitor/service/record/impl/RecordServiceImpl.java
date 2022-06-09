package com.pingok.monitor.service.record.impl;

import com.pingok.monitor.mapper.record.RecordMapper;
import com.pingok.monitor.service.record.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 门架断面流量 服务层处理
 *
 * @author qiumin
 */
@Service
public class RecordServiceImpl implements IRecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public List<Map> getGantryRecord(String startTime, String endTime) {
        return recordMapper.getGantryRecord(startTime, endTime);
    }

    @Override
    public List<Map> getSectionRecord(String startTime, String endTime) {
        return recordMapper.getSectionRecord(startTime, endTime);
    }
}
