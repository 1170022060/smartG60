package com.pingok.monitor.domain.vo;


import com.pingok.monitor.domain.deviceInfo.ViewMonitorDeviceInfo;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BasicInfo {
   private List<Map> baseStationInfos;
   private List<ViewMonitorDeviceInfo> monitorDeviceInfos;
}