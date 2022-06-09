package com.pingok.devicemonitor.service.heartbeat.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.service.heartbeat.IHeartbeatService;
import com.ruoyi.common.core.utils.NetUtil;
import com.ruoyi.common.core.utils.snmp.SnmpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author
 * @time 2022/4/13 16:32
 */
@Slf4j
@Service
public class HeartbeatServiceImpl implements IHeartbeatService {

    @Override
    public boolean ping(String ip) {
        boolean online = false;
        try {
            online = NetUtil.ping(ip);
        } catch (IOException e) {
            log.error("ping " + ip + " 失败，报错：" + e.getMessage());
        }
        return online;
    }

    @Override
    public JSONObject serverSnmp(String ip) {
        JSONObject object = new JSONObject();
        SnmpUtil snmpUtil;
        try {
            snmpUtil = new SnmpUtil(ip, 161);
            object.put("cpu", snmpUtil.getCpuUsage());
            object.put("storageUsage", snmpUtil.getDiskUsage());
            object.put("storageDesc", snmpUtil.getDiskUsed() + "GB/" + snmpUtil.getDiskSize() + "GB");
            object.put("memoryUsage", snmpUtil.getMemoryUsage());
            object.put("memoryDesc", snmpUtil.getMemoryUsed() + "GB/" + snmpUtil.getMemoryDesc() + "GB");
        } catch (IOException e) {
            log.info("snmp执行失败：" + e.getMessage());
        }
        return object;
    }
}
