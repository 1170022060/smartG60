package com.pingok.vocational.domain.report.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 在途总流量记录表(导出格式)
 *
 * @author ruoyi
 */

public class IntransitRecordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工班日期
     */
    @Excel(name = "工班日期",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date workDate;

    /**
     * Etc本地流量
     */
    @Excel(name = "Etc本地流量")
    private Long etcLocal;

    /**
     * Etc异地流量
     */
    @Excel(name = "Etc异地流量")
    private Long etcElse;

    /**
     * ETC总计
     */
    @Excel(name = "ETC总计")
    private Long etcTotal;

    /**
     * Mtc单省流量
     */
    @Excel(name = "Mtc单省流量")
    private Long mtcSingle;

    /**
     * Mtc跨省流量
     */
    @Excel(name = "Mtc跨省流量")
    private Long mtcTrans;

    /**
     * MTC总计
     */
    @Excel(name = "MTC总计")
    private Long mtcTotal;

    /**
     * 总计
     */
    @Excel(name = "总计")
    private Long totalSum;

    /**
     * 牌识流量
     */
    @Excel(name = "牌识流量")
    private Long license;

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Long getEtcLocal() {
        return etcLocal;
    }

    public void setEtcLocal(Long etcLocal) {
        this.etcLocal = etcLocal;
    }

    public Long getEtcElse() {
        return etcElse;
    }

    public void setEtcElse(Long etcElse) {
        this.etcElse = etcElse;
    }

    public Long getEtcTotal() {
        return etcTotal;
    }

    public void setEtcTotal(Long etcTotal) {
        this.etcTotal = etcTotal;
    }

    public Long getMtcSingle() {
        return mtcSingle;
    }

    public void setMtcSingle(Long mtcSingle) {
        this.mtcSingle = mtcSingle;
    }

    public Long getMtcTrans() {
        return mtcTrans;
    }

    public void setMtcTrans(Long mtcTrans) {
        this.mtcTrans = mtcTrans;
    }

    public Long getMtcTotal() {
        return mtcTotal;
    }

    public void setMtcTotal(Long mtcTotal) {
        this.mtcTotal = mtcTotal;
    }

    public Long getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Long totalSum) {
        this.totalSum = totalSum;
    }

    public Long getLicense() {
        return license;
    }

    public void setLicense(Long license) {
        this.license = license;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("workDate", getWorkDate())
                .append("etcLocal", getEtcLocal())
                .append("etcElse", getEtcElse())
                .append("etcTotal", getEtcTotal())
                .append("mtcSingle", getMtcSingle())
                .append("mtcTrans", getMtcTrans())
                .append("mtcTotal", getMtcTotal())
                .append("totalSum", getTotalSum())
                .append("license", getLicense())
                .toString();
    }
}
