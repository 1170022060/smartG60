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
 * 状态名单记录表 TBL_STATUS_LIST_RECORD
 *
 * @author ruoyi
 */
@Table(name = "TBL_STATUS_LIST_RECORD")
public class TblStatusListRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 版本号 */
    @Excel(name = "版本号")
    private String version;

    /** ETC卡号 */
    @Excel(name = "ETC卡号")
    private String cardId;

    /** 省份ID */
    @Excel(name = "省份ID")
    private String provId;

    /** 发行服务机构ID */
    @Excel(name = "发行服务机构ID")
    private String issuerId;

    /** 状态类型：1.卡挂失 2.无卡挂起 3.无卡注销 4.账户透支 5.合作机构黑名单 6.车型不符 7.储值卡余额不足 */
    @Excel(name = "状态类型",readConverterExp = "1=卡挂失,2=无卡挂起,3=无卡注销,4=账户透支,5=合作机构黑名单,6=车型不符,7=储值卡余额不足")
    private Integer statusType;

    /** 状态标识：1.入名单 2.出名单 */
    @Excel(name = "状态标识",readConverterExp = "1=入名单,2=出名单")
    private Integer statusSign;

    /** 生成时间 */
    @Excel(name = "生成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 入库时间 */
    @Excel(name = "入库时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date insertTime;

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

    @Size(min = 0, max = 12, message = "版本号不能超过12个字符")
    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
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

    @Size(min = 0, max = 2, message = "省份ID不能超过2个字符")
    public String getProvId()
    {
        return provId;
    }

    public void setProvId(String provId)
    {
        this.provId = provId;
    }

    @Size(min = 0, max = 6, message = "发行服务机构ID不能超过2个字符")
    public String getIssuerId()
    {
        return issuerId;
    }

    public void setIssuerId(String issuerId)
    {
        this.issuerId = issuerId;
    }

    public Integer getStatusType()
    {
        return statusType;
    }

    public void setStatusType(Integer statusType)
    {
        this.statusType = statusType;
    }

    public Integer getStatusSign()
    {
        return statusSign;
    }

    public void setStatusSign(Integer statusSign)
    {
        this.statusSign = statusSign;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getInsertTime()
    {
        return insertTime;
    }

    public void setInsertTime(Date insertTime)
    {
        this.insertTime = insertTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("version", getVersion())
                .append("cardId", getCardId())
                .append("provId", getProvId())
                .append("issuerId", getIssuerId())
                .append("statusType", getStatusType())
                .append("statusSign", getStatusSign())
                .append("createTime", getCreateTime())
                .append("insertTime", getInsertTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
