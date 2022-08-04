package com.pingok.external.service.bridge.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.config.BridgeConfig;
import com.pingok.external.domain.bridge.*;
import com.pingok.external.mapper.bridge.*;
import com.pingok.external.service.bridge.IBridgeService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.system.api.RemoteEventService;
import com.ruoyi.system.api.domain.event.TblEventRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class BridgeServiceImpl implements IBridgeService {


    @Autowired
    private RemoteEventService remoteEventService;
    @Autowired
    private TblBridgeProjectMapper tblBridgeProjectMapper;
    @Autowired
    private TblBridgeInfoMapper tblBridgeInfoMapper;
    @Autowired
    private TblBridgeCollectionMapper tblBridgeCollectionMapper;
    @Autowired
    private TblBridgeAcquisitionMapper tblBridgeAcquisitionMapper;
    @Autowired
    private TblBridgeWarningMapper tblBridgeWarningMapper;


    @Override
    public TblBridgeInfo selectBridgeInfoById(Long id) {
        return tblBridgeInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public void updateWarning() {
        Example example = new Example(TblBridgeWarning.class);
        example.createCriteria()
                .andEqualTo("treatmentStatus", 0);
        List<TblBridgeWarning> bridgeWarnings = tblBridgeWarningMapper.selectByExample(example);
        String r;
        for (TblBridgeWarning warning : bridgeWarnings) {
            r = HttpUtil.get(BridgeConfig.HOST + "/monitor/alarm_record/" + BridgeConfig.TOKEN + "/" + warning.getId());
            if (!StringUtils.isEmpty(r)) {
                JSONObject ret = JSONObject.parseObject(r);
                log.info(ret.toJSONString());
                if (ret.getInteger("code") == 200) {
                    ret = ret.getJSONObject("data");
                    log.info(ret.toJSONString());
                    if (!ret.isEmpty()) {
                        BeanUtils.copyNotNullProperties(JSON.parseObject(ret.toJSONString(), TblBridgeWarning.class), warning);
                        warning.setUpdateTime(DateUtils.getNowDate());
                        tblBridgeWarningMapper.updateByPrimaryKey(warning);
                    }
                }
            }
        }
    }

    @Override
    public void getWarning() {
        Date endTime = DateUtils.getNowDate();
        Date startTime = DateUtils.getPreTime(endTime, -5);
        String r;
        List<TblBridgeInfo> bridgeInfos = tblBridgeInfoMapper.selectAll();
        TblEventRecord eventRecord;
        R re;
        String host;
        for (TblBridgeInfo bridge : bridgeInfos) {
            host = BridgeConfig.HOST + "/monitor/alarm_records_by_range_time/" + BridgeConfig.TOKEN + "/" + bridge.getId() + "/" + DateUtils.dateTime(startTime, DateUtils.YYYYMMDDHHMMSS) + "/" + DateUtils.dateTime(endTime, DateUtils.YYYYMMDDHHMMSS);
            r = HttpUtil.get(host);
            if (!StringUtils.isEmpty(r)) {
                JSONObject ret = JSONObject.parseObject(r);
                if (ret.getInteger("code") == 200) {
                    JSONArray array = ret.getJSONArray("data");
                    log.info(array.toJSONString());
                    if (!array.isEmpty()) {
                        TblBridgeWarning warning;
                        int size = array.size();
                        for (int i = 0; i < size; i++) {
                            warning = JSON.parseObject(array.getJSONObject(i).toJSONString(), TblBridgeWarning.class);
                            warning.setCreateTime(DateUtils.getNowDate());
                            warning.setUpdateTime(DateUtils.getNowDate());
                            tblBridgeWarningMapper.insert(warning);
                            eventRecord = new TblEventRecord();
                            eventRecord.setEventType("30");
                            eventRecord.setLocationInterval(bridge.getLongitude() + "," + bridge.getLatitude());
                            eventRecord.setEventTime(warning.getAlarmTime());
                            eventRecord.setStatus(0);
                            eventRecord.setRemark(bridge.getName() + "----" + warning.getIncidentDescription());
                            re = remoteEventService.add(eventRecord);
                            if (re.getCode() == R.FAIL) {
                                log.error(bridge.getName() + "--id为：" + warning.getId() + "--事件推送失败：" + re.getMsg());
                            }
                        }
                    }
                } else {
                    throw new ServiceException("获取桥梁预警信息失败：" + ret.getString("msg"));
                }
            }
        }
    }

    @Override
    public void updateAcquisition() {
        String r;
        List<TblBridgeInfo> bridgeInfos = tblBridgeInfoMapper.selectAll();
        for (TblBridgeInfo bridge : bridgeInfos) {
            r = HttpUtil.get(BridgeConfig.HOST + "/monitor/sensors/" + BridgeConfig.TOKEN + "/" + bridge.getId());
            if (!StringUtils.isEmpty(r)) {
                JSONObject ret = JSONObject.parseObject(r);
                if (ret.getInteger("code") == 200) {
                    JSONArray array = ret.getJSONArray("data");
//                    log.info(array.toJSONString());
                    if (!array.isEmpty()) {
                        TblBridgeAcquisition acquisition;
                        JSONObject object;
                        int size = array.size();
                        for (int i = 0; i < size; i++) {
                            object = array.getJSONObject(i);
                            acquisition = tblBridgeAcquisitionMapper.selectByPrimaryKey(object.getLong("id"));
                            if (acquisition == null) {
                                acquisition = JSON.parseObject(object.toJSONString(), TblBridgeAcquisition.class);
                                acquisition.setCreateTime(DateUtils.getNowDate());
                                acquisition.setUpdateTime(DateUtils.getNowDate());
                                tblBridgeAcquisitionMapper.insert(acquisition);
                            } else {
                                BeanUtils.copyNotNullProperties(JSON.parseObject(object.toJSONString(), TblBridgeAcquisition.class), acquisition);
                                acquisition.setUpdateTime(DateUtils.getNowDate());
                                tblBridgeAcquisitionMapper.updateByPrimaryKey(acquisition);
                            }
                            if (acquisition.getStatus() == 1) {
                                bridge.setDeviceStatus(0);
                            }
                        }
                        tblBridgeInfoMapper.updateByPrimaryKey(bridge);
                    }
                } else {
                    throw new ServiceException("传感器信息更新失败：" + ret.getString("msg"));
                }
            }
        }
    }

    @Override
    public void updateCollection() {
        String r;
        List<TblBridgeInfo> bridgeInfos = tblBridgeInfoMapper.selectAll();
        for (TblBridgeInfo bridge : bridgeInfos) {
            r = HttpUtil.get(BridgeConfig.HOST + "/monitor/acquisitions/" + BridgeConfig.TOKEN + "/" + bridge.getId());
            if (!StringUtils.isEmpty(r)) {
                JSONObject ret = JSONObject.parseObject(r);
                if (ret.getInteger("code") == 200) {
                    JSONArray array = ret.getJSONArray("data");
//                    log.info(array.toJSONString());
                    if (!array.isEmpty()) {
                        TblBridgeCollection collection;
                        JSONObject object;
                        int size = array.size();
                        for (int i = 0; i < size; i++) {
                            object = array.getJSONObject(i);
                            collection = tblBridgeCollectionMapper.selectByPrimaryKey(object.getLong("id"));
                            if (collection == null) {
                                collection = JSON.parseObject(object.toJSONString(), TblBridgeCollection.class);
                                collection.setCreateTime(DateUtils.getNowDate());
                                collection.setUpdateTime(DateUtils.getNowDate());
                                tblBridgeCollectionMapper.insert(collection);
                            } else {
                                BeanUtils.copyNotNullProperties(JSON.parseObject(object.toJSONString(), TblBridgeCollection.class), collection);
                                collection.setUpdateTime(DateUtils.getNowDate());
                                tblBridgeCollectionMapper.updateByPrimaryKey(collection);
                            }
                            if (collection.getStatus() == 1) {
                                bridge.setDeviceStatus(0);
                            }
                        }
                        tblBridgeInfoMapper.updateByPrimaryKey(bridge);
                    }
                } else {
                    throw new ServiceException("采集仪信息更新失败：" + ret.getString("msg"));
                }
            }
        }
    }

    @Override
    public void updateBridgeInfo() {
        String r;
        List<TblBridgeProject> bridgeProjects = tblBridgeProjectMapper.selectAll();
        for (TblBridgeProject project : bridgeProjects) {
            r = HttpUtil.get(BridgeConfig.HOST + "/monitor/bridges/" + BridgeConfig.TOKEN + "/" + project.getId());
            if (!StringUtils.isEmpty(r)) {
                JSONObject ret = JSONObject.parseObject(r);
                if (ret.getInteger("code") == 200) {
                    JSONArray array = ret.getJSONArray("data");
//                    log.info(array.toJSONString());
                    if (!array.isEmpty()) {
                        TblBridgeInfo bridgeInfo;
                        JSONObject object;
                        int size = array.size();
                        for (int i = 0; i < size; i++) {
                            object = array.getJSONObject(i);
                            bridgeInfo = tblBridgeInfoMapper.selectByPrimaryKey(object.getLong("id"));
                            if (bridgeInfo == null) {
                                bridgeInfo = JSON.parseObject(object.toJSONString(), TblBridgeInfo.class);
                                bridgeInfo.setCreateTime(DateUtils.getNowDate());
                                bridgeInfo.setUpdateTime(DateUtils.getNowDate());
                                tblBridgeInfoMapper.insert(bridgeInfo);
                            } else {
                                BeanUtils.copyNotNullProperties(JSON.parseObject(object.toJSONString(), TblBridgeInfo.class), bridgeInfo);
                                bridgeInfo.setUpdateTime(DateUtils.getNowDate());
                                tblBridgeInfoMapper.updateByPrimaryKey(bridgeInfo);
                            }
                        }
                    }
                } else {
                    throw new ServiceException("桥梁信息更新失败：" + ret.getString("msg"));
                }
            }
        }
    }

    @Override
    public void updateProjectInfo() {
        String r = HttpUtil.get(BridgeConfig.HOST + "/monitor/projects/" + BridgeConfig.TOKEN);
        if (!StringUtils.isEmpty(r)) {
            JSONObject ret = JSONObject.parseObject(r);
            if (ret.getInteger("code") == 200) {
                JSONArray array = ret.getJSONArray("data");
//                log.info(array.toJSONString());
                if (!array.isEmpty()) {
                    TblBridgeProject bridgeProject;
                    JSONObject object;
                    int size = array.size();
                    for (int i = 0; i < size; i++) {
                        object = array.getJSONObject(i);
                        bridgeProject = tblBridgeProjectMapper.selectByPrimaryKey(object.getLong("id"));
                        if (bridgeProject == null) {
                            bridgeProject = JSON.parseObject(object.toJSONString(), TblBridgeProject.class);
                            bridgeProject.setCreateTime(DateUtils.getNowDate());
                            bridgeProject.setUpdateTime(DateUtils.getNowDate());
                            tblBridgeProjectMapper.insert(bridgeProject);
                        } else {
                            BeanUtils.copyNotNullProperties(JSON.parseObject(object.toJSONString(), TblBridgeProject.class), bridgeProject);
                            bridgeProject.setUpdateTime(DateUtils.getNowDate());
                            tblBridgeProjectMapper.updateByPrimaryKey(bridgeProject);
                        }
                    }
                }
            } else {
                throw new ServiceException("桥梁项目信息更新失败：" + ret.getString("msg"));
            }
        }
    }
}
