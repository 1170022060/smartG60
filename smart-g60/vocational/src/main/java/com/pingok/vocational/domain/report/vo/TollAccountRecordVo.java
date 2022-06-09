package com.pingok.vocational.domain.report.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 通行费核算记录表(导出格式)
 *
 * @author ruoyi
 */

public class TollAccountRecordVo {

    private static final long serialVersionUID = 1L;
    /**
     * 工班日期
     */
    @Excel(name = "工班日期",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date workDate;

    /**
     * 车种
     */
    @Excel(name = "车种")
    private String vehStatus;

    /**
     * 交易笔数
     */
    @Excel(name = "交易笔数")
    private Long transNo;

    /**
     * 计算金额
     */
    @Excel(name = "计算金额")
    private Long calculateFee;

    /**
     * 应收金额
     */
    @Excel(name = "应收金额")
    private Long tollFee;

    /**
     * 实收金额
     */
    @Excel(name = "实收金额")
    private Long amount;

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getVehStatus() {
        return vehStatus;
    }

    public void setVehStatus(String vehStatus) {
        this.vehStatus = vehStatus;
    }

    public Long getTransNo() {
        return transNo;
    }

    public void setTransNo(Long transNo) {
        this.transNo = transNo;
    }

    public Long getCalculateFee() {
        return calculateFee;
    }

    public void setCalculateFee(Long calculateFee) {
        this.calculateFee = calculateFee;
    }

    public Long getTollFee() {
        return tollFee;
    }

    public void setTollFee(Long tollFee) {
        this.tollFee = tollFee;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("workDate", getWorkDate())
                .append("vehStatus", getVehStatus())
                .append("transNo", getTransNo())
                .append("calculateFee", getCalculateFee())
                .append("tollFee", getTollFee())
                .append("amount", getAmount())
                .toString();
    }
}
