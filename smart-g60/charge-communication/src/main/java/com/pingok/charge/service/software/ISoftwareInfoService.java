package com.pingok.charge.service.software;


import com.pingok.charge.domain.software.vo.SoftwareHeartbeat;

/**
 * 软件信息 业务层
 *
 * @author qiumin
 */
public interface ISoftwareInfoService {
    /**
     * 软件心跳服务
     * @param SoftwareHeartbeat
     * @return
     */
    void heartbeat(SoftwareHeartbeat SoftwareHeartbeat);
}
