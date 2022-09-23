package com.pingok.gantry.domain.entity.gantry;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * dfs_gantry_vplr_baseinfo
 * 
 * @author bianj
 * @version 1.0.0 2021-12-13
 */
@Getter
@Setter
@Table(name = "dfs_gantry_vplr_baseinfo")
public class DfsGantryVplrBaseinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** picBaseId */
    @Id
    private String picBaseId;

    private String gantryId;

    /**
     * ״̬
     */
    private Date createTime;

    private Integer cameraNum;

    private Integer laneNum;

    private String pileNum;

    private String brand;

    private String deviceType;

    /**
     * 192.168.1.1
     */
    private String IPAddress;

    /**
     * 8001
     */
    private Integer port;

    private Integer pixel;

    /**
     * 1024*768
     */
    private String resolution;

    /**
     * 1-
     */
    private String shootPosition;

    /**
     * 0-ͣ
     */
    private String status;

    private Date recvTime;

    private Integer uploadFlag;

    private Date uploadTime;

    private Integer lnduceFlag;

    private Date lnduceTime;

    /**
     * 状态数据版本号(YYYYMMDD)
     */
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