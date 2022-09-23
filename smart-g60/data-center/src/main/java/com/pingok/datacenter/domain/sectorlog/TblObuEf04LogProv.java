package com.pingok.datacenter.domain.sectorlog;

import com.ruoyi.common.core.annotation.Excel;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * OBU EF04省份子表 TBL_OBU_EF04_LOG_PROV
 *
 * @author ruoyi
 */
@Table(name = "TBL_OBU_EF04_LOG_PROV")
public class TblObuEf04LogProv implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 对应OBU EF04日志表ID */
    @Excel(name = "对应OBU EF04日志表ID")
    private Long logId;

    /** 省份编码 */
    @Excel(name = "省份编码")
    private String provId;

    /** 省份累计实收金额 */
    @Excel(name = "省份累计实收金额")
    private Integer provFee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    @Size(min = 0, max = 2, message = "省份编码长度不能超过2个字符")
    public String getProvId() {
        return provId;
    }

    public void setProvId(String provId) {
        this.provId = provId;
    }

    public Integer getProvFee() {
        return provFee;
    }

    public void setProvFee(Integer provFee) {
        this.provFee = provFee;
    }
}
