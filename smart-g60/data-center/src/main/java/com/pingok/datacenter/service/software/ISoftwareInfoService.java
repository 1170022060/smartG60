package com.pingok.datacenter.service.software;

import com.pingok.datacenter.domain.software.TblSoftwareHeartbeat;

/**
 * 软件信息 业务层
 *
 * @author qiumin
 */
public interface ISoftwareInfoService {
    /**
     * 软件心跳服务
     * @param tblSoftwareHeartbeat
     * @return
     */
    void heartbeat(TblSoftwareHeartbeat tblSoftwareHeartbeat);
}
