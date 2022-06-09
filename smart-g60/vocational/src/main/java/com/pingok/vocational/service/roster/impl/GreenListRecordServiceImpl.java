package com.pingok.vocational.service.roster.impl;

import com.pingok.vocational.mapper.roster.TblGreenListRecordMapper;
import com.pingok.vocational.service.roster.IGreenListRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 绿通车辆名单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class GreenListRecordServiceImpl implements IGreenListRecordService {

    @Autowired
    private TblGreenListRecordMapper tblGreenListRecordMapper;

    @Override
    public List<Map> selectGreenList(String vehPlate) {

        return tblGreenListRecordMapper.selectGreenList(vehPlate);
    }
}
