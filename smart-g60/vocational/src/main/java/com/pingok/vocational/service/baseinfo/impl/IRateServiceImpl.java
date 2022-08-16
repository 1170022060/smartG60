package com.pingok.vocational.service.baseinfo.impl;

import com.pingok.vocational.mapper.baseinfo.TblRateMapper;
import com.pingok.vocational.service.baseinfo.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 最小费率 服务层处理
 *
 * @author ruoyi
 */
@Service
public class IRateServiceImpl implements IRateService {

    @Autowired
    private TblRateMapper tblRateMapper;

    @Override
    public List<Map> selectRate(String inStationName, String exStationId, Integer vehClass,Integer versionNum) {
        return tblRateMapper.selectRate(inStationName,  exStationId,  vehClass,  versionNum);
    }

    @Override
    public List<Map> selectRateProv(Long rateId) {
        return tblRateMapper.selectRateProv(rateId);
    }

    @Override
    public List<Map> selectInStation() {
        return tblRateMapper.selectInStation();
    }

    @Override
    public List<Map> selectVersionNum() {
        return tblRateMapper.selectVersionNum();
    }
}
