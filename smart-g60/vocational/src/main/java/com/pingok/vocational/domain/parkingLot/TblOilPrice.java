package com.pingok.vocational.domain.parkingLot;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 今日油价表(TBL_OIL_PRICE)
 * 
 * @author xia
 * @version 1.0.0 2022-09-21
 */
@Data
@Table(name = "TBL_OIL_PRICE")
public class TblOilPrice implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 5025569497991716644L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 主键 */
    @Id
    private Long id;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date transDate;

    /** 92汽油价格 */
    private Float price92;

    /** 95汽油价格 */
    private Float price95;

    /** 98汽油价格 */
    private Float price98;

    /** 0汽油价格 */
    private Float price0;

    /** 创建时间 */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 创建用户ID */
    @Excel(name = "创建用户工号")
    private Long createUserId;

    /** 更新用户ID */
    @Excel(name = "更新用户ID")
    private Long updateUserId;
}