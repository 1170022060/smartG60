package com.pingok.devicemonitor.domain.gantry;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * tbl_gantry_vplr_baseinfo
 * 
 * @author bianj
 * @version 1.0.0 2022-05-19
 */
@Data
@Table(name = "TBL_GANTRY_VPLR_BASEINFO")
public class TblGantryVplrBaseInfo implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1L;

    /** picBaseId */
    private String picBaseId;

    /** gantryId */
    private String gantryId;

    /** ״̬ */
    private Date createTime;

    /** cameraNum */
    private Integer cameraNum;

    /** laneNum */
    private Integer laneNum;

    /** pileNum */
    private String pileNum;

    /** brand */
    private String brand;

    /** deviceType */
    private String deviceType;

    /** 192.168.1.1 */
    private String ipAddress;

    /** 8001 */
    private Integer port;

    /** pixel */
    private Integer pixel;

    /** 1024*768 */
    private String resolution;

    /** 1- */
    private Integer shootPosition;

    /** 0-ͣ */
    private Integer status;

    /** recvTime */
    private Date recvTime;

    /** 状态数据版本号(YYYYMMDD) */
    private String stateVersion;
}