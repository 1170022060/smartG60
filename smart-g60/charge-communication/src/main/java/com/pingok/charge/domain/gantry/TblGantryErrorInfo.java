package com.pingok.charge.domain.gantry;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * tbl_gantry_errorinfo
 * 
 * @author bianj
 * @version 1.0.0 2022-05-19
 */
@Data
@Table(name = "tbl_gantry_errorinfo")
public class TblGantryErrorInfo implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1L;

    /** eventId */
    private String eventId;

    /** gantryId */
    private String gantryId;

    /** eventTime */
    private Date eventTime;

    /** eventSource */
    private Integer eventSource;

    /** XΪ */
    private Integer eventCode;

    /** eventDesc */
    private String eventDesc;

    /** recvTime */
    private Date recvTime;

}