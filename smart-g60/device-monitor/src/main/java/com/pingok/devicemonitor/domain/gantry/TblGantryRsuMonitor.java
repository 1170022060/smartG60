package com.pingok.devicemonitor.domain.gantry;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * tbl_gantry_rsu_monitor
 * 
 * @author bianj
 * @version 1.0.0 2022-05-19
 */
@Data
public class TblGantryRsuMonitor implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1L;

    /** gantryId */
    private String gantryId;

    /** controlId */
    private String controlId;

    /** status */
    private Integer status;

    /** psamInfoList */
    private String psamInfoList;

    /** rsuManuId */
    private String rsuManuId;

    /** rsuid */
    private String rsuid;

    /** rsuUpdateVersion */
    private String rsuUpdateVersion;

    /** YYYYMMDDHHMI */
    private String stateVersion;

    /** createTime */
    private Date createTime;

    /** storeStatus */
    private Integer storeStatus;

    /** controlTemperature */
    private String controlTemperature;

    /** RSU */
    private Integer controlNetWork;

    /** antennalInfoList */
    private String antennalInfoList;

    /** recvTime */
    private Date recvTime;

}