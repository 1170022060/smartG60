package com.pingok.vocational.service.roster.impl;

import com.pingok.vocational.mapper.roster.TblRescueListRecordMapper;
import com.pingok.vocational.service.roster.IRescueListRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * 抢险救灾名单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class RescueListRecordServiceImpl implements IRescueListRecordService {

    @Autowired
    private TblRescueListRecordMapper tblRescueListRecordMapper;

    @Override
    public List<Map> selectRescueList(String vehPlate, Date startTime, Date endTime) {

        return tblRescueListRecordMapper.selectRescueList(vehPlate, startTime, endTime);
    }
}
