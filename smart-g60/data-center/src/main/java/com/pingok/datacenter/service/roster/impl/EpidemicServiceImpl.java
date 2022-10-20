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
    private TblEpidemicListRecordMapper tblEpidemicListRecordMapper;
    @Autowired
    private TblEpidemicPrefixMapper tblEpidemicPrefixMapper;
    @Autowired
    private TblEpidemicPrefixStationUsedMapper tblEpidemicPrefixStationUsedMapper;
    @Autowired
    private TblEpidemicStationUsedMapper tblEpidemicStationUsedMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public void epidemic(JSONObject obj) {
        Example example = new Example(TblEpidemicStationUsed.class);
        example.createCriteria().andEqualTo("stationHex", obj.getString("stationHex"));
        TblEpidemicStationUsed stationUsed = tblEpidemicStationUsedMapper.selectOneByExample(example);
        if (StringUtils.isNull(stationUsed)) {
            stationUsed = new TblEpidemicStationUsed();
            stationUsed.setId(remoteIdProducerService.nextId());
            stationUsed.setStationHex(obj.getString("stationHex"));
            stationUsed.setApplyTime(obj.getDate("applyTime"));
            stationUsed.setCreateTime(DateUtils.getNowDate());
            stationUsed.setObuVersion(obj.getString("version"));
            tblEpidemicStationUsedMapper.insert(stationUsed);
        } else {
            if (Long.parseLong(stationUsed.getObuVersion()) < obj.getLong("version")) {
                stationUsed.setApplyTime(obj.getDate("applyTime"));
                stationUsed.setCreateTime(DateUtils.getNowDate());
                stationUsed.setObuVersion(obj.getString("version"));
                tblEpidemicStationUsedMapper.updateByPrimaryKey(stationUsed);
            }
        }

        JSONArray detail = obj.getJSONArray("detail");
        if (StringUtils.isNotNull(detail)) {
            JSONObject card;
            for (int i = 0; i < detail.size(); i++) {
                card = detail.getJSONObject(i);
                TblEpidemicListRecord epidemicListRecord = new TblEpidemicListRecord();
                epidemicListRecord.setId(remoteIdProducerService.nextId());
                epidemicListRecord.setStationId(obj.getString("stationId"));
                epidemicListRecord.setStationHex(card.getString("stationHex"));
                epidemicListRecord.setStationName(card.getString("stationName"));
                epidemicListRecord.setRegionName(card.getString("regionName"));
                epidemicListRecord.setVersion(obj.getString("version"));
                tblEpidemicListRecordMapper.insert(epidemicListRecord);
                }
        }
    }

    @Override
    public void epidemicPrefix(JSONObject obj) {
        Example example = new Example(TblEpidemicPrefixStationUsed.class);
        example.createCriteria().andEqualTo("stationHex", obj.getString("stationHex"));
        TblEpidemicPrefixStationUsed stationUsed = tblEpidemicPrefixStationUsedMapper.selectOneByExample(example);
        if (StringUtils.isNull(stationUsed)) {
            stationUsed = new TblEpidemicPrefixStationUsed();
            stationUsed.setId(remoteIdProducerService.nextId());
            stationUsed.setStationHex(obj.getString("stationHex"));
            stationUsed.setApplyTime(obj.getDate("applyTime"));
            stationUsed.setCreateTime(DateUtils.getNowDate());
            stationUsed.setObuVersion(obj.getString("version"));
            tblEpidemicPrefixStationUsedMapper.insert(stationUsed);
        } else {
            if (Long.parseLong(stationUsed.getObuVersion()) < obj.getLong("version")) {
                stationUsed.setApplyTime(obj.getDate("applyTime"));
                stationUsed.setCreateTime(DateUtils.getNowDate());
                stationUsed.setObuVersion(obj.getString("version"));
                tblEpidemicPrefixStationUsedMapper.updateByPrimaryKey(stationUsed);
            }
        }

        JSONArray detail = obj.getJSONArray("detail");
        if (StringUtils.isNotNull(detail)) {
            JSONObject card;
            for (int i = 0; i < detail.size(); i++) {
                card = detail.getJSONObject(i);
                TblEpidemicPrefix epidemicPrefix = new TblEpidemicPrefix();
                epidemicPrefix.setId(remoteIdProducerService.nextId());
                epidemicPrefix.setPrefix(obj.getString("prefix"));
                epidemicPrefix.setStartTime(card.getDate("startTime"));
                epidemicPrefix.setDbTime(card.getDate("dbTim"));
                epidemicPrefix.setVersion(obj.getString("version"));
                tblEpidemicPrefixMapper.insert(epidemicPrefix);
            }
        }
    }
}
