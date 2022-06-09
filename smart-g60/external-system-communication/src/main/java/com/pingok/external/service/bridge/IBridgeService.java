package com.pingok.external.service.bridge;

import com.pingok.external.domain.bridge.TblBridgeInfo;

public interface IBridgeService {

    /**
     * 根据id查询桥梁信息
     */
    TblBridgeInfo selectBridgeInfoById(Long id);

    /**
     * 更新预警信息
     */
    void updateWarning();
    /**
     * 获取预警信息
     */
    void getWarning();
    /**
     * 更新传感器信息
     */
    void updateAcquisition();
    /**
     * 更新采集仪信息
     */
    void updateCollection();
    /**
     * 更新桥梁信息
     */
    void updateBridgeInfo();

    /**
     * 更新桥梁项目信息
     */
    void updateProjectInfo();
}
