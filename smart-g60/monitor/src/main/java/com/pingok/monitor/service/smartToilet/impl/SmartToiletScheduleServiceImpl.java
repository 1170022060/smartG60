package com.pingok.monitor.service.smartToilet.impl;

import com.pingok.monitor.domain.smartToilet.TblSmartToiletSchedule;
import com.pingok.monitor.mapper.smartToilet.SmartToiletScheduleMapper;
import com.pingok.monitor.service.smartToilet.ISmartToiletScheduleService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SmartToiletScheduleServiceImpl implements ISmartToiletScheduleService {

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Autowired
    private SmartToiletScheduleMapper smartToiletScheduleMapper;

    @Override
    public List<Map> findToiletScheduleList(Long fieldId,Long toiletId, Date workDate) {
        return smartToiletScheduleMapper.findToiletScheduleList(fieldId,toiletId,workDate);
    }

    @Override
    public int insert(TblSmartToiletSchedule tblSmartToiletSchedule) {
        tblSmartToiletSchedule.setId(remoteIdProducerService.nextId());
        tblSmartToiletSchedule.setCreateTime(DateUtils.getNowDate());
        tblSmartToiletSchedule.setCreateUserId(SecurityUtils.getUserId());
        return smartToiletScheduleMapper.insert(tblSmartToiletSchedule);
    }

    @Override
    public int update(TblSmartToiletSchedule tblSmartToiletSchedule) {
        tblSmartToiletSchedule.setUpdateTime(DateUtils.getNowDate());
        tblSmartToiletSchedule.setUpdateUserId(SecurityUtils.getUserId());
        return smartToiletScheduleMapper.updateByPrimaryKey(tblSmartToiletSchedule);
    }

    @Override
    public List<Map> getToiletType() {
        return smartToiletScheduleMapper.getToiType();
    }

    @Override
    public TblSmartToiletSchedule findById(Long id) {
        TblSmartToiletSchedule tblSmartToiletSchedule = smartToiletScheduleMapper.selectByPrimaryKey(id);
        return tblSmartToiletSchedule;
    }
}
