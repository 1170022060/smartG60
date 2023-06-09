package com.pingok.external.domain.primary;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 申请大件运输车辆信息表(TBL_LARGE_TRANSPORT_VEHINFO)
 * 
 * @author bianj
 * @version 1.0.0 2023-02-13
 */
@Data
@Table(name = "TBL_LARGE_TRANSPORT_VEHINFO")
public class TblLargeTransportVehinfo implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -8627739383102178977L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private Long id;

    private String listId;

    /** mainId */
    private String mainId;

    /** recordNo */
    private String recordNo;

    /** certNo */
    private String certNo;

    /** transportUnit */
    private String transportUnit;

    /** 车牌号 */
    private String vehicleLicense;

    /** 车型 */
    private String vehicleType;

    /** 车重 */
    private String vehicleWeight;

    /** 货物名称 */
    private String goodsName;

    /** 货物信息-货重 */
    private String goodsWeight;

    /** 车身长度 */
    private String bodyLength;

    /** 车身宽度 */
    private String bodyWidth;

    /** 车身高度 */
    private String bodyHeight;

    /** axleLoadDist */
    private String axleLoadDist;

    /** dateStart */
    private String dateStart;

    /** dateEnd */
    private String dateEnd;

    /** route */
    private String route;

    /** certificatUnit */
    private String certificatUnit;

    /** applicationDate */
    private String applicationDate;

    /** operator */
    private String operator;

    /** fzDate */
    private String fzDate;

    /** createTime */
    private Date createTime;

    /** updateTime */
    private Date updateTime;

    /** isValid */
    private BigDecimal isValid;

    /** loadWeight */
    private String loadWeight;

    /** unionKey */
    private String unionKey;

}