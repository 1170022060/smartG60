package com.pingok.datacenter.service.roster.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.roster.TblEpidemicStationUsed;
import com.pingok.datacenter.domain.roster.TblRecoveryStationUsed;
import com.pingok.datacenter.mapper.roster.TblRecoveryListRecordMapper;
import com.pingok.datacenter.mapper.roster.TblRecoveryStationUsedMapper;
import com.pingok.datacenter.service.roster.IRecoveryService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 追缴名单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class RecoveryServiceImpl implements IRecoveryService {

    @Autowired
    private TblRecoveryListRecordMapper tblRecoveryListRecordMapper;
    @Autowired
    private TblRecoveryStationUsedMapper tblRecoveryStationUsedMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public void recovery(JSONObject obj) {
        TblRecoveryStationUsed stationUsed = new TblRecoveryStationUsed();
        stationUsed.setId(remoteIdProducerService.nextId());
        stationUsed.setStationHex(obj.getString("stationHex"));
        stationUsed.setApplyTime(obj.getDate("applyTime"));
        stationUsed.setCreateTime(DateUtils.getNowDate());
        stationUsed.setVersion(obj.getString("version"));
        tblRecoveryStationUsedMapper.insert(stationUsed);
    }
}
