package com.pingok.devicemonitor.service.vdt.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.device.TblDeviceInfo;
import com.pingok.devicemonitor.domain.vdt.VDStatus;
import com.pingok.devicemonitor.mapper.device.TblDeviceInfoMapper;
import com.pingok.devicemonitor.service.vdt.IVdtService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class VdtServiceImpl implements IVdtService {

    @Autowired
    private TblDeviceInfoMapper tblDeviceInfoMapper;
    @Autowired
    private RemoteKafkaService remoteKafkaService;

    @Override
    public void collect() {
        try {
            List<TblDeviceInfo> allDevList = tblDeviceInfoMapper.selectVdtInfo();
            KafkaEnum kafkaEnum = new KafkaEnum();
            kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_VDT);
            kafkaEnum.setData(JSON.toJSONString(allDevList));
            remoteKafkaService.send(kafkaEnum);
        } catch (Exception e) {
            log.error("情报板采集异常：" + e.getMessage());
        }
    }

    @Override
    public void notifyResult(JSONObject result) {
        // 更新db
        List<VDStatus> liRet = JSONArray.parseArray(result.toJSONString(), VDStatus.class);

        for (VDStatus status : liRet) {

        }
    }
}
