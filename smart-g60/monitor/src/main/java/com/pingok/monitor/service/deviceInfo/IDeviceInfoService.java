package com.pingok.monitor.service.deviceInfo;

import com.pingok.monitor.domain.deviceInfo.ViewMonitorDeviceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 设备基础信息 业务层
 *
 * @author qiumin
 */
public interface IDeviceInfoService {
    List<ViewMonitorDeviceInfo> getInfo();

    /**
     * 根据场地编号查询设备信息及状态
     * @param fieldNum
     * @return
     */
    List<Map> findByFieldNum(String fieldNum);
}
