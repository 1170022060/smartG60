package com.pingok.devicemonitor.domain.gantry;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * tbl_gantry_vplr_monitor
 * 
 * @author bianj
 * @version 1.0.0 2022-05-19
 */
@Data
public class TblGantryVplrMonitor implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1L;

    /** picStateId */
    private String picStateId;

    /** gantryId */
    private String gantryId;

    /** cameraNum */
    private Integer cameraNum;

    /** stateTime */
    private Date stateTime;

    /** overstockImageJourCount */
    private Integer overstockImageJourCount;

    /** overstockImageCount */
    private Integer overstockImageCount;

    /** laneNum */
    private Integer laneNum;

    /** 0- */
    private Integer connectStatus;

    /** 0- */
    private Integer workStatus;

    /** 0- */
    private Integer lightWorkStatus;

    /** 99.99 */
    private Double recognitionRate;

    /** 1.2.3.1234 */
    private String hardwareVersion;

    /** 1.2.3.1234 */
    private String softwareVersion;

    /** runningTime */
    private Long runningTime;

    /** brand */
    private String brand;

    /** deviceType */
    private String deviceType;

    /** 0- */
    private String statusCode;

    /** statusMsg */
    private String statusMsg;

    /** recvTime */
    private Date recvTime;

    /** stateVersion */
    private String stateVersion;
}