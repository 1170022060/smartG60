package com.pingok.gantry.domain.entity.charge;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * tbl_gantry_vplr_monitor
 * 
 * @author bianj
 * @version 1.0.0 2021-12-13
 */
@Getter
@Setter
@Table(name = "tbl_gantry_vplr_monitor")
public class TblGantryVplrMonitor implements Serializable {

    private static final long serialVersionUID = -5482974307615457594L;

    /** id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String picStateId;

    private String gantryId;

    private Integer cameraNum;

    private Date stateTime;

    private Integer overstockImageJourCount;

    private Integer overstockImageCount;

    private Integer laneNum;

    /**
     * 0-
     */
    private String connectStatus;

    /**
     * 0-
     */
    private String workStatus;

    /**
     * 0-
     */
    private String lightWorkStatus;

    /**
     * 99.99
     */
    private Double recognitionRate;

    /**
     * 1.2.3.1234
     */
    private String hardwareVersion;

    /**
     * 1.2.3.1234
     */
    private String softwareVersion;

    private Long runningTime;

    private String brand;

    private String deviceType;

    /**
     * 0-
     */
    private String statusCode;

    private String statusMsg;

    private Date recvTime;

    private Integer uploadFlag;

    private Date uploadTime;

    private Integer lnduceFlag;

    private Date lnduceTime;

    private String stateVersion;

    private Integer MUploadFlag;

    private Date MUploadTime;

    private String RUploadFlag;

    private Date RUploadTime;

    private String CUploadFlag;

    private Date CUploadTime;

    /** 心跳时间 */
    private Date heartbeatTime;

    /** 心跳状态：0异常 1正常 */
    private Integer heartbeatStatus;

}