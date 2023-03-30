package com.pingok.vocational.service.assist.impl;

import com.pingok.vocational.mapper.assist.TblCarbonEmissionMapper;
import com.pingok.vocational.service.assist.ICarbonEmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 碳排放统计表 服务层处理
 *
 * @author ruoyi
 */
@Service
public class CarbonEmissionServiceImpl implements ICarbonEmissionService{

    @Autowired
    private TblCarbonEmissionMapper tblCarbonEmissionMapper;

    @Override
    public List<Map> selectCarbonEmission(Date startDate, Date endDate) {
        return tblCarbonEmissionMapper.selectCarbonEmission(startDate, endDate);
    }

    @Override
    public List<Map> Co2Emission(Date startDate, Date endDate,Integer type) {
        return tblCarbonEmissionMapper.Co2Emission(startDate,endDate,type);
    }
}
