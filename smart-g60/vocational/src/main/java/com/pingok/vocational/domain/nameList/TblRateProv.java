package com.pingok.vocational.domain.nameList;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 最小费率分省信息表(TBL_RATE_PROV)
 * 
 * @author bianj
 * @version 1.0.0 2022-10-21
 */
@Data
@Table(name = "TBL_RATE_PROV")
public class TblRateProv implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1256407778976882334L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 主键 */
    @Id
    private Long id;

    /** 最小费率表主键ID */
    private Long rateId;

    /** 序号 */
    private BigDecimal pIndex;

    /** 省份编号 */
    private String prov;

    /** 分省金额 */
    private BigDecimal pFee;

    /** 分省打折金额 */
    private BigDecimal pFee95;

    /** 分省里程数 */
    private BigDecimal pM;

    /** 计费模块版本号 */
    private String mVer;

    /** 参数版本号 */
    private String pVer;

    /** 分省门架信息 */
    private String groupInfo;

    /** 分省门架收费信息 */
    private String groupFee;

}