package com.pingok.gantry.domain.entity.gantry;

import lombok.Getter;
import lombok.Setter;
import com.pingok.gantry.domain.vo.RsuBaseKey;
import javax.persistence.EmbeddedId;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * dfs_gantry_rsu_monitor
 * 
 * @author bianj
 * @version 1.0.0 2021-12-13
 */
@Getter
@Setter
@Table(name = "dfs_gantry_rsu_monitor")
public class DfsGantryRsuMonitor implements Serializable {

    @EmbeddedId
    private RsuBaseKey id;


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

//    private String RUploadFlag;
//
//    private Date RUploadTime;
//
//    private String CUploadFlag;
//
//    private Date CUploadTime;

}