package com.pingok.monitor.domain.device;

import com.pingok.monitor.domain.device.vo.DeviceInfoVo;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BaseInfo {
    List<Map> baseStation;
    List<Map> bridgeInfo;
    List<DeviceInfoVo> VMSInfo;
    List<DeviceInfoVo> VDInfo;
    List<DeviceInfoVo> CAMInfo;
    List<DeviceInfoVo> pilotLightInfo;
}
