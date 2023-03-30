package com.pingok.vocational.service.nameList.impl;

import com.pingok.vocational.mapper.nameList.TblGreenListRecordNMapper;
import com.pingok.vocational.mapper.nameList.TblGreenStationUsedMapper;
import com.pingok.vocational.service.nameList.IGreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Service
public class GreenServiceImpl implements IGreenService {
    @Autowired
    private TblGreenListRecordNMapper tblGreenListRecordNMapper;
    @Autowired
    private TblGreenStationUsedMapper tblGreenStationUsedMapper;

    @Override
    public List<Map> getGreenList(String stationName, String version) {
        return tblGreenStationUsedMapper.getGreenList(stationName,version);
    }

    @Override
    public List<Map> findById(Long id) {
        return tblGreenListRecordNMapper.findById(id);
    }
}
