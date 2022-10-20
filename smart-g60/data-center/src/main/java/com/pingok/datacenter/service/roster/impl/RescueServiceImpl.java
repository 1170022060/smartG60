package com.pingok.datacenter.service.roster.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.roster.TblRescueStationUsed;
import com.pingok.datacenter.mapper.roster.TblRescueListRecordMapper;
import com.pingok.datacenter.mapper.roster.TblRescueStationUsedMapper;
import com.pingok.datacenter.service.roster.IRescueService;
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
public class RescueServiceImpl implements IRescueService {

    @Autowired
    private TblRescueListRecordMapper tblRescueListRecordMapper;
    @Autowired
    private TblRescueStationUsedMapper tblRescueStationUsedMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public void rescue(JSONObject obj) {
        TblRescueStationUsed stationUsed = new TblRescueStationUsed();
        stationUsed.setId(remoteIdProducerService.nextId());
        stationUsed.setStationHex(obj.getString("stationHex"));
        stationUsed.setApplyTime(obj.getDate("applyTime"));
        stationUsed.setCreateTime(DateUtils.getNowDate());
        stationUsed.setVersion(obj.getString("version"));
        tblRescueStationUsedMapper.insert(stationUsed);
    }
}
