package com.pingok.devicemonitor.service.infoboard.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.device.TblDeviceInfo;
import com.pingok.devicemonitor.domain.device.TblDeviceStatus;
import com.pingok.devicemonitor.domain.infoBoard.DevInfo;
import com.pingok.devicemonitor.domain.infoBoard.TblReleaseRecord;
import com.pingok.devicemonitor.domain.infoBoard.VmsInfo;
import com.pingok.devicemonitor.mapper.device.TblDeviceInfoMapper;
import com.pingok.devicemonitor.mapper.device.TblDeviceStatusMapper;
import com.pingok.devicemonitor.mapper.infoBoard.TblReleaseRecordMapper;
import com.pingok.devicemonitor.service.infoboard.IInfoBoardService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.ip.IpUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.RemoteReleaseService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @time 2022/4/13 16:32
 */
@Slf4j
@Service
public class InfoBoardServiceImpl implements IInfoBoardService {


    @Autowired
    private RemoteKafkaService remoteKafkaService;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblReleaseRecordMapper tblReleaseRecordMapper;
    @Autowired
    private TblDeviceInfoMapper tblDeviceInfoMapper;
    @Autowired
    private TblDeviceStatusMapper tblDeviceStatusMapper;

    @Override
    public int publish(JSONObject pubInfo) {
        //转发
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_INFOBOARD_PUBLISH);
        kafkaEnum.setData(JSON.toJSONString(pubInfo));
        remoteKafkaService.send(kafkaEnum);
        return 200;
    }

    @Override
    public int notifyResult(JSONObject result) {

        //更新发布记录状态
        String pubContent = result.getString("pubContent");
        JSONArray devList = result.getJSONArray("devList");
        for (int i = 0; i < devList.size(); ++i) {
            JSONObject dev = devList.getJSONObject(i);
            TblReleaseRecord record = tblReleaseRecordMapper.findByDeviceId(dev.getString("devId"));
            record.setStatus(0);
            record.setPublishContent(pubContent);
            record.setPresetUserId(SecurityUtils.getUserId());
            tblReleaseRecordMapper.updateByPrimaryKey(record);
        }
        //通知前端（websocket）
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.WEBSOCKET_SEND);
        kafkaEnum.setData(JSON.toJSONString(result));
        remoteKafkaService.send(kafkaEnum);
        return 200;
    }

    @Override
    public int collect() {
        int ret = 200;
        try {
            List<TblDeviceInfo> allDevList = tblDeviceInfoMapper.selectVmsInfo();
            KafkaEnum kafkaEnum = new KafkaEnum();
            kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_INFOBOARD_PING);
            kafkaEnum.setData(JSON.toJSONString(allDevList));
            remoteKafkaService.send(kafkaEnum);
        } catch (Exception e) {
            log.error("情报板采集异常：" + e.getMessage());
            ret = -1;
        }
        return ret;
    }

    @Override
    public int notifyPing(JSONArray result) {
        //更新状态表
        int ret = 200;

        for(int i = 0; i < result.size(); ++i) {
            JSONObject jo = result.getJSONObject(i);
            Long devId = jo.getLong("devId");
            Integer ping = jo.getBoolean("ping") ? 0 : 1;
            boolean bExist = true;
            TblDeviceStatus devStatus = tblDeviceStatusMapper.findByDeviceId(devId);
            if(devStatus == null) {
                devStatus = new TblDeviceStatus();
                devStatus.setId(remoteIdProducerService.nextId());
                bExist = false;
            }
            devStatus.setDeviceId(devId);
            devStatus.setStatus(ping);
            devStatus.setStatusDesc(ping == 0 ? "正常" : "离线");
            devStatus.setTime(DateUtils.getNowDate());
            if(bExist) tblDeviceStatusMapper.updateByPrimaryKey(devStatus);
            else tblDeviceStatusMapper.insert(devStatus);
        }

        return ret;
    }
}
