package com.pingok.vocational.service.nameList.impl;

import com.pingok.vocational.mapper.nameList.TblRescueListRecordNMapper;
import com.pingok.vocational.mapper.nameList.TblRescueStationMapper;
import com.pingok.vocational.service.nameList.IRescueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Service
public class RescueServiceImpl implements IRescueService {

    @Autowired
    private TblRescueStationMapper tblRescueStationMapper;
    @Autowired
    private TblRescueListRecordNMapper tblRescueListRecordNMapper;

    @Override
    public List<Map> getEmgAppendList(String stationName, String version) {
        return tblRescueStationMapper.getEmgAppendList(stationName,version);
    }

    @Override
    public List<Map> findById(Long id) {
        return tblRescueListRecordNMapper.findById(id);
    }
}
