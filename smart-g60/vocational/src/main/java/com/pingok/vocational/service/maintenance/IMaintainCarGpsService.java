package com.pingok.vocational.service.maintenance;

import com.pingok.vocational.domain.maintenance.TblMaintainCarGps;

import java.util.List;
import java.util.Map;

/**
 * 养护车辆GPS信息表 业务层
 *
 * @author ruoyi
 */
public interface IMaintainCarGpsService {

    /**
     * 查询所有养护车辆GPS信息
     *
     * @return 所有养护车辆GPS信息
     */
    List<TblMaintainCarGps> selectAll();

    List<Map> selectInfo(String vehPlate);
}
