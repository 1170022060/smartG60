package com.pingok.devicemonitor.service.gantry;

import com.pingok.devicemonitor.domain.gantry.TblGantryStatus;

public interface IGantryService {
    /**
     * 门架心跳
     * @param gantryStatus
     */
    void updateStatus(TblGantryStatus gantryStatus);


}
