package com.pingok.datacenter.service.gantry.impl;

import com.pingok.datacenter.mapper.gantry.TblGantryAlgorithmMapper;
import com.pingok.datacenter.service.gantry.IGantryAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 门架 服务层处理
 *
 * @author ruoyi
 */
@Service
public class GantryAlgorithmImpl implements IGantryAlgorithm {

    @Autowired
    private TblGantryAlgorithmMapper tblGantryAlgorithmMapper;

    @Override
    public List<Map> selectGantryAlgorithm(String startTime, String endTime) {
        return tblGantryAlgorithmMapper.selectGantryAlgorithm(startTime,endTime);
    }

    @Override
    public List<Map> selectGantryAlgorithmList(Integer direction) {
        return tblGantryAlgorithmMapper.selectGantryAlgorithmList(direction);
    }

    @Override
    public List<Map> selectGantryAlgorithmPassRecord(String gantryId, String startTime, String endTime) {
        return tblGantryAlgorithmMapper.selectGantryAlgorithmPassRecord(gantryId,startTime,endTime);
    }
}
