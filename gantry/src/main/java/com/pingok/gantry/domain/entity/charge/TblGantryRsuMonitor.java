package com.pingok.gantry.domain.entity.charge;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * tbl_gantry_rsu_monitor
 * 
 * @author bianj
 * @version 1.0.0 2021-12-13
 */
@Getter
@Setter
@Table(name = "tbl_gantry_rsu_monitor")
public class TblGantryRsuMonitor implements Serializable {

    private static final long serialVersionUID = -5482974307615457594L;

    /** gantryId */
    private String gantryId;

    /** controlId */
    private String controlId;

    /** YYYYMMDDHHMI */
    private String stateVersion;
    private Integer status;

    private String PSAMInfoList;

    private String RSUManuID;

    private String RSUID;

    private String RSUUpdateVersion;

    private Date createTime;

    private Integer storeStatus;

    private String controlTemperature;

    /**
     * RSU
     */
    private Integer controlNetWork;

    private String antennalInfoList;

    private Date recvTime;

    private Integer uploadFlag;

    private Date uploadTime;

    private Integer lnduceFlag;

    private Date lnduceTime;

    private Integer MUploadFlag;

    private Date MUploadTime;

    /** id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 心跳时间 */
    private Date heartbeatTime;

    /** 心跳状态：0异常 1正常 */
    private Integer heartbeatStatus;

}