package com.pingok.monitor.service.device;

import java.util.List;
import java.util.Map;

/**
 * 设备业务层
 *
 * @author qiumin
 */
public interface IDeviceStatusService {
    List<Map> list(Long deviceCategory);
}
