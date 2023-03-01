package com.pingok.api.service.beidou;

import com.ruoyi.system.api.domain.gps.TblMaintainCarGps;

import java.util.List;

public interface IBeiDouService {
    /**
     * 养护车流GPS实时数据
     * @return
     */
    List<TblMaintainCarGps> list();
}
