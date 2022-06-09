package com.pingok.monitor.domain.infoboard;

import lombok.Data;

import java.util.List;

/** 情报板发布内容
 * @author
 * @time 2022/5/2 8:51
 */
@Data
public class VmsPublishInfo {

    private String deviceId;
    private String deviceIp;
    private Integer slaveId;
    private String protocol;
    //限速值
    private String fmsValue;

    private List<VmsPublishScreenInfo> info;
}
