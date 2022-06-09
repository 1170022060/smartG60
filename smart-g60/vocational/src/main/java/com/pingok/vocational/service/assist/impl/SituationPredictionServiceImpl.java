package com.pingok.vocational.service.assist.impl;

import com.pingok.vocational.mapper.assist.TblSituationPredictionMapper;
import com.pingok.vocational.service.assist.ISituationPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 路段运行态势预估表 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SituationPredictionServiceImpl implements ISituationPredictionService {

    @Autowired
    private TblSituationPredictionMapper tblSituationPredictionMapper;

    @Override
    public List<Map> selectSituationFlow(Date startDate, Date endDate) {
        return tblSituationPredictionMapper.selectSituationFlow(startDate,endDate);
    }

    @Override
    public List<Map> selectSituationStatus(Date startDate, Date endDate) {
        return tblSituationPredictionMapper.selectSituationStatus(startDate,endDate);
    }
}
