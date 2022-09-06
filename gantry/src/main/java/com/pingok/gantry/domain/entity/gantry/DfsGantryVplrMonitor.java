package com.pingok.gantry.domain.entity.gantry;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * dfs_gantry_vplr_monitor
 * 
 * @author bianj
 * @version 1.0.0 2021-12-13
 */
@Getter
@Setter
@Table(name = "dfs_gantry_vplr_monitor")
public class DfsGantryVplrMonitor implements Serializable {

    private static final long serialVersionUID = -5482974307615457594L;

    /** picStateId */
    @Id
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

//    private String RUploadFlag;
//
//    private Date RUploadTime;
//
//    private String CUploadFlag;
//
//    private Date CUploadTime;

}