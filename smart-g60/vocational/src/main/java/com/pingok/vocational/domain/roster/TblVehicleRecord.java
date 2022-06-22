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
 * 车辆通行记录表 TBL_VEHICLE_RECORD
 *
 * @author ruoyi
 */
@Table(name = "TBL_VEHICLE_RECORD")
public class TblVehicleRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 通行编号 */
    @Excel(name = "通行编号")
    private String passId;

    /** GID */
    @Excel(name = "GID")
    private String gid;

    /** 计费编号 */
    @Excel(name = "计费编号")
    private String transactionId;

    /** 入口站编号 */
    @Excel(name = "入口站编号")
    private String enStationId;

    /** 入口站名称 */
    @Excel(name = "入口站名称")
    private String enStationName;

    /** 出口站编号 */
    @Excel(name = "出口站编号")
    private String exStationId;

    /** 出口站名称 */
    @Excel(name = "出口站名称")
    private String exStationName;

    /** 门架编号 */
    @Excel(name = "门架编号")
    private String gantryId;

    /** 车辆图片 */
    @Excel(name = "车辆图片")
    private String vehPhoto;

    /** 出口站名称 */
    @Excel(name = "车牌")
    private String vehPlate;

    /** 车牌颜色*/
    @Excel(name = "车牌颜色")
    private Integer vehColor;

    /** 收费车型*/
    @Excel(name = "收费车型")
    private Integer vehClass;

    /** 收费车种*/
    @Excel(name = "收费车种")
    private Integer vehStatus;

    /** 入口时间*/
    @Excel(name = "入口时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enTime;

    /** 出口时间*/
    @Excel(name = "出口时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exTime;

    /** 介质类型*/
    @Excel(name = "介质类型",readConverterExp = "1=Obu,2=Cpc,3=纸券,9=无通行介质")
    private Integer mediaType;

    /** 介质编码 */
    @Excel(name = "介质编码")
    private String mediaNo;

    /** 应收金额 */
    @Excel(name = "应收金额")
    private Integer totalPay;

    /** 优惠金额 */
    @Excel(name = "优惠金额")
    private Integer totalDiscount;

    /** 实收金额 */
    @Excel(name = "实收金额")
    private Integer totalFee;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Size(min = 0, max = 50, message = "通行编号不能超过50个字符")
    public String getPassId()
    {
        return passId;
    }

    public void setPassId(String passId)
    {
        this.passId = passId;
    }

    @Size(min = 0, max = 37, message = "通行编号不能超过37个字符")
    public String getGid()
    {
        return gid;
    }

    public void setGid(String gid)
    {
        this.gid = gid;
    }

    @Size(min = 0, max = 50, message = "计费编号不能超过50个字符")
    public String getTransactionId()
    {
        return transactionId;
    }

    public void setTransactionId(String transactionId)
    {
        this.transactionId = transactionId;
    }

    @Size(min = 0, max = 14, message = "入口站编号不能超过14个字符")
    public String getEnStationId()
    {
        return enStationId;
    }

    public void setEnStationId(String enStationId)
    {
        this.enStationId = enStationId;
    }

    @Size(min = 0, max = 50, message = "入口站编号不能超过50个字符")
    public String getEnStationName()
    {
        return enStationName;
    }

    public void setEnStationName(String enStationName)
    {
        this.enStationName = enStationName;
    }

    @Size(min = 0, max = 14, message = "出口站编号不能超过14个字符")
    public String getExStationId()
    {
        return exStationId;
    }

    public void setExStationId(String exStationId)
    {
        this.exStationId = exStationId;
    }

    @Size(min = 0, max = 50, message = "出口站编号不能超过50个字符")
    public String getExStationName()
    {
        return exStationName;
    }

    public void setExStationName(String exStationName)
    {
        this.exStationName = exStationName;
    }

    @Size(min = 0, max = 19, message = "门架编号不能超过50个字符")
    public String getGantryId()
    {
        return gantryId;
    }

    public void setGantryId(String gantryId)
    {
        this.gantryId = gantryId;
    }

    @Size(min = 0, max = 255, message = "车辆图片不能超过255个字符")
    public String getVehPhoto()
    {
        return vehPhoto;
    }

    public void setVehPhoto(String vehPhoto)
    {
        this.vehPhoto = vehPhoto;
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

    public Integer getVehStatus()
    {
        return vehStatus;
    }

    public void setVehStatus(Integer vehStatus)
    {
        this.vehStatus = vehStatus;
    }

    public Date getEnTime()
    {
        return enTime;
    }

    public void setEnTime(Date enTime)
    {
        this.enTime = enTime;
    }

    public Date getExTime()
    {
        return exTime;
    }

    public void setExTime(Date exTime)
    {
        this.exTime = exTime;
    }

    public Integer getMediaType()
    {
        return mediaType;
    }

    public void setMediaType(Integer mediaType)
    {
        this.mediaType = mediaType;
    }

    @Size(min = 0, max = 20, message = "车牌长度不能超过20个字符")
    public String getMediaNo()
    {
        return mediaNo;
    }

    public void setMediaNo(String mediaNo)
    {
        this.mediaNo = mediaNo;
    }

    public Integer getTotalPay()
    {
        return totalPay;
    }

    public void setTotalPay(Integer totalPay)
    {
        this.totalPay = totalPay;
    }

    public Integer getTotalDiscount()
    {
        return totalDiscount;
    }

    public void setTotalDiscount(Integer totalDiscount)
    {
        this.totalDiscount = totalDiscount;
    }

    public Integer getTotalFee()
    {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee)
    {
        this.totalFee = totalFee;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id",getId())
                .append("passId",getPassId())
                .append("gid",getGid())
                .append("transactionId",getTransactionId())
                .append("enStationId",getEnStationId())
                .append("enStationName",getEnStationName())
                .append("exStationId",getExStationId())
                .append("exStationName",getExStationName())
                .append("gantryId",getGantryId())
                .append("vehPhoto",getVehPhoto())
                .append("vehPlate",getVehPlate())
                .append("vehColor",getVehColor())
                .append("vehClass",getVehClass())
                .append("vehStatus",getVehStatus())
                .append("enTime",getEnTime())
                .append("exTime",getExTime())
                .append("mediaType",getMediaType())
                .append("mediaNo",getMediaNo())
                .append("totalPay",getTotalPay())
                .append("totalDiscount",getTotalDiscount())
                .append("totalFee",getTotalFee())
                .toString();
    }
}
