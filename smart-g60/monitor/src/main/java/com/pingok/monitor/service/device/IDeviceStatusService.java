package com.pingok.monitor.service.device;

import com.pingok.monitor.domain.device.vo.DeviceInfoVo;

import java.util.List;
import java.util.Map;

/**
 * 设备业务层
 *
 * @author qiumin
 */
public interface IDeviceStatusService {

    /**
     * 检查设备离线状态
     */
    void checkStatus();
    List<Map> list(Long deviceCategory,String deviceName,String deviceId,Integer status);
    List<Map> selectBaseStation();
    List<Map> selectBridgeInfo();
    List<Map> selectGantry();
    List<DeviceInfoVo> selectDeviceInfo(Integer deviceType);

}
