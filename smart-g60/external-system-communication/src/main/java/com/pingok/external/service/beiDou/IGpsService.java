package com.pingok.external.service.beiDou;

import com.pingok.external.domain.gps.TblMaintainCarGps;

import java.util.List;

public interface IGpsService {
    /**
     * 养护车流GPS实时数据
     * @return
     */
    List<TblMaintainCarGps> carGps();
    void getCarsGps();
}
