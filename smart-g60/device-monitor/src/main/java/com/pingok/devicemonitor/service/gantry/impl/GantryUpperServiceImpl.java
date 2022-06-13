package com.pingok.devicemonitor.service.gantry.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.gantry.*;
import com.pingok.devicemonitor.mapper.gantry.*;
import com.pingok.devicemonitor.service.gantry.IGantryUpperService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @author
 * @time 2022/5/23 12:36
 */
@Slf4j
@Service
public class GantryUpperServiceImpl implements IGantryUpperService {

    @Autowired
    private TblGantryBaseInfoCurrentMapper tblGantryBaseInfoCurrentMapper;
    @Autowired
    private TblGantryBaseInfoMapper tblGantryBaseInfoMapper;
    @Autowired
    private TblGantryRsuBaseInfoCurrentMapper tblGantryRsuBaseInfoCurrentMapper;
    @Autowired
    private TblGantryRsuBaseInfoMapper tblGantryRsuBaseInfoMapper;
    @Autowired
    private TblGantryVplrBaseInfoCurrentMapper tblGantryVplrBaseInfoCurrentMapper;
    @Autowired
    private TblGantryVplrBaseInfoMapper tblGantryVplrBaseInfoMapper;

    @Autowired
    private TblGantryMonitorStatusCurrentMapper tblGantryMonitorStatusCurrentMapper;
    @Autowired
    private TblGantryMonitorStatusMapper tblGantryMonitorStatusMapper;
    @Autowired
    private TblGantryRsuMonitorCurrentMapper tblGantryRsuMonitorCurrentMapper;
    @Autowired
    private TblGantryRsuMonitorMapper tblGantryRsuMonitorMapper;
    @Autowired
    private TblGantryVplrMonitorCurrentMapper tblGantryVplrMonitorCurrentMapper;
    @Autowired
    private TblGantryVplrMonitorMapper tblGantryVplrMonitorMapper;
    @Autowired
    private TblGantryErrorInfoMapper tblGantryErrorInfoMapper;

    @Override
    public void handleBaseInfoUpload(JSONObject data) {
        try {
            //前端（工控机），历史状态
            JSONArray gantryInfoList = data.getJSONArray("gantryInfoList");
            for (int i = 0; i < gantryInfoList.size(); i++) {
                String s = gantryInfoList.getJSONObject(i).toJSONString();
                TblGantryBaseInfo tblGantryBaseInfo = JSON.parseObject(s, TblGantryBaseInfo.class);
                tblGantryBaseInfo.setFrontSysFlag(1);
                tblGantryBaseInfoMapper.insert(tblGantryBaseInfo);
                // 实时状态
                TblGantryBaseInfoCurrent tblGantryBaseInfoCurrent = JSON.parseObject(s, TblGantryBaseInfoCurrent.class);
                Example example = new Example(TblGantryBaseInfoCurrent.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("gantryId", tblGantryBaseInfoCurrent.getGantryId());
                criteria.andEqualTo("frontSysFlag", 1);
                criteria.andEqualTo("computerOrder", tblGantryBaseInfoCurrent.getComputerOrder());
                tblGantryBaseInfoCurrentMapper.updateByExample(tblGantryBaseInfoCurrent, example);
            }

            //后台（服务器），历史状态
            JSONArray chargeUnitInfoList = data.getJSONArray("chargeUnitInfoList");
            for (int i = 0; i < chargeUnitInfoList.size(); i++) {
                String s = chargeUnitInfoList.getJSONObject(i).toJSONString();
                TblGantryBaseInfo tblGantryBaseInfoBack = JSON.parseObject(s, TblGantryBaseInfo.class);
                tblGantryBaseInfoBack.setFrontSysFlag(2);
                tblGantryBaseInfoMapper.insert(tblGantryBaseInfoBack);
                //实时状态
                TblGantryBaseInfoCurrent tblGantryBaseInfoCurrentBack = JSON.parseObject(chargeUnitInfoList.toJSONString(), TblGantryBaseInfoCurrent.class);
                Example example = new Example(TblGantryBaseInfoCurrent.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("gantryId", tblGantryBaseInfoCurrentBack.getGantryId());
                criteria.andEqualTo("frontSysFlag", 2);
                criteria.andEqualTo("computerOrder", tblGantryBaseInfoCurrentBack.getComputerOrder());
                tblGantryBaseInfoCurrentMapper.updateByExample(tblGantryBaseInfoCurrentBack, example);
            }

            //牌识，历史状态
            JSONArray cameraInfoList = data.getJSONArray("cameraInfoList");
            for (int i = 0; i < cameraInfoList.size(); i++) {
                String s = cameraInfoList.getJSONObject(i).toJSONString();
                TblGantryVplrBaseInfo tblGantryVplrBaseInfo = JSON.parseObject(s, TblGantryVplrBaseInfo.class);
                tblGantryVplrBaseInfoMapper.insert(tblGantryVplrBaseInfo);
                //实时状态
                TblGantryVplrBaseInfoCurrent tblGantryVplrBaseInfoCurrent = JSON.parseObject(s, TblGantryVplrBaseInfoCurrent.class);
                Example example = new Example(TblGantryVplrBaseInfoCurrent.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("gantryId", tblGantryVplrBaseInfoCurrent.getGantryId());
                criteria.andEqualTo("cameraNum", tblGantryVplrBaseInfoCurrent.getCameraNum());
                tblGantryVplrBaseInfoCurrentMapper.updateByExample(tblGantryVplrBaseInfoCurrent, example);
            }

            //RSU基础信息
            JSONArray rsuInfoList = data.getJSONArray("RSUInfoList");
            for (int i = 0; i < rsuInfoList.size(); i++) {
                String s = rsuInfoList.getJSONObject(i).toJSONString();
                TblGantryRsuBaseInfo tblGantryRsuBaseInfo = JSON.parseObject(s, TblGantryRsuBaseInfo.class);
                tblGantryRsuBaseInfoMapper.insert(tblGantryRsuBaseInfo);
                //实时状态
                TblGantryRsuBaseInfoCurrent tblGantryRsuBaseInfoCurrent = JSON.parseObject(s, TblGantryRsuBaseInfoCurrent.class);
                Example example = new Example(TblGantryRsuBaseInfoCurrent.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("gantryId", tblGantryRsuBaseInfoCurrent.getGantryId());
                criteria.andEqualTo("rsuId", tblGantryRsuBaseInfoCurrent.getRsuid());
                tblGantryRsuBaseInfoCurrentMapper.updateByExample(tblGantryRsuBaseInfoCurrent, example);
            }

            //车检器基础信息（暂无）
            //过车汇总集合信息（空）
            //门架其他基础信息（空）

        } catch (Exception ex) {
            throw new ServiceException("存储门架基础信息异常，原因：" + ex.getMessage());
        }
    }

    @Override
    public void handleTghbu(JSONObject data) {
        try {
            String heatVersion = data.getString("heatVersion");
            //前端（工控机）
            JSONArray gantryHeartbeatList = data.getJSONArray("gantryHeartbeatList");
            for (int i = 0; i < gantryHeartbeatList.size(); i++) {
                JSONObject jo = gantryHeartbeatList.getJSONObject(i);
                String s = jo.toJSONString();
                String frontRunStateId = jo.getString("frontRunStateId");
                Date frontStateTime = DateUtils.dateTime("yyyy-MM-dd'T'HH:mm:ss", jo.getString("frontStateTime"));
                Integer frontComputerOrder = jo.getInteger("frontComputerOrder");
                TblGantryMonitorStatus tblGantryMonitorStatus = JSON.parseObject(s, TblGantryMonitorStatus.class);
                tblGantryMonitorStatus.setFrontSysFlag(1);
                tblGantryMonitorStatus.setStateVersion(heatVersion);
                tblGantryMonitorStatus.setRunStateId(frontRunStateId);
                tblGantryMonitorStatus.setStateTime(frontStateTime);
                tblGantryMonitorStatus.setComputerOrder(frontComputerOrder);
                tblGantryMonitorStatusMapper.insert(tblGantryMonitorStatus);
                // 实时状态
                TblGantryMonitorStatusCurrent tblGantryMonitorStatusCurrent = new TblGantryMonitorStatusCurrent();
                BeanUtils.copyProperties(tblGantryMonitorStatus, tblGantryMonitorStatusCurrent);
                Example example = new Example(TblGantryMonitorStatusCurrent.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("gantryId", tblGantryMonitorStatusCurrent.getGantryId());
                criteria.andEqualTo("stateVersion", tblGantryMonitorStatusCurrent.getStateVersion());
                criteria.andEqualTo("computerOrder", tblGantryMonitorStatusCurrent.getComputerOrder());
                tblGantryMonitorStatusCurrentMapper.updateByExample(tblGantryMonitorStatusCurrent, example);
            }
            //后台（服务器）
            JSONArray chargeUnitHeartbeatList = data.getJSONArray("chargeUnitHeartbeatList");
            for (int i = 0; i < chargeUnitHeartbeatList.size(); i++) {
                JSONObject jo = chargeUnitHeartbeatList.getJSONObject(i);
                String s = jo.toJSONString();
                String backRunStateId = jo.getString("backRunStateId");
                Date backStateTime = DateUtils.dateTime("yyyy-MM-dd'T'HH:mm:ss", jo.getString("backStateTime"));
                Integer backComputerOrder = jo.getInteger("backComputerOrder");
                TblGantryMonitorStatus tblGantryMonitorStatus = JSON.parseObject(s, TblGantryMonitorStatus.class);
                tblGantryMonitorStatus.setFrontSysFlag(2);
                tblGantryMonitorStatus.setStateVersion(heatVersion);
                tblGantryMonitorStatus.setRunStateId(backRunStateId);
                tblGantryMonitorStatus.setStateTime(backStateTime);
                tblGantryMonitorStatus.setComputerOrder(backComputerOrder);
                tblGantryMonitorStatus.setFrontSysVersion(jo.getString("backSysVersion"));
                tblGantryMonitorStatus.setFrontParamVersion(jo.getString("backParamVersion"));
                tblGantryMonitorStatus.setFrontSoftVersion(jo.getString("backSoftVersion"));
                tblGantryMonitorStatus.setFrontSoftState(jo.getString("backSoftState"));
                tblGantryMonitorStatus.setFrontOverTradeCount(jo.getInteger("backOverTradeCount"));
                tblGantryMonitorStatus.setFrontTransErrTradeCount(jo.getInteger("backTransErrTradeCount"));
                tblGantryMonitorStatus.setFrontOverVehDataCount(jo.getInteger("backOverVehDataCount"));
                tblGantryMonitorStatus.setFrontTransErrVehDataCount(jo.getInteger("backTransErrVehDataCount"));
                tblGantryMonitorStatus.setFrontOverVehPicCount(jo.getInteger("backOverVehPicCount"));
                tblGantryMonitorStatus.setFrontTransErrVehPicCount(jo.getInteger("backTransErrVehPicCount"));
                tblGantryMonitorStatus.setFrontLoadAverage(jo.getString("backLoadAverage"));
                tblGantryMonitorStatus.setFrontDiskDataTotalSize(jo.getString("backDiskDataTotalSize"));
                tblGantryMonitorStatus.setFrontDiskDataLeftSize(jo.getString("backDiskDataLeftSize"));
                tblGantryMonitorStatus.setFrontDiskRunTotalSize(jo.getString("backDiskRunTotalSize"));
                tblGantryMonitorStatus.setFrontDiskRunLeftSize(jo.getString("backDiskRunLeftSize"));
                tblGantryMonitorStatus.setFrontCpuRate(jo.getString("backCPURate"));
                tblGantryMonitorStatus.setFrontMemoryRate(jo.getString("backMemoryRate"));
                tblGantryMonitorStatus.setFrontBeidouState(jo.getInteger("backBeidouState"));
                tblGantryMonitorStatus.setFrontErrorDataTotal(jo.getString("backErrorDataTotal"));
                tblGantryMonitorStatus.setFrontErrorEventTotal(jo.getString("backErrorEventTotal"));
                tblGantryMonitorStatusMapper.insert(tblGantryMonitorStatus);
                // 实时状态
                TblGantryMonitorStatusCurrent tblGantryMonitorStatusCurrent = new TblGantryMonitorStatusCurrent();
                BeanUtils.copyProperties(tblGantryMonitorStatus, tblGantryMonitorStatusCurrent);
                Example example = new Example(TblGantryMonitorStatusCurrent.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("gantryId", tblGantryMonitorStatusCurrent.getGantryId());
                criteria.andEqualTo("stateVersion", tblGantryMonitorStatusCurrent.getStateVersion());
                criteria.andEqualTo("computerOrder", tblGantryMonitorStatusCurrent.getComputerOrder());
                tblGantryMonitorStatusCurrentMapper.updateByExample(tblGantryMonitorStatusCurrent, example);
            }
            //牌识
            JSONArray cameraHeartbeatList = data.getJSONArray("cameraHeartbeatList");
            for (int i = 0; i < cameraHeartbeatList.size(); i++) {
                JSONObject jo = cameraHeartbeatList.getJSONObject(i);
                String s = jo.toJSONString();
                TblGantryVplrMonitor tblGantryVplrMonitor = JSON.parseObject(s, TblGantryVplrMonitor.class);
                tblGantryVplrMonitorMapper.insert(tblGantryVplrMonitor);
                TblGantryVplrMonitorCurrent tblGantryVplrMonitorCurrent = new TblGantryVplrMonitorCurrent();
                BeanUtils.copyProperties(tblGantryVplrMonitor, tblGantryVplrMonitorCurrent);
                Example example = new Example(TblGantryVplrMonitorCurrent.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("gantryId", tblGantryVplrMonitorCurrent.getGantryId());
                criteria.andEqualTo("cameraNum", tblGantryVplrMonitorCurrent.getCameraNum());
                tblGantryVplrMonitorCurrentMapper.updateByExample(tblGantryVplrMonitorCurrent, example);
            }
            //天线
            JSONArray rsuHeartbeatList = data.getJSONArray("RSUHeartbeatList");
            JSONArray psamInfoList = data.getJSONArray("PSAMInfoList");
            JSONArray antennalInfoList = data.getJSONArray("antennalInfoList");
            for (int i = 0; i < rsuHeartbeatList.size(); i++) {
                JSONObject jo = rsuHeartbeatList.getJSONObject(i);
                jo.put("psamInfoList", psamInfoList);
                jo.put("antennalInfoList", antennalInfoList);
                String s = jo.toJSONString();
                TblGantryRsuMonitor tblGantryRsuMonitor = JSON.parseObject(s, TblGantryRsuMonitor.class);
                tblGantryRsuMonitorMapper.insert(tblGantryRsuMonitor);
                TblGantryRsuMonitorCurrent tblGantryRsuMonitorCurrent = new TblGantryRsuMonitorCurrent();
                BeanUtils.copyProperties(tblGantryRsuMonitor, tblGantryRsuMonitorCurrent);
                Example example = new Example(TblGantryRsuMonitorCurrent.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("gantryId", tblGantryRsuMonitorCurrent.getGantryId());
                criteria.andEqualTo("rsuId", tblGantryRsuMonitorCurrent.getRsuid());
                tblGantryRsuMonitorCurrentMapper.updateByExample(tblGantryRsuMonitorCurrent, example);
            }
        } catch (Exception ex) {
            log.error("处理门架报文异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleSpecialEventUpload(JSONObject data) {
        try {
            TblGantryErrorInfo tblGantryErrorInfo = JSON.parseObject(data.toJSONString(), TblGantryErrorInfo.class);
            tblGantryErrorInfoMapper.insert(tblGantryErrorInfo);
        } catch (Exception ex) {
            log.error("处理门架报文异常：" + ex.getMessage());
        }
    }

}
