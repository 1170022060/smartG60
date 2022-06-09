package com.pingok.devicemonitor.domain.gantry;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * tbl_gantry_rsu_baseinfo
 * 
 * @author bianj
 * @version 1.0.0 2022-05-19
 */
@Data
@Table(name = "TBL_GANTRY_RSU_BASEINFO_CUR")
public class TblGantryRsuBaseInfoCurrent implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1L;

    /** ETC */
    private String gantryId;

    /** RSU */
    private String controlId;

    /** ipAddress */
    private String ipAddress;

    /** port */
    private Integer port;

    /** rsuManuId */
    private String rsuManuId;

    /** rsuid */
    private String rsuid;

    /** stateVersion */
    private String stateVersion;

    /** createTime */
    private Date createTime;

    /** rsuUpdateVersion */
    private String rsuUpdateVersion;

    /** hardWareVersion */
    private String hardWareVersion;

    /** softWareVersion */
    private String softWareVersion;

    /** antennaHardWareVersion */
    private String antennaHardWareVersion;

    /** antennaSoftWareVersion */
    private String antennaSoftWareVersion;

    /** RSU */
    private Integer antennaNum;

    /** psamNum */
    private Integer psamNum;

    /** recvTime */
    private Date recvTime;

}