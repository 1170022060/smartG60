package com.pingok.devicemonitor.service.infoboard.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.service.infoboard.IInfoBoardService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.RemoteReleaseService;
import com.ruoyi.system.api.domain.kafuka.TblKafkaFailInfo;
import com.ruoyi.system.api.domain.release.TblReleaseRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private RemoteReleaseService remoteReleaseService;

    @Override
    public void publish(JSONObject vmsPublishInfo) {
        TblKafkaFailInfo tblKafkaFailInfo = new TblKafkaFailInfo();
        tblKafkaFailInfo.setTopIc(KafkaTopIc.MONITOR_SIGNAL_INFOBOARD_PUBLISH);
        tblKafkaFailInfo.setData(JSON.toJSONString(vmsPublishInfo));
        remoteKafkaService.send(tblKafkaFailInfo);

        TblReleaseRecord tblReleaseRecord = new TblReleaseRecord();
        tblReleaseRecord.setDeviceId(vmsPublishInfo.getLong("deviceId"));
        if (vmsPublishInfo.containsKey("fmsValue")) {
            tblReleaseRecord.setPresetInfo(vmsPublishInfo.getString("fmsValue"));
        }
        if (vmsPublishInfo.containsKey("info")) {
            JSONArray infos = vmsPublishInfo.getJSONArray("info");
            if (infos != null && infos.size() > 0) {
                JSONObject info;
                int size = infos.size();
                for (int i = 0; i < size; i++) {
                    info = infos.getJSONObject(i);
                    tblReleaseRecord.setPresetInfo(info.getString("text"));
                    tblReleaseRecord.setColor(info.getString("fontColor"));
                    tblReleaseRecord.setPictureType(info.getInteger("picId"));
                    tblReleaseRecord.setTypeface(info.getString("font"));
                    tblReleaseRecord.setTypefaceSize(info.getInteger("fontSize"));
                    remoteReleaseService.add(tblReleaseRecord);
                }
            }
        } else {
            remoteReleaseService.add(tblReleaseRecord);
        }
    }
}
