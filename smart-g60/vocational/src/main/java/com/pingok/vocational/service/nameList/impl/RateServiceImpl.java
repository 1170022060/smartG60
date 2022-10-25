package com.pingok.vocational.service.nameList.impl;

import com.pingok.vocational.domain.nameList.TblRate;
import com.pingok.vocational.domain.nameList.TblRateProv;
import com.pingok.vocational.mapper.nameList.TblRateNMapper;
import com.pingok.vocational.mapper.nameList.TblRateProvMapper;
import com.pingok.vocational.mapper.nameList.TblRateStationUsedMapper;
import com.pingok.vocational.service.nameList.IRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Service
public class RateServiceImpl implements IRateService {
    @Autowired
    private TblRateStationUsedMapper tblRateStationUsedMapper;
    @Autowired
    private TblRateNMapper tblRateNMapper;
    @Autowired
    private TblRateProvMapper tblRateProvMapper;

    @Override
    public List<Map> getRateList(String stationName, String version) {
        return tblRateStationUsedMapper.getRateList(stationName,version);
    }

    @Override
    public List<TblRate> findRateById(Long id) {
        Example example;
        example = new Example(TblRate.class);
        example.createCriteria().andEqualTo("versionId",id);
        return tblRateNMapper.selectByExample(example);
    }

    @Override
    public List<TblRateProv> findRateProvById(Long id) {
        Example example;
        example = new Example(TblRateProv.class);
        example.createCriteria().andEqualTo("rateId",id);
        return tblRateProvMapper.selectByExample(example);
    }
}
