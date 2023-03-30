package com.pingok.datacenter.domain.roster.recovery;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 追缴名单记录表 TBL_RECOVERY_LIST_RECORD
 *
 * @author ruoyi
 */
@Data
@Table(name = "TBL_RECOVERY_LIST_RECORD")
public class TblRecoveryListRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 版本号 */
    @Excel(name = "版本号")
    private String version;

    /** 黑名单类型：1.追缴名单 */
    @Excel(name = "黑名单类型",readConverterExp = "1=追缴名单")
    private Integer type;

    /** 状态：1.进入黑名单 2.解除黑名单 */
    @Excel(name = "状态",readConverterExp = "1=进入黑名单,2=解除黑名单")
    private Integer status;

    /** 车辆车牌号码+颜色 */
    private String vehicleId;

    /** 追缴原因 */
    @Excel(name = "追缴原因")
    private String reason;

    /** 欠费金额 */
    private Integer oweFee;

    /** 欠费行为次数 */
    private Integer evasionCount;

    /** 中心创建时间 */
    @Excel(name = "中心创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creationTime;

    /**
     * 创建或更新时间
     */
    @ApiModelProperty(value = "创建或更新时间")
    private Date updateTime;

    /** 关联id */
    private Long versionId;
}
