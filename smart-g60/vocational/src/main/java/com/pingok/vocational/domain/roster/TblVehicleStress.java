package com.pingok.vocational.domain.roster;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 重点车辆清单表 TBL_VEHICLE_STRESS
 *
 * @author ruoyi
 */
@Table(name = "TBL_VEHICLE_STRESS")
public class TblVehicleStress {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 车牌*/
    @Excel(name = "车牌")
    private String vehPlate;

    /** 车牌颜色*/
    @Excel(name = "车牌颜色")
    private Integer vehColor;

    /** 车型*/
    @Excel(name = "车型")
    private Integer vehClass;

    /**车辆种类：1.客运 2.危险品 3.超限*/
    @Excel(name = "车辆种类",readConverterExp = "1=客运,2=危险品,3=超限")
    private Integer vehType;

    /** 创建时间 */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
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

    public Integer getVehClass()
    {
        return vehClass;
    }

    public void setVehClass(Integer vehClass)
    {
        this.vehClass = vehClass;
    }

    public Integer getVehType()
    {
        return vehType;
    }

    public void setVehType(Integer vehType)
    {
        this.vehType = vehType;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("vehPlate", getVehPlate())
                .append("vehColor", getVehColor())
                .append("vehClass", getVehClass())
                .append("vehType", getVehType())
                .append("createTime",getCreateTime())
                .append("updateTime",getUpdateTime())
                .toString();
    }
}
