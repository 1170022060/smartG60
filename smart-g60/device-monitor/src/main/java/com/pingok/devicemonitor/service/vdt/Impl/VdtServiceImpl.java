package com.pingok.devicemonitor.service.vdt.Impl;

import com.pingok.devicemonitor.domain.device.TblDeviceInfo;
import com.pingok.devicemonitor.mapper.device.TblDeviceInfoMapper;
import com.pingok.devicemonitor.service.vdt.IVdtService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.TblKafkaFailInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 * @time 2022/7/6 10:47
 */
@Slf4j
@Service
public class VdtServiceImpl implements IVdtService {

    @Autowired
    RemoteKafkaService remoteKafkaService;

    @Override
    public void collect() {
        //转发
        TblKafkaFailInfo kfk = new TblKafkaFailInfo();
        kfk.setTopIc(KafkaTopIc.MONITOR_SIGNAL_VDT);
        remoteKafkaService.send(kfk);
    }
}
