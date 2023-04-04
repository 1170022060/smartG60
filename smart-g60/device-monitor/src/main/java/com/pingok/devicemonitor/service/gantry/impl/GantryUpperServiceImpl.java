package com.pingok.devicemonitor.service.gantry.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.gantry.*;
import com.pingok.devicemonitor.domain.gantry.vo.*;
import com.pingok.devicemonitor.mapper.gantry.*;
import com.pingok.devicemonitor.service.gantry.IGantryUpperService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.RemoteFileService;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.domain.SysFile;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.entity.ContentType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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


    @Autowired
    private TblGantryInfoMapper tblGantryInfoMapper;
    @Autowired
    private TblGantryInfoDtlMapper tblGantryInfoDtlMapper;
    @Autowired
    private TblGantryPictureMapper tblGantryPictureMapper;
    @Autowired
    private TblGantryPictureFailMapper tblGantryPictureFailMapper;
    @Autowired
    private TblGantrySumTransactionMapper tblGantrySumTransactionMapper;
    @Autowired
    private TblGantrySumTravelImageMapper tblGantrySumTravelImageMapper;
    @Autowired
    private TblGantryTransactionMapper tblGantryTransactionMapper;
    @Autowired
    private TblGantryTravelImageMapper tblGantryTravelImageMapper;
    @Autowired
    private RemoteFileService remoteFileService;
    @Autowired
    private TblGantryLogfileMapper tblGantryLogfileMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;


    @Override
    public String getReqType(String reqFileName) {
        int pos = reqFileName.indexOf('_');
        pos = reqFileName.indexOf('_', pos + 1);
        return reqFileName.substring(0, pos);
    }


    @Override
    public JSONObject genResponse(String reqType, Integer code) {
        JSONObject jo = new JSONObject();
        jo.put("subCode", code);
        jo.put("info", code == 200 ? "成功" : "失败");
        jo.put("receiveTime", DateUtils.dateTimeNow("yyyy-MM-dd'T'HH:mm:ss"));

        switch (reqType) {
            case "GBUPLOAD_VIU": {
                jo.put("successCount", 0);
                jo.put("failList", new JSONArray());
                break;
            }
            default:
                break;
        }
        return jo;
    }

    @Async
    @Override
    public void handleBaseInfoUpload(JSONObject data) {
        try {
            //前端（工控机），历史状态
            TblGantryBaseInfo tblGantryBaseInfo;
            String s;
            TblGantryBaseInfoCurrent tblGantryBaseInfoCurrent;
            Example example;
            Example.Criteria criteria;
            JSONArray gantryInfoList = data.getJSONArray("gantryInfoList");
            for (int i = 0; i < gantryInfoList.size(); i++) {
                s = gantryInfoList.getJSONObject(i).toJSONString();
                tblGantryBaseInfo = JSON.parseObject(s, TblGantryBaseInfo.class);
                if(StringUtils.isEmpty(tblGantryBaseInfo.getGantryId())){
                    return;
                }
                tblGantryBaseInfo.setFrontSysFlag(1);
                tblGantryBaseInfoMapper.insert(tblGantryBaseInfo);
                // 实时状态
                tblGantryBaseInfoCurrent = JSON.parseObject(s, TblGantryBaseInfoCurrent.class);
                example = new Example(TblGantryBaseInfoCurrent.class);
                criteria = example.createCriteria();
                criteria.andEqualTo("gantryId", tblGantryBaseInfoCurrent.getGantryId());
                criteria.andEqualTo("frontSysFlag", 1);
                criteria.andEqualTo("computerOrder", tblGantryBaseInfoCurrent.getComputerOrder());
                tblGantryBaseInfoCurrentMapper.updateByExample(tblGantryBaseInfoCurrent, example);
            }

            //后台（服务器），历史状态
            TblGantryBaseInfo tblGantryBaseInfoBack;
            TblGantryBaseInfoCurrent tblGantryBaseInfoCurrentBack;
            JSONArray chargeUnitInfoList = data.getJSONArray("chargeUnitInfoList");
            for (int i = 0; i < chargeUnitInfoList.size(); i++) {
                s = chargeUnitInfoList.getJSONObject(i).toJSONString();
                tblGantryBaseInfoBack = JSON.parseObject(s, TblGantryBaseInfo.class);
                if(StringUtils.isEmpty(tblGantryBaseInfoBack.getGantryId())){
                    return;
                }
                tblGantryBaseInfoBack.setFrontSysFlag(2);
                tblGantryBaseInfoMapper.insert(tblGantryBaseInfoBack);
                //实时状态
                tblGantryBaseInfoCurrentBack = JSON.parseObject(chargeUnitInfoList.toJSONString(), TblGantryBaseInfoCurrent.class);
                example = new Example(TblGantryBaseInfoCurrent.class);
                criteria = example.createCriteria();
                criteria.andEqualTo("gantryId", tblGantryBaseInfoCurrentBack.getGantryId());
                criteria.andEqualTo("frontSysFlag", 2);
                criteria.andEqualTo("computerOrder", tblGantryBaseInfoCurrentBack.getComputerOrder());
                tblGantryBaseInfoCurrentMapper.updateByExample(tblGantryBaseInfoCurrentBack, example);
            }

            //牌识，历史状态
            TblGantryVplrBaseInfo tblGantryVplrBaseInfo;
            TblGantryVplrBaseInfoCurrent tblGantryVplrBaseInfoCurrent;
            JSONArray cameraInfoList = data.getJSONArray("cameraInfoList");
            for (int i = 0; i < cameraInfoList.size(); i++) {
                s = cameraInfoList.getJSONObject(i).toJSONString();
                tblGantryVplrBaseInfo = JSON.parseObject(s, TblGantryVplrBaseInfo.class);
                if(StringUtils.isEmpty(tblGantryVplrBaseInfo.getGantryId())){
                    return;
                }
                tblGantryVplrBaseInfoMapper.insert(tblGantryVplrBaseInfo);
                //实时状态
                tblGantryVplrBaseInfoCurrent = JSON.parseObject(s, TblGantryVplrBaseInfoCurrent.class);
                example = new Example(TblGantryVplrBaseInfoCurrent.class);
                criteria = example.createCriteria();
                criteria.andEqualTo("gantryId", tblGantryVplrBaseInfoCurrent.getGantryId());
                criteria.andEqualTo("cameraNum", tblGantryVplrBaseInfoCurrent.getCameraNum());
                tblGantryVplrBaseInfoCurrentMapper.updateByExample(tblGantryVplrBaseInfoCurrent, example);
            }

            //RSU基础信息
            JSONArray rsuInfoList = data.getJSONArray("RSUInfoList");
            TblGantryRsuBaseInfo tblGantryRsuBaseInfo;
            TblGantryRsuBaseInfoCurrent tblGantryRsuBaseInfoCurrent;
            for (int i = 0; i < rsuInfoList.size(); i++) {
                s = rsuInfoList.getJSONObject(i).toJSONString();
                tblGantryRsuBaseInfo = JSON.parseObject(s, TblGantryRsuBaseInfo.class);
                if(StringUtils.isEmpty(tblGantryRsuBaseInfo.getGantryId())){
                    return;
                }
                tblGantryRsuBaseInfoMapper.insert(tblGantryRsuBaseInfo);
                //实时状态
                tblGantryRsuBaseInfoCurrent = JSON.parseObject(s, TblGantryRsuBaseInfoCurrent.class);
                example = new Example(TblGantryRsuBaseInfoCurrent.class);
                criteria = example.createCriteria();
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

    @Async
    @Override
    public void handleTghbu(JSONObject data) {
        try {
            String heatVersion = data.getString("heatVersion");
            //前端（工控机）
            JSONArray gantryHeartbeatList = data.getJSONArray("gantryHeartbeatList");
            JSONObject jo;
            String s;
            String frontRunStateId;
            Date frontStateTime;
            Integer frontComputerOrder;
            TblGantryMonitorStatus tblGantryMonitorStatus;
            TblGantryMonitorStatusCurrent tblGantryMonitorStatusCurrent;
            Example example;
            Example.Criteria criteria;
            for (int i = 0; i < gantryHeartbeatList.size(); i++) {
                jo = gantryHeartbeatList.getJSONObject(i);
                s = jo.toJSONString();
                frontRunStateId = jo.getString("frontRunStateId");
                frontStateTime = DateUtils.dateTime("yyyy-MM-dd'T'HH:mm:ss", jo.getString("frontStateTime"));
                frontComputerOrder = jo.getInteger("frontComputerOrder");
                tblGantryMonitorStatus = JSON.parseObject(s, TblGantryMonitorStatus.class);
                if(StringUtils.isEmpty(tblGantryMonitorStatus.getGantryId())){
                    return;
                }
                tblGantryMonitorStatus.setFrontSysFlag(1);
                tblGantryMonitorStatus.setStateVersion(heatVersion);
                tblGantryMonitorStatus.setRunStateId(frontRunStateId);
                tblGantryMonitorStatus.setStateTime(frontStateTime);
                tblGantryMonitorStatus.setComputerOrder(frontComputerOrder);
                tblGantryMonitorStatusMapper.insert(tblGantryMonitorStatus);
                // 实时状态
                tblGantryMonitorStatusCurrent = new TblGantryMonitorStatusCurrent();
                BeanUtils.copyProperties(tblGantryMonitorStatus, tblGantryMonitorStatusCurrent);
                example = new Example(TblGantryMonitorStatusCurrent.class);
                criteria = example.createCriteria();
                criteria.andEqualTo("gantryId", tblGantryMonitorStatusCurrent.getGantryId());
                criteria.andEqualTo("stateVersion", tblGantryMonitorStatusCurrent.getStateVersion());
                criteria.andEqualTo("computerOrder", tblGantryMonitorStatusCurrent.getComputerOrder());
                tblGantryMonitorStatusCurrentMapper.updateByExample(tblGantryMonitorStatusCurrent, example);
            }
            //后台（服务器）
            JSONArray chargeUnitHeartbeatList = data.getJSONArray("chargeUnitHeartbeatList");
            String backRunStateId;
            Date backStateTime;
            Integer backComputerOrder;
            for (int i = 0; i < chargeUnitHeartbeatList.size(); i++) {
                jo = chargeUnitHeartbeatList.getJSONObject(i);
                s = jo.toJSONString();
                backRunStateId = jo.getString("backRunStateId");
                backStateTime = DateUtils.dateTime("yyyy-MM-dd'T'HH:mm:ss", jo.getString("backStateTime"));
                backComputerOrder = jo.getInteger("backComputerOrder");
                tblGantryMonitorStatus = JSON.parseObject(s, TblGantryMonitorStatus.class);
                if(StringUtils.isEmpty(tblGantryMonitorStatus.getGantryId())){
                    return;
                }
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
                tblGantryMonitorStatusCurrent = new TblGantryMonitorStatusCurrent();
                BeanUtils.copyProperties(tblGantryMonitorStatus, tblGantryMonitorStatusCurrent);
                example = new Example(TblGantryMonitorStatusCurrent.class);
                criteria = example.createCriteria();
                criteria.andEqualTo("gantryId", tblGantryMonitorStatusCurrent.getGantryId());
                criteria.andEqualTo("stateVersion", tblGantryMonitorStatusCurrent.getStateVersion());
                criteria.andEqualTo("computerOrder", tblGantryMonitorStatusCurrent.getComputerOrder());
                tblGantryMonitorStatusCurrentMapper.updateByExample(tblGantryMonitorStatusCurrent, example);
            }
            //牌识
            JSONArray cameraHeartbeatList = data.getJSONArray("cameraHeartbeatList");
            TblGantryVplrMonitor tblGantryVplrMonitor;
            TblGantryVplrMonitorCurrent tblGantryVplrMonitorCurrent;
            for (int i = 0; i < cameraHeartbeatList.size(); i++) {
                jo = cameraHeartbeatList.getJSONObject(i);
                s = jo.toJSONString();
                tblGantryVplrMonitor = JSON.parseObject(s, TblGantryVplrMonitor.class);
                if(StringUtils.isEmpty(tblGantryVplrMonitor.getGantryId())){
                    return;
                }
                tblGantryVplrMonitorMapper.insert(tblGantryVplrMonitor);
                tblGantryVplrMonitorCurrent = new TblGantryVplrMonitorCurrent();
                BeanUtils.copyProperties(tblGantryVplrMonitor, tblGantryVplrMonitorCurrent);
                example = new Example(TblGantryVplrMonitorCurrent.class);
                criteria = example.createCriteria();
                criteria.andEqualTo("gantryId", tblGantryVplrMonitorCurrent.getGantryId());
                criteria.andEqualTo("cameraNum", tblGantryVplrMonitorCurrent.getCameraNum());
                tblGantryVplrMonitorCurrentMapper.updateByExample(tblGantryVplrMonitorCurrent, example);
            }
            //天线
            JSONArray rsuHeartbeatList = data.getJSONArray("RSUHeartbeatList");
            JSONArray psamInfoList = data.getJSONArray("PSAMInfoList");
            JSONArray antennalInfoList = data.getJSONArray("antennalInfoList");
            TblGantryRsuMonitor tblGantryRsuMonitor;
            TblGantryRsuMonitorCurrent tblGantryRsuMonitorCurrent;
            for (int i = 0; i < rsuHeartbeatList.size(); i++) {
                jo = rsuHeartbeatList.getJSONObject(i);
                jo.put("psamInfoList", psamInfoList);
                jo.put("antennalInfoList", antennalInfoList);
                s = jo.toJSONString();
                tblGantryRsuMonitor = JSON.parseObject(s, TblGantryRsuMonitor.class);
                if(StringUtils.isEmpty(tblGantryRsuMonitor.getGantryId())){
                    return;
                }
                tblGantryRsuMonitorMapper.insert(tblGantryRsuMonitor);
                tblGantryRsuMonitorCurrent = new TblGantryRsuMonitorCurrent();
                BeanUtils.copyProperties(tblGantryRsuMonitor, tblGantryRsuMonitorCurrent);
                example = new Example(TblGantryRsuMonitorCurrent.class);
                criteria = example.createCriteria();
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
            if(StringUtils.isEmpty(tblGantryErrorInfo.getGantryId())){
                return;
            }
            tblGantryErrorInfoMapper.insert(tblGantryErrorInfo);
        } catch (Exception ex) {
            log.error("处理门架报文异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleViu(TblGantryTravelImage data) {
        try {
            if(StringUtils.isEmpty(data.getGantryId())){
                return;
            }
            String year = DateUtils.dateYear();
            data.setYear(year);
            tblGantryTravelImageMapper.addTblGantryTravelimage(data);
        } catch (Exception ex) {
            log.error("存储门架牌识数据异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleViu(List<TblGantryTravelImage> data) {
        try {
            for (TblGantryTravelImage g : data) {
                if (!tblGantryTravelImageMapper.existsWithPrimaryKey(g.getPicId())) {
                    tblGantryTravelImageMapper.insert(g);
                } else {
                    tblGantryTravelImageMapper.updateByPrimaryKey(g);
                }
            }
        } catch (Exception ex) {
            log.error("存储门架牌识数据异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleVipu(TblGantryPicture data) {
        try {
            if(StringUtils.isEmpty(data.getGantryId())){
                return;
            }
            tblGantryPictureMapper.addTblGantryPicture(data);
        } catch (Exception ex) {
            log.error("存储门架牌识图片异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleVipu(List<TblGantryPicture> data) {
        try {
            for (TblGantryPicture g : data) {
                if (!tblGantryPictureMapper.existsWithPrimaryKey(g.getPicId())) {
                    tblGantryPictureMapper.insert(g);
                } else {
                    tblGantryPictureMapper.updateByPrimaryKey(g);
                }
            }
        } catch (Exception ex) {
            log.error("存储门架牌识图片异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleSvipu(TblGantryPictureFail data) {
        try {
            if(StringUtils.isEmpty(data.getGantryId())){
                return;
            }
            tblGantryPictureFailMapper.addTblGantryPictureFail(data);
        } catch (Exception ex) {
            log.error("存储门架牌识图片异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleSvipu(List<TblGantryPictureFail> data) {
        try {
            for (TblGantryPictureFail g : data) {
                if (!tblGantryPictureFailMapper.existsWithPrimaryKey(g.getPicId())) {
                    tblGantryPictureFailMapper.insert(g);
                } else {
                    tblGantryPictureFailMapper.updateByPrimaryKey(g);
                }
            }
        } catch (Exception ex) {
            log.error("存储门架牌识图片异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleEtctu(TblGantryTransaction data) {
        try {
            if(StringUtils.isEmpty(data.getGantryId())){
                return;
            }
            String year = DateUtils.dateYear();
            data.setYear(year);
            tblGantryTransactionMapper.addtblGantryTransaction(data);
        } catch (Exception ex) {
            log.error("存储门架交易流水异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleEtctu(List<TblGantryTransaction> data) {
        try {
            for (TblGantryTransaction g : data) {
                if (!tblGantryTransactionMapper.existsWithPrimaryKey(g.getTradeId())) {
                    tblGantryTransactionMapper.insert(g);
                } else {
                    tblGantryTransactionMapper.updateByPrimaryKey(g);
                }
            }
        } catch (Exception ex) {
            log.error("存储门架交易流水异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleEtcsu(TblGantrySumTransaction data) {
        try {
            if(StringUtils.isEmpty(data.getGantryId())){
                return;
            }
            tblGantrySumTransactionMapper.addTblGantrySumTransaction(data);
        } catch (Exception ex) {
            log.error("存储ETC 门架交易小时批次汇总异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleEtcsu(List<TblGantrySumTransaction> data) {
        try {
            for (TblGantrySumTransaction g : data) {
                if (!tblGantryTransactionMapper.existsWithPrimaryKey(g.getCollectId())) {
                    tblGantrySumTransactionMapper.insert(g);
                } else {
                    tblGantrySumTransactionMapper.updateByPrimaryKey(g);
                }
            }
        } catch (Exception ex) {
            log.error("存储ETC 门架交易小时批次汇总异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleVisu(TblGantrySumTravelImage data) {
        try {
            if(StringUtils.isEmpty(data.getGantryid())){
                return;
            }
            tblGantrySumTravelImageMapper.addTblGantrySumTravelimage(data);
        } catch (Exception ex) {
            log.error("存储ETC 门架牌识小时批次汇总异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleVisu(List<TblGantrySumTravelImage> data) {
        try {
            for (TblGantrySumTravelImage g : data) {
                if (!tblGantrySumTravelImageMapper.existsWithPrimaryKey(g.getCollectId())) {
                    tblGantrySumTravelImageMapper.insert(g);
                } else {
                    tblGantrySumTravelImageMapper.updateByPrimaryKey(g);
                }
            }
        } catch (Exception ex) {
            log.error("存储ETC 门架牌识小时批次汇总异常：" + ex.getMessage());
        }
    }

    @Override
    public void handleLog(JSONObject data) {
        try {
            String gantryId = null;
            String path = "/root/" + DateUtils.getNowTimestampLong();
            String fileName = data.getString("reqFileName") + ".zip";
            File file = new File(path);
            if (!file.exists()) {
                file.mkdir();
            }
            String pathName = path + "/" + fileName;
            file = new File(pathName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data.getString("data").getBytes(), 0, data.getString("data").getBytes().length);
            fos.flush();
            fos.close();
            ZipFile zp = new ZipFile(pathName, Charset.forName("gbk"));
            Enumeration entries = zp.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                String zipEntryName = entry.getName();
                if (zipEntryName.contains(".json")) {
                    FileReader fileReader = new FileReader(pathName);
                    Reader reader = new InputStreamReader(new FileInputStream(pathName), "utf-8");
                    int ch = 0;
                    StringBuffer sb = new StringBuffer();
                    while ((ch = reader.read()) != -1) {
                        sb.append((char) ch);
                    }
                    fileReader.close();
                    reader.close();
                    JSONObject object = JSONObject.parseObject(sb.toString());
                    gantryId = object.getString("gantryId");
                    break;
                }
            }
            zp.close();
            file.delete();
            InputStream inputStream = new ByteArrayInputStream(data.getString("data").getBytes());
            MultipartFile mFile = new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
            R<SysFile> r = remoteFileService.upload(mFile);
            if (r != null) {
                if (R.SUCCESS == r.getCode()) {
                    if (gantryId != null) {
                        Example example = new Example(TblGantryLogfile.class);
                        example.createCriteria().andEqualTo("gantryId", gantryId);
                        TblGantryLogfile tblGantryLogfile = tblGantryLogfileMapper.selectOneByExample(example);
                        if (tblGantryLogfile == null) {
                            tblGantryLogfile = new TblGantryLogfile();
                            tblGantryLogfile.setGantryId(gantryId);
                            tblGantryLogfile.setId(remoteIdProducerService.nextId());
                            tblGantryLogfile.setUrl(r.getData().getUrl());
                            tblGantryLogfileMapper.insert(tblGantryLogfile);
                        } else {
                            tblGantryLogfile.setUrl(r.getData().getUrl());
                            tblGantryLogfileMapper.updateByPrimaryKey(tblGantryLogfile);
                        }
                    } else {
                        throw new ServiceException("门架日志存储失败，原因：JSON文件内未包含门架编号");
                    }
                } else {
                    throw new ServiceException("门架日志存储失败，原因：" + r.getMsg());
                }
            } else {
                throw new ServiceException("门架日志存储失败，原因未知");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 调取相关上传异常数据，对上传异常数据进行人工干预修正
    @Override
    public AjaxResult GetErrorData(GetErrorDataModel body) {
        body.setMsgId(genMsgId(body.getGantryId()));
        body.setMsgTime(DateUtils.dateTimeNow("yyyy-MM-dd'T'HH:mm:ss"));
        String data = JSONObject.toJSONString(body);
        JSONObject ret = send(body.getGantryId(), "NOTIFY_GETERRORDATA", data);
        if (ret == null) {
            return AjaxResult.error();
        }
        return AjaxResult.success(ret);
    }

    @Override
    public AjaxResult FixErrorData(FixErrorDataModel body) {
        body.setMsgId(genMsgId(body.getGantryId()));
        body.setMsgTime(DateUtils.dateTimeNow("yyyy-MM-dd'T'HH:mm:ss"));
        String data = JSONObject.toJSONString(body);
        JSONObject ret = send(body.getGantryId(), "NOTIFY_FIXERRORDATA", data);
        if (ret == null) {
            return AjaxResult.error();
        }
        return AjaxResult.success(ret);
    }

    @Override
    public AjaxResult PicRealTransfer(PicRealTransferModel body) {
        String data = JSONObject.toJSONString(body);
        JSONObject ret = send(body.getGantryId(), "NOTIFY_PICREALTRANSFER", data);
        if (ret == null) {
            return AjaxResult.error();
        }
        return AjaxResult.success(ret);
    }

    // 牌识数据重传
    @Override
    public AjaxResult VIUpload(VIUploadModel body) {
        body.setMsgId(genMsgId(body.getGantryId()));
        body.setMsgTime(DateUtils.dateTimeNow("yyyy-MM-dd'T'HH:mm:ss"));
        String data = JSONObject.toJSONString(body);
        JSONObject ret = send(body.getGantryId(), "NOTIFY_VIUPLOAD", data);
        if (ret == null) {
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    // 牌识图片按需调取
    @Override
    public AjaxResult VIPUpload(VIPUploadModel body) {
        body.setMsgId(genMsgId(body.getGantryId()));
        body.setMsgTime(DateUtils.dateTimeNow("yyyy-MM-dd'T'HH:mm:ss"));
        String data = JSONObject.toJSONString(body);
        JSONObject ret = send(body.getGantryId(), "NOTIFY_VIPUPLOAD", data);
        if (ret == null) {
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    // 交易重传
    @Override
    public AjaxResult ETCTUpload(ETCTUploadModel body) {
        body.setMsgId(genMsgId(body.getGantryId()));
        body.setMsgTime(DateUtils.dateTimeNow("yyyy-MM-dd'T'HH:mm:ss"));
        String data = JSONObject.toJSONString(body);
        JSONObject ret = send(body.getGantryId(), "NOTIFY_ETCTUPLOAD", data);
        if (ret == null) {
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    // 小时批次汇总重传
    @Override
    public AjaxResult HourSumUpload(HourSumUploadModel body) {
        body.setMsgId(genMsgId(body.getGantryId()));
        body.setMsgTime(DateUtils.dateTimeNow("yyyy-MM-dd'T'HH:mm:ss"));
        String data = JSONObject.toJSONString(body);
        JSONObject ret = send(body.getGantryId(), "NOTIFY_HOURSUMUPLOAD", data);
        if (ret == null) {
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    // 日志文件按需调取
    @Override
    public AjaxResult LogBUpload(LogBUploadModel body) {
        body.setMsgId(genMsgId(body.getGantryId()));
        body.setMsgTime(DateUtils.dateTimeNow("yyyy-MM-dd'T'HH:mm:ss"));
        String data = JSONObject.toJSONString(body);
        JSONObject ret = send(body.getGantryId(), "NOTIFY_LOGBUPLOAD", data);
        if (ret == null) {
            return AjaxResult.error();
        }
        return AjaxResult.success();
    }

    // 生成msgId
    String genMsgId(String gantryId) {
        return String.format("%s%s%02d", gantryId, DateUtils.dateTimeNow(), 1);
    }

    // 生成请求文件名
    String genFileName(String reqType, String gantryId) {
        return String.format("%s_REQ_%s_%s@_@json", reqType,
                DateUtils.dateTimeNow("yyyyMMddHHmmssSSSS"), gantryId.substring(0, 16));
    }

    // 获取请求的后台地址
    String getUrl(String reqType, String gantryId) {
        Example example = new Example(TblGantryInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gantryId", gantryId);
        TblGantryInfo tblGantryInfo = tblGantryInfoMapper.selectOneByExample(example);

        example = new Example(TblGantryInfoDtl.class);
        criteria = example.createCriteria();
        criteria.andEqualTo("InfoId", tblGantryInfo.getId());
        TblGantryInfoDtl tblGantryInfoDtl = tblGantryInfoDtlMapper.selectOneByExample(example);
        String url = String.format("http://%s:8080/etcdfs/api/%s/bin/%s", tblGantryInfoDtl.getMainIp(),
                gantryId.substring(0, 16), genFileName(reqType, gantryId));
        return url;
    }

    JSONObject send(String gantryId, String reqType, String data) {
        String url = getUrl(reqType, gantryId);
        String reqFileName = genFileName(reqType, gantryId);
        OkHttpClient client = new OkHttpClient();
        MultipartBody binFile = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("binFile", reqFileName, RequestBody.create(
                        MediaType.parse("application/octet-stream"), data.getBytes(StandardCharsets.UTF_8)))
                .build();
        Request req = new Request.Builder().url(url).post(binFile).build();
        JSONObject ret = null;
        try {
            // 改为同步模式，解析后再发送给前端？
            Call call = client.newCall(req);
            Response resp = call.execute();
            if (resp.isSuccessful()) {
                ret = JSONObject.parseObject(resp.body().string());
            }
            // 异步模式
//            call.enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    log.error("门架服务器返回失败：" + reqType + "," + e.getMessage());
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//                    JSONObject jo = JSONObject.parseObject(response.body().string());
//                    String fileName = jo.getString("binFile");
//                    if(!fileName.isEmpty()) {
//                        String respType = getRespType(fileName);
//                        switch (respType) {
//                            case "NOTIFY_GETERRORDATA": handleGetErrorData(jo); break;
//                            case "NOTIFY_FIXERRORDATA": handleFixErrorData(jo); break;
//                            case "NOTIFY_PICREALTRANSFER": handlePicRealTransfer(jo); break;
//                        }
//                    }
//                }
//            });
        } catch (Exception e) {
            log.error("发送请求至门架服务器失败：", e.getMessage());
        }
        return ret;
    }

    // 解析返回类型
    String getRespType(String respFileName) {
        int pos = respFileName.indexOf('_');
        pos = respFileName.indexOf('_', pos + 1);
        return respFileName.substring(0, pos);
    }

}
