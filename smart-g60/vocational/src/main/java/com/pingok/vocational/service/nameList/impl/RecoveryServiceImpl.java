package com.pingok.vocational.service.nameList.impl;

import com.pingok.vocational.mapper.nameList.TblRecoveryListRecordNMapper;
import com.pingok.vocational.mapper.nameList.TblRecoveryMapper;
import com.pingok.vocational.service.nameList.IRecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Service
public class RecoveryServiceImpl implements IRecoveryService {
    @Autowired
    private TblRecoveryMapper tblRecoveryMapper;
    @Autowired
    private TblRecoveryListRecordNMapper tblRecoveryListRecordNMapper;

    @Override
    public List<Map> getRecoveryList(String stationName,String version) {
        return tblRecoveryMapper.getRecoveryList(stationName,version);
    }

    @Override
    public List<Map> findById(Long id) {
        return tblRecoveryListRecordNMapper.getRecoveryListById(id);
    }
}
