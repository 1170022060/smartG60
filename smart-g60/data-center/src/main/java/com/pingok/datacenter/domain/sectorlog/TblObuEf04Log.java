package com.pingok.datacenter.domain.sectorlog;


import com.ruoyi.common.core.annotation.Excel;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

/**
 * OBU EF04日志表 TBL_OBU_EF04_LOG
 *
 * @author ruoyi
 */
@Table(name = "TBL_OBU_EF04_LOG")
public class TblObuEf04Log {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 对应扇区日志表ID */
    @Excel(name = "对应扇区日志表ID")
    private Long logId;

    /** 累计通行省份个数 */
    @Excel(name = "累计通行省份个数")
    private Integer tollProvinceCnt;

    /** 累计应收金额 */
    @Excel(name = "累计应收金额")
    private Integer tollOrgFee;

    /** 累计实收金额 */
    @Excel(name = "累计实收金额")
    private Integer tollFee;

    /** 累计交易成功次数 */
    @Excel(name = "累计交易成功次数")
    private Integer tollSuccTimes;

    /** 累计计费里程 */
    @Excel(name = "累计计费里程")
    private Integer tollMileage;

    /** 标签无卡累计次数 */
    @Excel(name = "标签无卡累计次数")
    private Integer noCardTimes;

    /** 本省入口编码 */
    @Excel(name = "本省入口编码")
    private String localHex;

    /** 本省累计应收金额 */
    @Excel(name = "本省累计应收金额")
    private Integer localOrgFee;

    /** 本省累计交易成功数量 */
    @Excel(name = "本省累计交易成功数量")
    private Integer localSuccTimes;

    /** 本省累计实收金额 */
    @Excel(name = "本省累计实收金额")
    private Integer localFee;

    /** 加密摘要 */
    @Excel(name = "加密摘要")
    private String digest;

    /** 子菜单 */
    @Transient
    private List<Map> prov;

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

    public Integer getTollProvinceCnt()
    {
        return tollProvinceCnt;
    }

    public void setTollProvinceCnt(Integer tollProvinceCnt)
    {
        this.tollProvinceCnt = tollProvinceCnt;
    }

    public Integer getTollOrgFee()
    {
        return tollOrgFee;
    }

    public void setTollOrgFee(Integer tollOrgFee)
    {
        this.tollOrgFee = tollOrgFee;
    }

    public Integer getTollFee()
    {
        return tollFee;
    }

    public void setTollFee(Integer tollFee)
    {
        this.tollFee = tollFee;
    }

    public Integer getTollSuccTimes()
    {
        return tollSuccTimes;
    }

    public void setTollSuccTimes(Integer tollSuccTimes)
    {
        this.tollSuccTimes = tollSuccTimes;
    }

    public Integer getTollMileage()
    {
        return tollMileage;
    }

    public void setTollMileage(Integer tollMileage)
    {
        this.tollMileage = tollMileage;
    }

    public Integer getNoCardTimes()
    {
        return noCardTimes;
    }

    public void setNoCardTimes(Integer noCardTimes)
    {
        this.noCardTimes = noCardTimes;
    }

    @Size(min = 0, max = 6, message = "本省入口编码长度不能超过6个字符")
    public String getLocalHex()
    {
        return localHex;
    }

    public void setLocalHex(String localHex)
    {
        this.localHex = localHex;
    }

    public Integer getLocalOrgFee()
    {
        return localOrgFee;
    }

    public void setLocalOrgFee(Integer localOrgFee)
    {
        this.localOrgFee = localOrgFee;
    }

    public Integer getLocalSuccTimes()
    {
        return localSuccTimes;
    }

    public void setLocalSuccTimes(Integer localSuccTimes)
    {
        this.localSuccTimes = localSuccTimes;
    }

    public Integer getLocalFee()
    {
        return localFee;
    }

    public void setLocalFee(Integer localFee)
    {
        this.localFee = localFee;
    }

    @Size(min = 0, max = 20, message = "加密摘要长度不能超过20个字符")
    public String getDigest()
    {
        return digest;
    }

    public void setDigest(String digest)
    {
        this.digest = digest;
    }

    public List<Map> getProv() {
        return prov;
    }

    public void setProv(List<Map> prov) {
        this.prov = prov;
    }
}
