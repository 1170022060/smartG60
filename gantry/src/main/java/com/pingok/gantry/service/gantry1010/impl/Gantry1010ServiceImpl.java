package com.pingok.gantry.service.gantry120.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.pingok.gantry.domain.entity.gantry.*;
import com.pingok.gantry.mapper.gantry.*;
import com.pingok.gantry.service.gantry110.IGantry1010Service;
import com.pingok.gantry.service.gantry110.IGantry120Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *
 */

@Service
@DS(value = "gantry1010")
public class Gantry1010ServiceImpl implements IGantry1010Service {

    @Autowired
    private DfsGantryBaseinfoMapper dfsGantryBaseinfoMapper;
    @Autowired
    private DfsGantryRsuBaseinfoMapper dfsGantryRsuBaseinfoMapper;
    @Autowired
    private DfsGantryVplrBaseinfoMapper dfsGantryVplrBaseinfoMapper;
    @Autowired
    private DfsGantryMonitorStatusMapper dfsGantryMonitorStatusMapper;
    @Autowired
    private DfsGantryRsuMonitorMapper dfsGantryRsuMonitorMapper;
    @Autowired
    private DfsGantryVplrMonitorMapper dfsGantryVplrMonitorMapper;
    @Autowired
    private DfsGantryTransactionMapper dfsGantryTransactionMapper;
    @Autowired
    private DfsGantryTravelimageMapper dfsGantryTravelimageMapper;
    

    @Override
    public List<DfsGantryBaseinfo> findByStateTime(String startTime, String endTime) {
        return dfsGantryBaseinfoMapper.findByStateTime(startTime, endTime);
    }

    @Override
    public List<DfsGantryRsuBaseinfo> findByCreateTime(String startTime, String endTime) {
        return dfsGantryRsuBaseinfoMapper.findByCreateTime(startTime, endTime);
    }

    @Override
    public List<DfsGantryVplrBaseinfo> findVplrByCreateTime(String startTime, String endTime) {
        return dfsGantryVplrBaseinfoMapper.findByCreateTime(startTime, endTime);
    }

    @Override
    public List<DfsGantryMonitorStatus> findGantryMonitor(String time) {
        return dfsGantryMonitorStatusMapper.findByStateTime(time);
    }

    @Override
    public List<DfsGantryRsuMonitor> findRsuMonitor(String time) {
        return dfsGantryRsuMonitorMapper.findByCreateTime(time);
    }

    @Override
    public List<DfsGantryVplrMonitor> findVplrMonitor(String time) {
        return dfsGantryVplrMonitorMapper.findByStateTime(time);
    }

    @Override
    public List<DfsGantryTransaction> findTransactionByTransTime(Date startTime, Date endTime) {
        return dfsGantryTransactionMapper.findTransTime(startTime, endTime);
    }

    @Override
    public List<DfsGantryTravelimage> findTravelImageByPicTime(Date startTime, Date endTime) {
        return dfsGantryTravelimageMapper.findPicTime(startTime, endTime);
    }

    @Override
    public DfsGantryTransaction findTransactionById(String tradeId) {
        return dfsGantryTransactionMapper.selectByPrimaryKey(tradeId);
    }

    @Override
    public DfsGantryTravelimage findTravelImageById(String picId) {
        return dfsGantryTravelimageMapper.selectByPrimaryKey(picId);
    }
}
