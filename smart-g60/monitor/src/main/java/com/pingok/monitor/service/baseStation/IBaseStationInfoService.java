package com.pingok.monitor.service.baseStation;

import com.pingok.monitor.domain.baseStation.TblBaseStationInfo;

import java.util.List;
import java.util.Map;

/**
 * 收费站信息 业务层
 *
 * @author qiumin
 */
public interface IBaseStationInfoService {

    /**
     * 查询用户所属站及监控站
     * @return
     */
    List<TblBaseStationInfo> findByUser();

    List<Map> getMonitorInfo();
}
