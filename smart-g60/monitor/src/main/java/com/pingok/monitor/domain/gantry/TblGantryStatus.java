package com.pingok.monitor.domain.gantry;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 门架设备状态表(TBL_GANTRY_STATUS)
 * 
 * @author qiumin
 * @version 1.0.0 2022-04-19
 */
@Table(name = "TBL_GANTRY_STATUS")
@Data
public class TblGantryStatus implements Serializable {

    /** id */
    @Id
    private Long id;

    /** 门架设备主键ID */
    private Long deviceId;

    /** 心跳时间 */
    private Date time;

    /** 状态:0-异常 1-正常 */
    private Integer status;

    /** 状态描述 */
    private String statusDesc;

    /** 未上传流水数 */
    private Integer transactionNumber;

    /** 未上传牌识数 */
    private Integer travelimageNumber;

    @Transient
    private TblGantryDayTrading dayTrading;

    @Transient
    private List<TblGantryStatusDtl> gantryStatusDtls;

}