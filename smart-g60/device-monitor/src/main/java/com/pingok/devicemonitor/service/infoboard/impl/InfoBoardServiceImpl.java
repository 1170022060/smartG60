package com.pingok.devicemonitor.service.infoboard.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.infoBoard.TblReleaseRecord;
import com.pingok.devicemonitor.domain.infoBoard.VmsInfo;
import com.pingok.devicemonitor.mapper.infoBoard.TblReleaseRecordMapper;
import com.pingok.devicemonitor.service.infoboard.IInfoBoardService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.security.utils.SecurityUtils;
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
    private RemoteReleaseService remoteReleaseService;

    @Autowired
    private TblReleaseRecordMapper tblReleaseRecordMapper;

    @Override
    public int publish(JSONObject pubInfo) {

        //更新发布记录状态（未发布）
//        List<VmsInfo> vmsInfoList = new ArrayList<>();
        JSONArray devInfoList = pubInfo.getJSONArray("devInfo");
        if (pubInfo.containsKey("data")) {
            JSONArray pubInfoList = pubInfo.getJSONArray("data");
            for (int i = 0; i < devInfoList.size(); ++i) {
                JSONObject joDev = (JSONObject) devInfoList.get(i);
                TblReleaseRecord record = tblReleaseRecordMapper.findByDeviceId(joDev.getString("devId"));
                joDev.put("id", record.getId());
                record.setStatus(0);
                if(joDev.getString("protocol") == "sansi_plist_multi")
                {
                    record.setPublishContent(JSON.toJSONString(pubInfoList));
                }
                else {
                    JSONArray jaList = pubInfoList.getJSONArray(0);
                    StringBuilder content = new StringBuilder();
                    StringBuilder typeface = new StringBuilder();
                    StringBuilder textSize = new StringBuilder();
                    StringBuilder textColor = new StringBuilder();
                    for(int j = 0; j < jaList.size(); ++j) {
                        JSONObject joText = (JSONObject) jaList.get(i);
                        content.append(joText.getString("content")).append('|');
                        typeface.append(joText.getString("typeface")).append('|');
                        textSize.append(joText.getString("textSize")).append('|');
                        textColor.append(joText.getString("textColor")).append('|');
                    }
                    record.setPresetInfo(content.toString());
                    record.setPresetInfo(typeface.toString());
                    record.setPresetInfo(textSize.toString());
                    record.setPresetInfo(textColor.toString());
                }
                record.setPresetUserId(SecurityUtils.getUserId());
                tblReleaseRecordMapper.updateByPrimaryKey(record);
            }
        } else {
            return 500;
        }

        //转发
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_INFOBOARD_PUBLISH);
        kafkaEnum.setData(JSON.toJSONString(pubInfo));
        remoteKafkaService.send(kafkaEnum);

        return 200;
    }

    @Override
    public int notifyResult(JSONArray result) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.WEBSOCKET_SEND);
        kafkaEnum.setData(JSON.toJSONString(result));
        remoteKafkaService.send(kafkaEnum);
        return 200;
    }
}
