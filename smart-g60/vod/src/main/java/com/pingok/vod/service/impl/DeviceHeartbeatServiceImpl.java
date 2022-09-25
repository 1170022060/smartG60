package com.pingok.vod.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.vod.domain.TblDeviceInfo;
import com.pingok.vod.domain.TblDeviceStatus;
import com.pingok.vod.domain.TblDeviceStatusLog;
import com.pingok.vod.mapper.TblDeviceStatusLogMapper;
import com.pingok.vod.mapper.TblDeviceStatusMapper;
import com.pingok.vod.service.IDeviceHeartbeatService;
import com.pingok.vod.service.IDeviceInfoService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * 设备心跳 服务层处理
 *
 * @author qiumin
 */
@Service
public class DeviceHeartbeatServiceImpl implements IDeviceHeartbeatService {

    @Autowired
    private IDeviceInfoService iDeviceInfoService;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblDeviceStatusMapper tblDeviceStatusMapper;
    @Autowired
    private TblDeviceStatusLogMapper tblDeviceStatusLogMapper;
    @Autowired
    private RemoteKafkaService remoteKafkaService;

    @Override
    public void heartbeat(JSONArray heartbeat) {
        TblDeviceInfo tblDeviceInfo;
        TblDeviceStatus tblDeviceStatus;
        TblDeviceStatusLog tblDeviceStatusLog;
        JSONObject o;
        Example example;
        JSONArray array = new JSONArray();
        JSONObject status;
        for (int i = 0; i < heartbeat.size(); i++) {
            o = heartbeat.getJSONObject(i);
            tblDeviceInfo = iDeviceInfoService.findByDeviceId(o.getString("id"));
            if (tblDeviceInfo != null) {
                example = new Example(TblDeviceStatus.class);
                example.createCriteria().andEqualTo("deviceId", tblDeviceInfo.getId());
                tblDeviceStatus = tblDeviceStatusMapper.selectOneByExample(example);
                if (tblDeviceStatus == null) {
                    tblDeviceStatus = new TblDeviceStatus();
                    tblDeviceStatus.setId(remoteIdProducerService.nextId());
                    tblDeviceStatus.setDeviceId(tblDeviceInfo.getId());
                    tblDeviceStatusMapper.insert(tblDeviceStatus);
                }
                switch (o.getString("status")) {
                    case "ONLINE":
                        tblDeviceStatus.setStatus(1);
                        tblDeviceStatus.setStatusDesc("在线");
                        break;
                    case "OFFLINE":
                        tblDeviceStatus.setStatus(0);
                        tblDeviceStatus.setStatusDesc("离线");
                        break;
                }
                tblDeviceStatus.setTime(DateUtils.getNowDate());
                tblDeviceStatusMapper.updateByPrimaryKey(tblDeviceStatus);

                tblDeviceStatusLog = new TblDeviceStatusLog();
                tblDeviceStatusLog.setId(remoteIdProducerService.nextId());
                tblDeviceStatusLog.setDeviceId(tblDeviceStatus.getDeviceId());
                tblDeviceStatusLog.setStatus(tblDeviceStatus.getStatus());
                tblDeviceStatusLog.setStatusDesc(tblDeviceStatus.getStatusDesc());
                tblDeviceStatusLog.setTime(tblDeviceStatus.getTime());
                tblDeviceStatusLogMapper.insert(tblDeviceStatusLog);

                status = new JSONObject();
                status.put("id", tblDeviceInfo.getId());
                status.put("deviceId", tblDeviceInfo.getDeviceId());
                status.put("status", tblDeviceStatus.getStatus());
                status.put("statusDesc", tblDeviceStatus.getStatusDesc());
                status.put("time", tblDeviceStatus.getTime());
                array.add(status);
            }
        }
    }
}
