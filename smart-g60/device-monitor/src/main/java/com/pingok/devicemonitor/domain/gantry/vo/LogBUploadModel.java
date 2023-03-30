package com.pingok.devicemonitor.domain.gantry.vo;

import lombok.Data;

import javax.persistence.Transient;

/**
 * @author
 * @time 2022/5/23 14:52
 */
@Data
public class LogBUploadModel {
    @Transient
    private String msgId;
    @Transient
    private String msgTime;

    private String gantryId;
    private String logDate; //日志日期 YYYYMMDD
    private Integer logType; //日志类型
}
