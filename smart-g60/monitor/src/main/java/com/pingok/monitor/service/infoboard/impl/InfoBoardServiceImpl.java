package com.pingok.monitor.service.infoboard.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.domain.infoBoard.VmsInfo;
import com.pingok.monitor.service.infoboard.IInfoBoardService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.RemoteReleaseService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import com.ruoyi.system.api.domain.release.TblReleaseRecord;
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
    private RemoteReleaseService remoteReleaseService;

    @Override
    public int publish(JSONObject pubInfo) {
        //转发
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_INFOBOARD_PUBLISH);
        kafkaEnum.setData(JSON.toJSONString(pubInfo));
        remoteKafkaService.send(kafkaEnum);

        //发布记录
        List<VmsInfo> vmsInfoList = new ArrayList<>();
        String deviceIds = pubInfo.getString("deviceId");
        if (pubInfo.containsKey("info")) {
            JSONArray infoList = pubInfo.getJSONArray("info");
            if (infoList != null && infoList.size() > 0) {
                vmsInfoList = JSON.parseArray(JSONObject.toJSONString(infoList), VmsInfo.class);
            }
        } else {
            return 500;
        }
        String[] ids = deviceIds.split("|");
        String br = "|";
        for(int i = 0; i < ids.length; ++i) {
            TblReleaseRecord rec = new TblReleaseRecord();
            rec.setDeviceId(ids[i]);
            for (VmsInfo info : vmsInfoList) {
                rec.setPresetInfo(rec.getPresetInfo() + br + info.getText());
                rec.setColor(rec.getColor() + br + info.getFontColor());
                rec.setTypeface(rec.getTypeface() + br + info.getFont());
                rec.setTypefaceSize(rec.getTypefaceSize() + br + info.getFontSize());
                remoteReleaseService.add(rec);
            }
        }
        return 200;
    }
}
