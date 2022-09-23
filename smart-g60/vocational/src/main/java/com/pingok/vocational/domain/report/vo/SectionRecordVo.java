package com.pingok.vocational.domain.report.vo;

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
 * 出入口段面记录(导出格式)
 *
 * @author ruoyi
 */

public class SectionRecordVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 工班日期
     */
    @Excel(name = "工班日期",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date workDate;

    /**
     * 收费站名
     */
    @Excel(name = "收费站名")
    private String stationId;

    /**
     * 方向：1.入口 2.出口
     */
    @Excel(name = "方向")
    private String direction;

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

    @Size(min = 0, max = 8, message = "收费站ID长度不能超过8个字符")
    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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
                .append("stationId", getStationId())
                .append("direction", getDirection())
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
