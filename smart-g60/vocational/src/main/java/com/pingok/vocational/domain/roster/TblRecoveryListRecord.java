package com.pingok.vocational.domain.roster;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
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

    /** 车牌*/
    @Excel(name = "车牌")
    private String vehPlate;

    /** 车牌颜色*/
    @Excel(name = "车牌颜色")
    private Integer vehColor;

    /** 追缴原因 */
    @Excel(name = "追缴原因")
    private String reason;

    /** 欠费金额 */
    @Excel(name = "欠费金额")
    private Integer fee;

    /** 欠费次数 */
    @Excel(name = "欠费次数")
    private Integer count;

    /** 欠费日期 */
    @Excel(name = "欠费日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date arrearsDate;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Size(min = 0, max = 12, message = "版本号不能超过12个字符")
    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    @Size(min = 0, max = 20, message = "车牌长度不能超过20个字符")
    public String getVehPlate()
    {
        return vehPlate;
    }

    public void setVehPlate(String vehPlate)
    {
        this.vehPlate = vehPlate;
    }

    public Integer getVehColor()
    {
        return vehColor;
    }

    public void setVehColor(Integer vehColor)
    {
        this.vehColor = vehColor;
    }

    @Size(min = 0, max = 200, message = "追缴原因不能超过200个字符")
    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public Integer getFee()
    {
        return fee;
    }

    public void setFee(Integer fee)
    {
        this.fee = fee;
    }

    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }

    public Date getArrearsDate()
    {
        return arrearsDate;
    }

    public void setArrearsDate(Date arrearsDate)
    {
        this.arrearsDate = arrearsDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("version", getVersion())
                .append("type", getType())
                .append("status", getStatus())
                .append("vehPlate", getVehPlate())
                .append("vehColor", getVehColor())
                .append("reason", getReason())
                .append("fee", getFee())
                .append("count", getCount())
                .append("arrearsDate", getArrearsDate())
                .toString();
    }
}
