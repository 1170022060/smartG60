package com.pingok.datacenter.service.gantry.impl;

import com.pingok.datacenter.domain.gantry.TblGantryChargeInfo;
import com.pingok.datacenter.domain.gantry.model.ChargeFlowModel;
import com.pingok.datacenter.mapper.gantry.TblGantryAlgorithmMapper;
import com.pingok.datacenter.mapper.gantry.TblGantryChargeInfoMapper;
import com.pingok.datacenter.service.gantry.IGantryAlgorithm;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 门架 服务层处理
 *
 * @author ruoyi
 */
@Service
public class GantryAlgorithmImpl implements IGantryAlgorithm {

    @Autowired
    private TblGantryAlgorithmMapper tblGantryAlgorithmMapper;
    @Autowired
    private TblGantryChargeInfoMapper tblGantryChargeInfoMapper;

    @Override
    public List<Map> selectGantryAlgorithm(String startTime, String endTime) {
        return tblGantryAlgorithmMapper.selectGantryAlgorithm(startTime,endTime);
    }

    @Override
    public List<Map> selectGantryAlgorithmList(Integer direction) {
        return tblGantryAlgorithmMapper.selectGantryAlgorithmList(direction);
    }

    @Override
    public List<Map> selectGantryAlgorithmPassRecord(String gantryId, String startTime, String endTime) {
        return tblGantryAlgorithmMapper.selectGantryAlgorithmPassRecord(gantryId,startTime,endTime);
    }

    @Override
    public List<TblGantryChargeInfo> selectChargeInfo(String chargingUnitId) {
        Example example = new Example(TblGantryChargeInfo.class);
        Example.Criteria criteria = example.createCriteria();
        List<TblGantryChargeInfo> list = new ArrayList<>();
        if(!StringUtils.isEmpty(chargingUnitId)) {
            criteria.andEqualTo("chargingUnitId", chargingUnitId);
            TblGantryChargeInfo info = tblGantryChargeInfoMapper.selectOneByExample(example);
            list.add(info);
        } else {
            list = tblGantryChargeInfoMapper.selectByExample(example);
        }
        return list;
    }

    @Override
    public List<ChargeFlowModel> selectChargeFlowList(String chargingUnitId, String startDate, String endDate) {
        String table = String.format("TBL_GANTRY_TRANSACTION_%s", startDate.substring(0, 4));
        return tblGantryChargeInfoMapper.selectChargeFlowList(table, chargingUnitId, startDate, endDate);
    }

    @Override
    public ChargeFlowModel selectChargeFlow(String chargingUnitId, String statisticsDate) {
        String table = String.format("TBL_GANTRY_TRANSACTION_%s", statisticsDate.substring(0, 4));
        return tblGantryChargeInfoMapper.selectChargeFlow(table, chargingUnitId, statisticsDate);
    }
}
