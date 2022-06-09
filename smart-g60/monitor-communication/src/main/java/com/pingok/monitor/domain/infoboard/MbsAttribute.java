package com.pingok.monitor.domain.infoboard;

import lombok.Data;

/**
 * @author
 * @time 2022/5/2 8:50
 */
@Data
public class MbsAttribute {

    private String host;
    private Integer port;
    private Integer slaveId;
    //功能码
    private Integer funcCode;
    //偏移索引
    private Integer offset;
    //值的类型
    private String attributeType;

    public MbsAttribute(String host, Integer slaveId, Integer funcCode, Integer offset, String attributeType) {
        this.host = host;
        this.port = 502;
        this.slaveId = slaveId;
        this.funcCode = funcCode;
        this.offset = offset;
        this.attributeType = attributeType;
    }
}
