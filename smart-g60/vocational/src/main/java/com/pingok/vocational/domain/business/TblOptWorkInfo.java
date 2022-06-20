package com.pingok.vocational.domain.business;

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
 * 操作员工班信息表 TBL_OPT_WORK_INFO
 *
 * @author ruoyi
 */
@Table(name = "TBL_OPT_WORK_INFO")
public class TblOptWorkInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 操作员ID */
    @Excel(name = "操作员ID")
    private Integer optId;

    /** 登陆时间 */
    @Excel(name = "登陆时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inTime;

    /** 登出时间 */
    @Excel(name = "登出时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date outTime;

    /** 工班日期 */
    @Excel(name = "工班日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date workDate;

    /** 收费站ID */
    @Excel(name = "收费站ID")
    private String stationId;

    /** 收费车道ID */
    @Excel(name = "收费车道ID")
    private String laneId;

    /** 车道类型：1:Mtc入 2：Mtc出 3：Etc入 4：Etc出*/
    @Excel(name = "车道类型",readConverterExp = "1=Mtc入,2=Mtc出,3=Etc入,4=Etc出")
    private Integer laneType;

    /** 工班类型：0：登出 1：夜班 2：白班*/
    @Excel(name = "工班类型",readConverterExp = "0=登出,1=夜班,2=白班")
    private Integer shift;

    /** 现金 */
    @Excel(name = "现金")
    private Integer cash;

    /** 通行卡 */
    @Excel(name = "通行卡")
    private Integer card;

    /** 坏卡数 */
    @Excel(name = "坏卡数")
    private Integer badCard;

    /** 无卡数 */
    @Excel(name = "无卡数")
    private Integer noCard;

    /** 发票 */
    @Excel(name = "发票")
    private Integer receipt;

    /** 移动支付 */
    @Excel(name = "移动支付")
    private Integer mPay;

    /** Obu支付 */
    @Excel(name = "Obu支付")
    private Integer obu;

    /** 交通卡支付 */
    @Excel(name = "交通卡支付")
    private Integer sptcc;

    /** 状态 0：未上传 1：已上传 4：上传失败 9：超时*/
    @Excel(name = "状态",readConverterExp = "0=未上传,1=已上传,4=上传失败,9=超时")
    private Integer transStatus;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Integer getOptId()
    {
        return optId;
    }

    public void setOptId(Integer optId)
    {
        this.optId = optId;
    }

    public Date getInTime()
    {
        return inTime;
    }

    public void setInTime(Date inTime) { this.inTime = inTime; }

    public Date getOutTime()
    {
        return outTime;
    }

    public void setOutTime(Date outTime) { this.outTime = outTime; }

    public Date getWorkDate()
    {
        return workDate;
    }

    public void setWorkDate(Date workDate) { this.workDate = workDate; }

    @Size(min = 0, max = 4, message = "收费站ID不能超过4个字符")
    public String getStationId()
    {
        return stationId;
    }

    public void setStationId(String stationId) { this.stationId = stationId; }

    @Size(min = 0, max = 2, message = "收费车道ID不能超过2个字符")
    public String getLaneId()
    {
        return laneId;
    }

    public void setLaneId(String laneId) { this.laneId = laneId; }

    public Integer getLaneType()
    {
        return laneType;
    }

    public void setLaneType(Integer laneType)
    {
        this.laneType = laneType;
    }

    public Integer getShift()
    {
        return shift;
    }

    public void setShift(Integer shift)
    {
        this.shift = shift;
    }

    public Integer getCash()
    {
        return cash;
    }

    public void setCash(Integer cash)
    {
        this.cash = cash;
    }

    public Integer getCard()
    {
        return card;
    }

    public void setCard(Integer card)
    {
        this.card = card;
    }

    public Integer getBadCard()
    {
        return badCard;
    }

    public void setBadCard(Integer badCard)
    {
        this.badCard = badCard;
    }

    public Integer getNoCard()
    {
        return noCard;
    }

    public void setNoCard(Integer noCard)
    {
        this.noCard = noCard;
    }

    public Integer getReceipt()
    {
        return receipt;
    }

    public void setReceipt(Integer receipt)
    {
        this.receipt = receipt;
    }

    public Integer getMPay()
    {
        return mPay;
    }

    public void setMPay(Integer mPay)
    {
        this.mPay = mPay;
    }

    public Integer getObu()
    {
        return obu;
    }

    public void setObu(Integer obu)
    {
        this.obu = obu;
    }

    public Integer getSptcc()
    {
        return sptcc;
    }

    public void setSptcc(Integer sptcc)
    {
        this.sptcc = sptcc;
    }

    public Integer getTransStatus()
    {
        return transStatus;
    }

    public void setTransStatus(Integer transStatus)
    {
        this.transStatus = transStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id",getId())
                .append("optId",getOptId())
                .append("inTime",getInTime())
                .append("outTime",getOutTime())
                .append("workDate",getWorkDate())
                .append("stationId",getStationId())
                .append("laneId",getLaneId())
                .append("laneType",getLaneType())
                .append("shift",getShift())
                .append("cash",getCash())
                .append("card",getCard())
                .append("badCard",getBadCard())
                .append("noCard",getNoCard())
                .append("receipt",getReceipt())
                .append("mPay",getMPay())
                .append("obu",getObu())
                .append("sptcc",getSptcc())
                .append("transStatus",getTransStatus())
                .toString();
    }
}
