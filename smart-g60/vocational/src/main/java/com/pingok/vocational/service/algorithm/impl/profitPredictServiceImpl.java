package com.pingok.vocational.service.algorithm.impl;

import com.pingok.vocational.mapper.algorithm.profitPredictMapper;
import com.pingok.vocational.service.algorithm.IProfitPredictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class profitPredictServiceImpl implements IProfitPredictService {
    @Autowired
    private profitPredictMapper profitPredictMapper;

    @Override
    public List<Map> profitPredict(Date startTime, Date endTime, Integer type) {

        return profitPredictMapper.profitPred(startTime,endTime,type);
    }

    @Override
    public List<Map> getProfitPredTotal(Date startTime, Date endTime) {
        return profitPredictMapper.getProfitPredTotal(startTime,endTime);
    }
}
