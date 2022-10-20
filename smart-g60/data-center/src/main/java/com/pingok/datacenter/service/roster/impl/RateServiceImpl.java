package com.pingok.datacenter.service.roster.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.roster.TblRateStationUsed;
import com.pingok.datacenter.mapper.roster.TblRateStationUsedMapper;
import com.pingok.datacenter.service.roster.IRecoveryService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 最小费率 服务层处理
 *
 * @author ruoyi
 */
@Service
public class RateServiceImpl implements IRecoveryService {

    @Autowired
    private TblRateStationUsedMapper tblRateStationUsedMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public void recovery(JSONObject obj) {
        TblRateStationUsed stationUsed = new TblRateStationUsed();
        stationUsed.setId(remoteIdProducerService.nextId());
        stationUsed.setStationHex(obj.getString("stationHex"));
        stationUsed.setApplyTime(obj.getDate("applyTime"));
        stationUsed.setCreateTime(DateUtils.getNowDate());
        stationUsed.setVersion(obj.getString("version"));
        tblRateStationUsedMapper.insert(stationUsed);
    }
}
