package com.pingok.datacenter.service.roster.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.domain.roster.*;
import com.pingok.datacenter.mapper.roster.*;
import com.pingok.datacenter.service.roster.IEpidemicService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * 中高风险名单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class EpidemicServiceImpl implements IEpidemicService {
    @Autowired
    private TblEpidemicPrefixMapper tblEpidemicPrefixMapper;
    @Autowired
    private TblEpidemicListRecordMapper tblEpidemicListRecordMapper;
    @Autowired
    private TblEpidemicPrefixStationUsedMapper tblEpidemicPrefixStationUsedMapper;
    @Autowired
    private TblEpidemicStationUsedMapper tblEpidemicStationUsedMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public void epidemic(JSONObject obj) {
        TblEpidemicStationUsed stationUsed = new TblEpidemicStationUsed();
        stationUsed.setId(remoteIdProducerService.nextId());
        stationUsed.setStationHex(obj.getString("stationHex"));
        stationUsed.setApplyTime(obj.getDate("applyTime"));
        stationUsed.setCreateTime(DateUtils.getNowDate());
        stationUsed.setVersion(obj.getString("version"));
        tblEpidemicStationUsedMapper.insert(stationUsed);

    }

    @Override
    public void epidemicPrefix(JSONObject obj) {
        TblEpidemicPrefixStationUsed stationUsed = new TblEpidemicPrefixStationUsed();
        stationUsed.setId(remoteIdProducerService.nextId());
        stationUsed.setStationHex(obj.getString("stationHex"));
        stationUsed.setApplyTime(obj.getDate("applyTime"));
        stationUsed.setCreateTime(DateUtils.getNowDate());
        stationUsed.setVersion(obj.getString("version"));
        tblEpidemicPrefixStationUsedMapper.insert(stationUsed);
    }
}
