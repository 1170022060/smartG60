package com.pingok.devicemonitor.controller.device;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.device.TblDeviceFault;
import com.pingok.devicemonitor.domain.device.TblDeviceInfo;
import com.pingok.devicemonitor.domain.device.TblDeviceStatus;
import com.pingok.devicemonitor.service.device.IDeviceService;
import com.pingok.devicemonitor.service.heartbeat.IHeartbeatService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.security.utils.DictUtils;
import com.ruoyi.system.api.RemoteConfigService;
import com.ruoyi.system.api.RemoteEventService;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.SysDictData;
import com.ruoyi.system.api.domain.event.TblEventRecord;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @time 2022/4/13 16:30
 */
@RestController
@Slf4j
@RequestMapping("/deviceMonitor")
public class DeviceMonitorController extends BaseController {
    @Autowired
    private IHeartbeatService iHeartbeatService;
    @Autowired
    private IDeviceService iDeviceService;
    @Autowired
    private RemoteConfigService remoteConfigService;
    @Autowired
    private RemoteKafkaService remoteKafkaService;

    @Autowired
    private RemoteEventService remoteEventService;

    @GetMapping("/selectBydeviceType")
    public AjaxResult selectBydeviceType(@RequestParam(value = "deviceType") Integer deviceType) {
        return AjaxResult.success(iDeviceService.selectBydeviceType(deviceType));
    }

    @GetMapping("/selectByDeviceId")
    public AjaxResult selectByDeviceId(String deviceId) {
        return AjaxResult.success(iDeviceService.selectByDeviceId(deviceId));
    }

    @PostMapping
    public AjaxResult updateHeartbeat(@RequestBody TblDeviceStatus deviceStatus) {
        iDeviceService.updateStatus(deviceStatus);
        TblDeviceFault deviceFault = new TblDeviceFault();
        deviceFault.setDeviceId(deviceStatus.getDeviceId());
        deviceFault.setFaultDescription(deviceStatus.getStatusDesc());
        deviceFault.setFaultTime(DateUtils.getNowDate());
        deviceFault.setRegisterType(2);
        deviceFault.setFaultType(deviceStatus.getFaultType() != null ? deviceStatus.getFaultType() : "offLine");
        if (deviceStatus.getStatus() != null) {
            if (deviceStatus.getStatus() == 0) {
                iDeviceService.deviceFault(deviceFault);
            } else {
                iDeviceService.updateDeviceFault(deviceFault);
            }
        }
        if (StringUtils.isNotNull(deviceStatus.getStatusDetails()) && deviceStatus.getStatusDetails().startsWith("{")) {
            JSONObject obj = JSON.parseObject(deviceStatus.getStatusDetails());
            if (obj.containsKey("currentVisibility")) {
                Integer currentVisibility = obj.getInteger("currentVisibility");
                if (currentVisibility != -1) {
                    List<SysDictData> sysDictDataList = DictUtils.getDictCache("visibility_warning");
                    Integer level = 0;
                    for (SysDictData s : sysDictDataList) {
                        if (currentVisibility.intValue() < Integer.parseInt(s.getDictValue())) {
                            if (level.intValue() < s.getDictSort().intValue()) {
                                level = s.getDictSort().intValue();

                            }
                        }
                    }
                    if (level > 0) {
                        TblDeviceInfo info = iDeviceService.info(deviceStatus.getDeviceId());
                        TblEventRecord eventRecord;
                        R<TblEventRecord> re = remoteEventService.selectByEventTypeAndPileNo("23",info.getPileNo());
                        if(re.getCode()==R.SUCCESS && re.getData()!=null){
                            eventRecord = re.getData();
                            eventRecord.setEventTime(DateUtils.getNowDate());
                            eventRecord.setEventType("23");
                            eventRecord.setRemark(currentVisibility.toString());
                            eventRecord.setDirection("双向");
                            eventRecord.setLocationInterval(info.getGps());
                            eventRecord.setPileNo(info != null ? info.getPileNo() : null);
                            R r = remoteEventService.edit(eventRecord);
                            if (r.getCode() == R.FAIL) {
                                log.error("能见度预警事件更新失败");
                            }
                        }else {
                            eventRecord = new TblEventRecord();
                            eventRecord.setEventTime(DateUtils.getNowDate());
                            eventRecord.setEventType("23");
                            eventRecord.setRemark(currentVisibility.toString());
                            eventRecord.setDirection("双向");
                            eventRecord.setLocationInterval(info.getGps());
                            eventRecord.setPileNo(info != null ? info.getPileNo() : null);
                            R r = remoteEventService.add(eventRecord);
                            if (r.getCode() == R.FAIL) {
                                log.error("能见度预警事件新增失败");
                            }
                        }

                    }
                }
            }
        }
        return AjaxResult.success();
    }

    @GetMapping
    public AjaxResult serverHeartbeat() {
        return getHeartbeat("serverSnmp");
    }

    @GetMapping("/pingHeartbeat")
    public AjaxResult pingHeartbeat() {
        List<TblDeviceInfo> deviceInfos = iDeviceService.findByProtocol("ping");
        if (deviceInfos != null && deviceInfos.size() > 0) {
            List<TblDeviceInfo> daasArray = new ArrayList<>();
            List<TblDeviceInfo> monitorArray = new ArrayList<>();
            List<TblDeviceInfo> chargeArray = new ArrayList<>();
            R<String> r;
            String chargeIp = null;
            String monitorIp = null;
            String daasIp = null;
            List<SysDictData> sysDictDataList = DictUtils.getDictCache("network_segment");
            if (sysDictDataList == null) {
                AjaxResult.error("未配置网段信息");
            }
            for (SysDictData d : sysDictDataList) {
                switch (d.getDictLabel()) {
                    case "charge":
                        chargeIp = d.getDictValue();
                        break;
                    case "monitor":
                        monitorIp = d.getDictValue();
                        break;
                    case "daas":
                        daasIp = d.getDictValue();
                        break;
                }
            }

            for (TblDeviceInfo d : deviceInfos) {
                if (chargeIp != null) {
                    if (d.getDeviceIp().startsWith(chargeIp)) {
                        chargeArray.add(d);
                    }
                }
                if (monitorIp != null) {
                    if (d.getDeviceIp().startsWith(monitorIp)) {
                        monitorArray.add(d);
                    }
                }
                if (daasIp != null) {
                    if (d.getDeviceIp().startsWith(daasIp)) {
                        daasArray.add(d);
                    }
                }
            }
            KafkaEnum kafkaEnum;
            if (chargeArray != null && chargeArray.size() > 0) {
                kafkaEnum = new KafkaEnum();
                kafkaEnum.setTopIc(KafkaTopIc.CHARGE_SIGNAL_PING_STATUS);
                kafkaEnum.setData(JSON.toJSONString(chargeArray));
                remoteKafkaService.send(kafkaEnum);
            }
            if (monitorArray != null && monitorArray.size() > 0) {
                kafkaEnum = new KafkaEnum();
                kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PING_STATUS);
                kafkaEnum.setData(JSON.toJSONString(monitorArray));
                remoteKafkaService.send(kafkaEnum);
            }
            if (daasArray != null && daasArray.size() > 0) {
                TblDeviceStatus deviceStatus;
                boolean online;
                JSONObject jsonObject;
                BigDecimal threshold;
                StringBuffer statusDesc = new StringBuffer();
                TblDeviceFault deviceFault;
                for (TblDeviceInfo d : daasArray) {
                    deviceStatus = new TblDeviceStatus();
                    deviceStatus.setDeviceId(d.getId());
                    deviceStatus.setTime(DateUtils.getNowDate());
                    deviceStatus.setStatus(1);
                    deviceStatus.setStatusDesc("正常");
                    online = iHeartbeatService.ping(d.getDeviceIp());
                    if (online) {
                        deviceStatus.setStatus(1);
                        deviceStatus.setStatusDesc("在线");
                        iDeviceService.updateStatus(deviceStatus);
                    } else {
                        deviceStatus.setStatus(0);
                        deviceStatus.setStatusDesc("网络异常");
                        iDeviceService.updateStatus(deviceStatus);

                        deviceFault = new TblDeviceFault();
                        deviceFault.setDeviceId(d.getId());
                        deviceFault.setFaultId("offline");
                        deviceFault.setFaultDescription("网络异常");
                        deviceFault.setFaultTime(DateUtils.getNowDate());
                        deviceFault.setRegisterType(2);
                        deviceFault.setFaultType("warning");
                        iDeviceService.deviceFault(deviceFault);
                    }
                }
            }
        }
        return AjaxResult.success();
    }

    @GetMapping("/switchHeartbeat")
    public AjaxResult switchHeartbeat() {
        return getHeartbeat("switchSnmp");
    }

    @GetMapping("/firewallHeartbeat")
    public AjaxResult firewallHeartbeat() {
        return getHeartbeat("firewallSnmp");
    }

    private AjaxResult getHeartbeat(String type) {
        List<TblDeviceInfo> deviceInfos = iDeviceService.findByProtocol(type);
        if (deviceInfos != null && deviceInfos.size() > 0) {
            List<TblDeviceInfo> daasArray = new ArrayList<>();
            List<TblDeviceInfo> monitorArray = new ArrayList<>();
            List<TblDeviceInfo> chargeArray = new ArrayList<>();
            R<String> r;
            String chargeIp = null;
            String monitorIp = null;
            String daasIp = null;
            List<SysDictData> sysDictDataList = DictUtils.getDictCache("network_segment");
            if (sysDictDataList == null) {
                AjaxResult.error("未配置网段信息");
            }
            for (SysDictData d : sysDictDataList) {
                switch (d.getDictLabel()) {
                    case "charge":
                        chargeIp = d.getDictValue();
                        break;
                    case "monitor":
                        monitorIp = d.getDictValue();
                        break;
                    case "daas":
                        daasIp = d.getDictValue();
                        break;
                }
            }

            for (TblDeviceInfo d : deviceInfos) {
                if (chargeIp != null) {
                    if (d.getDeviceIp().startsWith(chargeIp)) {
                        chargeArray.add(d);
                    }
                }
                if (monitorIp != null) {
                    if (d.getDeviceIp().startsWith(monitorIp)) {
                        monitorArray.add(d);
                    }
                }
                if (daasIp != null) {
                    if (d.getDeviceIp().startsWith(daasIp)) {
                        daasArray.add(d);
                    }
                }
            }
            KafkaEnum kafkaEnum;
            String topIcCharge = "", topIcMonitor = "";
            switch (type) {
                case "serverSnmp":
                    topIcCharge = KafkaTopIc.CHARGE_SIGNAL_SERVER_STATUS;
                    topIcMonitor = KafkaTopIc.MONITOR_SIGNAL_SERVER_STATUS;
                    break;
                case "switchSnmp":
                    topIcCharge = KafkaTopIc.CHARGE_SIGNAL_SWITCH_STATUS;
                    topIcMonitor = KafkaTopIc.MONITOR_SIGNAL_SWITCH_STATUS;
                    break;
                case "firewallSnmp":
                    topIcCharge = KafkaTopIc.CHARGE_SIGNAL_FIREWALL_STATUS;
                    topIcMonitor = KafkaTopIc.MONITOR_SIGNAL_FIREWALL_STATUS;
                    break;
            }
            if (chargeArray != null && chargeArray.size() > 0) {
                kafkaEnum = new KafkaEnum();
                kafkaEnum.setTopIc(topIcCharge);
                kafkaEnum.setData(JSON.toJSONString(chargeArray));
                remoteKafkaService.send(kafkaEnum);
            }
            if (monitorArray != null && monitorArray.size() > 0) {
                kafkaEnum = new KafkaEnum();
                kafkaEnum.setTopIc(topIcMonitor);
                kafkaEnum.setData(JSON.toJSONString(monitorArray));
                remoteKafkaService.send(kafkaEnum);
            }
            if (daasArray != null && daasArray.size() > 0) {
                TblDeviceStatus deviceStatus;
                boolean online;
                JSONObject jsonObject;
                BigDecimal threshold;
                StringBuffer statusDesc = new StringBuffer();
                TblDeviceFault deviceFault;
                for (TblDeviceInfo d : daasArray) {
                    deviceStatus = new TblDeviceStatus();
                    deviceStatus.setDeviceId(d.getId());
                    deviceStatus.setTime(DateUtils.getNowDate());
                    deviceStatus.setStatus(1);
                    deviceStatus.setStatusDesc("正常");
                    online = iHeartbeatService.ping(d.getDeviceIp());
                    if (online) {
                        jsonObject = iHeartbeatService.serverSnmp(d.getDeviceIp());
                        r = remoteConfigService.getConfigKey("cup.threshold");
                        if (R.SUCCESS == r.getCode()) {
                            threshold = new BigDecimal(r.getData());
                            if (jsonObject.getBigDecimal("cpu").compareTo(threshold) > -1) {
                                deviceStatus.setStatus(0);
                                statusDesc.append("|CPU占用率过高|");
                            }
                        }
                        r = remoteConfigService.getConfigKey("memory.threshold");
                        if (R.SUCCESS == r.getCode()) {
                            threshold = new BigDecimal(r.getData());
                            if (jsonObject.getBigDecimal("memoryUsage").compareTo(threshold) > -1) {
                                deviceStatus.setStatus(0);
                                statusDesc.append("|内存占用率过高|");
                                jsonObject.put("memory", 0);
                            } else {
                                jsonObject.put("memory", 1);
                            }
                        }
                        r = remoteConfigService.getConfigKey("disk.threshold");
                        if (R.SUCCESS == r.getCode()) {
                            threshold = new BigDecimal(r.getData());
                            if (jsonObject.getBigDecimal("storageUsage").compareTo(threshold) > -1) {
                                deviceStatus.setStatus(0);
                                statusDesc.append("|硬盘占用率过高|");
                                jsonObject.put("storage", 0);

                            } else {
                                jsonObject.put("storage", 1);
                            }
                        }
                        if (statusDesc != null) {
                            deviceStatus.setStatusDesc(statusDesc.toString());
                        }
                        deviceStatus.setStatusDetails(jsonObject.toJSONString());

                        iDeviceService.updateStatus(deviceStatus);
                        if (deviceStatus.getStatus() == 0) {
                            deviceFault = new TblDeviceFault();
                            deviceFault.setDeviceId(d.getId());
                            deviceFault.setFaultId("hardware");
                            deviceFault.setFaultDescription(statusDesc.toString());
                            deviceFault.setFaultTime(DateUtils.getNowDate());
                            deviceFault.setRegisterType(2);
                            deviceFault.setFaultType("remind");
                            iDeviceService.deviceFault(deviceFault);
                        }
                    } else {
                        deviceStatus.setStatus(0);
                        deviceStatus.setStatusDesc("网络异常");
                        iDeviceService.updateStatus(deviceStatus);

                        deviceFault = new TblDeviceFault();
                        deviceFault.setDeviceId(d.getId());
                        deviceFault.setFaultId("offline");
                        deviceFault.setFaultDescription("网络异常");
                        deviceFault.setFaultTime(DateUtils.getNowDate());
                        deviceFault.setRegisterType(2);
                        deviceFault.setFaultType("warning");
                        iDeviceService.deviceFault(deviceFault);
                    }
                }
            }
        }
        return AjaxResult.success();
    }
}
