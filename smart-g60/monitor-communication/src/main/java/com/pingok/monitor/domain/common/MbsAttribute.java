package com.pingok.monitor.domain.common;

import lombok.Data;

/**
 * @author
 * @time 2022/6/26 13:24
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
    //寄存器个数
    private Integer count;
    //值的类型
    private Integer dataType;

    public MbsAttribute(String host, Integer port, Integer slaveId, Integer funcCode, Integer offset, Integer count, Integer dataType) {
        this.host = host;
        this.port = port;
        this.slaveId = slaveId;
        this.funcCode = funcCode;
        this.offset = offset;
        this.count = count;
        this.dataType = dataType;
    }
}
