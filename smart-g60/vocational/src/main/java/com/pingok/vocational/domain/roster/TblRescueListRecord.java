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
 * 抢险救灾名单记录表 TBL_RESCUE_LIST_RECORD
 *
 * @author ruoyi
 */
@Table(name = "TBL_RESCUE_LIST_RECORD")
public class TblRescueListRecord {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 车牌*/
    @Excel(name = "车牌")
    private String vehPlate;

    /** 车型*/
    @Excel(name = "车型")
    private Integer vehClass;

    /** 车牌颜色*/
    @Excel(name = "车牌颜色")
    private Integer vehColor;

    /** 优惠类型*/
    @Excel(name = "优惠类型")
    private Integer discountType;

    /** 优惠说明*/
    @Excel(name = "优惠说明")
    private String discountExplain;

    /** 优惠方式*/
    @Excel(name = "优惠方式")
    private Integer discountMode;

    /** 优惠折扣*/
    @Excel(name = "优惠折扣")
    private Integer discountRebate;

    /** 处理方 */
    @Excel(name = "处理方")
    private String handleSide;

    /** 预约时间 */
    @Excel(name = "预约时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date reserveTime;

    /** ETC卡号 */
    @Excel(name = "ETC卡号")
    private String cardId;

    /** 优惠金额 */
    @Excel(name = "优惠金额")
    private Integer discountFee;

    /** 优惠定额 */
    @Excel(name = "优惠定额")
    private Integer discountQuota;

    /** 行程开始时间 */
    @Excel(name = "行程开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 行程结束时间 */
    @Excel(name = "行程结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 入口站 */
    @Excel(name = "入口站")
    private String enStation;

    /** 出口站 */
    @Excel(name = "出口站")
    private String exStation;

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

    public Integer getVehClass()
    {
        return vehClass;
    }

    public void setVehClass(Integer vehClass)
    {
        this.vehClass = vehClass;
    }

    public Integer getVehColor()
    {
        return vehColor;
    }

    public void setVehColor(Integer vehColor)
    {
        this.vehColor = vehColor;
    }

    public Integer getDiscountType()
    {
        return discountType;
    }

    public void setDiscountType(Integer discountType)
    {
        this.discountType = discountType;
    }

    @Size(min = 0, max = 50, message = "优惠说明不能超过50个字符")
    public String getDiscountExplain()
    {
        return discountExplain;
    }

    public void setDiscountExplain(String discountExplain)
    {
        this.discountExplain = discountExplain;
    }

    public Integer getDiscountMode()
    {
        return discountMode;
    }

    public void setDiscountMode(Integer discountMode)
    {
        this.discountMode = discountMode;
    }

    public Integer getDiscountRebate()
    {
        return discountRebate;
    }

    public void setDiscountRebate(Integer discountRebate)
    {
        this.discountRebate = discountRebate;
    }

    @Size(min = 0, max = 20, message = "处理方不能超过20个字符")
    public String getHandleSide()
    {
        return handleSide;
    }

    public void setHandleSide(String handleSide)
    {
        this.handleSide = handleSide;
    }

    public Date getReserveTime()
    {
        return reserveTime;
    }

    public void setReserveTime(Date reserveTime)
    {
        this.reserveTime = reserveTime;
    }

    @Size(min = 0, max = 20, message = "ETC卡号不能超过20个字符")
    public String getCardId()
    {
        return cardId;
    }

    public void setCardId(String cardId)
    {
        this.cardId = cardId;
    }

    public Integer getDiscountFee()
    {
        return discountFee;
    }

    public void setDiscountFee(Integer discountFee)
    {
        this.discountFee = discountFee;
    }

    public Integer getDiscountQuota()
    {
        return discountQuota;
    }

    public void setDiscountQuota(Integer discountQuota)
    {
        this.discountQuota = discountQuota;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }

    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    @Size(min = 0, max = 20, message = "入口站")
    public String getEnStation()
    {
        return enStation;
    }

    public void setEnStation(String enStation)
    {
        this.enStation = enStation;
    }

    @Size(min = 0, max = 20, message = "出口站")
    public String getExStation()
    {
        return exStation;
    }

    public void setExStation(String exStation)
    {
        this.exStation = exStation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("vehPlate", getVehPlate())
                .append("vehClass", getVehClass())
                .append("vehColor", getVehColor())
                .append("discountType", getDiscountType())
                .append("discountExplain", getDiscountExplain())
                .append("discountMode", getDiscountMode())
                .append("discountRebate", getDiscountRebate())
                .append("handleSide", getHandleSide())
                .append("reserveTime", getReserveTime())
                .append("cardId", getCardId())
                .append("discountFee", getDiscountFee())
                .append("discountQuota", getDiscountQuota())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("enStation", getEnStation())
                .append("exStation", getExStation())
                .toString();
    }
}
