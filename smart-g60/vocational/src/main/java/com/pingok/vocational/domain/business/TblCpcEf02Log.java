package com.pingok.vocational.domain.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * CPC EF02日志表 TBL_CPC_EF02_LOG
 *
 * @author ruoyi
 */
@Table(name = "TBL_CPC_EF02_LOG")
public class TblCpcEf02Log implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 对应扇区日志表ID */
    @Excel(name = "对应扇区日志表ID")
    private Long logId;

    /** 通行省份个数 */
    @Excel(name = "通行省份个数")
    private Integer proCount;

    /** 本省门架个数 */
    @Excel(name = "本省门架个数")
    private Integer gantryCount;

    /** 本省累计金额 */
    @Excel(name = "本省累计金额")
    private Integer fee;

    /** 本省累计里程 */
    @Excel(name = "本省累计里程")
    private Integer mileage;

    /** 入口etc门架编码 */
    @Excel(name = "入口etc门架编码")
    private String enEtcGantryHex;

    /** 入口时间 */
    @Excel(name = "入口时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enTime;

    /** 最新etc门架编码 */
    @Excel(name = "最新etc门架编码")
    private String lastEtcGantryHex;

    /** 最新etc门架通行时间 */
    @Excel(name = "最新etc门架通行时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastPassTime;

    /** 最新etc门架计费金额 */
    @Excel(name = "最新etc门架计费金额")
    private Integer lastFee;

    /** 最新etc门架计费里程 */
    @Excel(name = "最新etc门架计费里程")
    private Integer lastMileage;

    /** 过站信息中的门架数 */
    @Excel(name = "过站信息中的门架数")
    private Integer etcGantryCount;

    /** 过站信息（Etc门架编号） */
    @Excel(name = "过站信息（Etc门架编号）")
    private String etcGantryRecords;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getLogId()
    {
        return logId;
    }

    public void setLogId(Long logId)
    {
        this.logId = logId;
    }

    public Integer getProCount()
    {
        return proCount;
    }

    public void setProCount(Integer proCount)
    {
        this.proCount = proCount;
    }

    public Integer getGantryCount()
    {
        return gantryCount;
    }

    public void setGantryCount(Integer gantryCount)
    {
        this.gantryCount = gantryCount;
    }

    public Integer getFee()
    {
        return fee;
    }

    public void setFee(Integer fee)
    {
        this.fee = fee;
    }

    public Integer getMileage()
    {
        return mileage;
    }

    public void setMileage(Integer mileage)
    {
        this.mileage = mileage;
    }

    public String getEnEtcGantryHex()
    {
        return enEtcGantryHex;
    }

    public void setEnEtcGantryHex(String enEtcGantryHex)
    {
        this.enEtcGantryHex = enEtcGantryHex;
    }

    public Date getEnTime()
    {
        return enTime;
    }

    public void setEnTime(Date enTime)
    {
        this.enTime = enTime;
    }

    public String getLastEtcGantryHex()
    {
        return lastEtcGantryHex;
    }

    public void setLastEtcGantryHex(String lastEtcGantryHex)
    {
        this.lastEtcGantryHex = lastEtcGantryHex;
    }

    public Date getLastPassTime()
    {
        return lastPassTime;
    }

    public void setLastPassTime(Date lastPassTime)
    {
        this.lastPassTime = lastPassTime;
    }

    public Integer getLastFee()
    {
        return lastFee;
    }

    public void setLastFee(Integer lastFee)
    {
        this.lastFee = lastFee;
    }

    public Integer getLastMileage()
    {
        return lastMileage;
    }

    public void setLastMileage(Integer lastMileage)
    {
        this.lastMileage = lastMileage;
    }

    public Integer getEtcGantryCount()
    {
        return etcGantryCount;
    }

    public void setEtcGantryCount(Integer etcGantryCount)
    {
        this.etcGantryCount = etcGantryCount;
    }

    public String getEtcGantryRecords()
    {
        return etcGantryRecords;
    }

    public void setEtcGantryRecords(String etcGantryRecords)
    {
        this.etcGantryRecords = etcGantryRecords;
    }

}
