package com.pingok.devicemonitor.domain.gantry;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * @time 2022/5/23 12:52
 */
@Data
@Table(name = "TBL_GANTRY_ERRORINFO")
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
