package com.pingok.datacenter.domain.roster.green;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 绿通车辆名单记录表 TBL_GREEN_LIST_RECORD
 *
 * @author ruoyi
 */
@Data
@Table(name = "TBL_GREEN_LIST_RECORD")
public class TblGreenListRecord implements Serializable
{
    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Id
    private Long id;

    /** 绿通记录ID */
    private String greenId;

    private Integer passStateId;


    private String vehicleSign;

    private String vehicleId;

    private Date endTransportTime;
    private Date appointmentTime;
    private String endStationId;

    /** 入口省份*/
    @Excel(name = "入口省份")
    private String startDistrictId;

    /** 出口省份*/
    @Excel(name = "出口省份")
    private String endDistrictId;

    /** 入库时间 */
    @Excel(name = "入库时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dbTime;

    /** 关联id */
    private Long versionId;

    /** 电话号码*/
    @Excel(name = "电话号码")
    private String phone;

    /** 预约时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appointTime;
}