package com.pingok.monitor.service.vdt;

import com.alibaba.fastjson.JSONArray;
import com.pingok.monitor.domain.device.TblDeviceInfo;

import java.util.List;

public interface IVdtService {
    void collect(List<TblDeviceInfo> devList);
}
