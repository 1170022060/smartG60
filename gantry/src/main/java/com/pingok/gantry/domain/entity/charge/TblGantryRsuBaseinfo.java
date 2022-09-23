package com.pingok.gantry.domain.entity.charge;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * tbl_gantry_rsu_baseinfo
 * 
 * @author bianj
 * @version 1.0.0 2021-12-13
 */
@Getter
@Setter
@Table(name = "tbl_gantry_rsu_baseinfo")
public class TblGantryRsuBaseinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** ETC */
    private String gantryId;

    /** RSU */
    private String controlId;

    /** ipaddress */
    private String ipaddress;

    /** port */
    private Integer port;

    /** rsumanuid */
    private String rsumanuid;

    /** rsuid */
    private String rsuid;

    /** stateVersion */
    private String stateVersion;

    /** createTime */
    private Date createTime;

    /** rsuupdateversion */
    private String rsuupdateversion;

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

    /** psamnum */
    private Integer psamnum;

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