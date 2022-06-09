package com.pingok.external.service.amap;

import com.pingok.external.domain.amap.TblAutoNaviMapRecord;

public interface IAutoNaviMapService {
    /**
     * 事件推送
     *
     * @param autoNaviMapRecord
     */
    void eventPublish(TblAutoNaviMapRecord autoNaviMapRecord);

    /**
     * 高德地图回调
     *
     * @param sourceid
     * @param id
     * @param status
     */
    void callback(String sourceid, Long id, Integer status);

    /**
     * 事件解除
     *
     * @param id
     */
    void eventRelieve(Long id);
}
