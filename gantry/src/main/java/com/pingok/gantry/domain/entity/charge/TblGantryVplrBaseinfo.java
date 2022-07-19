package com.pingok.gantry.domain.entity.charge;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * tbl_gantry_vplr_baseinfo
 * 
 * @author bianj
 * @version 1.0.0 2021-12-13
 */
@Getter
@Setter
@Table(name = "tbl_gantry_vplr_baseinfo")
public class TblGantryVplrBaseinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private String ipaddress;

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

    /** uploadFlag */
    private Integer uploadFlag;

    /** uploadTime */
    private Date uploadTime;

    /** lnduceFlag */
    private Integer lnduceFlag;

    /** lnduceTime */
    private Date lnduceTime;

    /** 状态数据版本号(YYYYMMDD) */
    private String stateVersion;

    /** muploadflag */
    private Integer muploadflag;

    /** muploadtime */
    private Date muploadtime;

    /** ruploadflag */
    private String ruploadflag;

    /** ruploadtime */
    private Date ruploadtime;

    /** cuploadflag */
    private String cuploadflag;

    /** cuploadtime */
    private Date cuploadtime;

}