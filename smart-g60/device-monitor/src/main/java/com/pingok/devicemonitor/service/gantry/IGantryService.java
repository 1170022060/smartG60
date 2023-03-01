package com.pingok.devicemonitor.service.gantry;

import com.pingok.devicemonitor.domain.gantry.TblGantryStatus;
import com.ruoyi.system.api.domain.gantry.TblGantryEventRelease;

public interface IGantryService {


    /**
     * 车路协同推送
     */
    void eventProcessing(TblGantryEventRelease tblGantryEventRelease);

    /**
     * 门架心跳
     *
     * @param gantryStatus
     */
    void updateStatus(TblGantryStatus gantryStatus);


}
