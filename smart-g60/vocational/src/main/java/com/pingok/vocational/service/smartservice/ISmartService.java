package com.pingok.vocational.service.smartservice;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.vocational.domain.device.TblDeviceRepair;
import com.pingok.vocational.domain.report.TblDeviceFault;

import java.util.List;
import java.util.Map;

public interface ISmartService {

    List<Map> serviceDevice(String fieldNum, Integer deviceType, Integer status);

    int insertServiceFault(TblDeviceFault tblDeviceFault);

    List<Map> serviceDeviceStatus(String fieldNum,Integer deviceType);
    List<Map> serviceDeviceFault(String fieldNum, Integer deviceType,String deviceId, String faultId, String faultDescription,Integer status);
    int insertServiceRepair(TblDeviceRepair tblDeviceRepair);

    JSONArray getDeviceStatusDesc();
}
