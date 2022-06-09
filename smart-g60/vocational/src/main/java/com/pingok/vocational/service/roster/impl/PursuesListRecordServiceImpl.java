package com.pingok.vocational.service.roster.impl;

import com.pingok.vocational.mapper.roster.TblPursuesListRecordMapper;
import com.pingok.vocational.service.roster.IPursuesListRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 追讨名单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class PursuesListRecordServiceImpl implements IPursuesListRecordService {

    @Autowired
    private TblPursuesListRecordMapper tblPursuesListRecordMapper;

    @Override
    public List<Map> selectPursuesList(Date startTime, Date endTime) {
        return tblPursuesListRecordMapper.selectPursuesList(startTime, endTime);
    }
}
