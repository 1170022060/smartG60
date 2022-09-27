package com.pingok.monitor.domain.infoboard;

import lombok.Data;

@Data
public class DevInfo {

    private Long id;
    private String devId;
    private String ip;
    private Integer port;
    private Integer slave;
    private String protocol;
    private Integer width;
    private Integer height;
    private Integer recordId;

}
