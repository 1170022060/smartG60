package com.pingok.vocational.domain.device;

import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * 门架设备详情表 TBL_GANTRY_INFO_DTL
 *
 * @author ruoyi
 */
@Table(name = "TBL_GANTRY_INFO_DTL")
public class TblGantryInfoDtl {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 基础信息ID */
    @Excel(name = "基础信息ID")
    private Long infoId;

    /** 类型：1-服务器 2-工控机 3-RSU 4-牌识 5-北斗授时 6-智能机柜 7-存储 8-全景相机 */
    @Excel(name = "类型",readConverterExp = "1=服务器,2=工控机,3=RSU,4=牌识,5=北斗授时,6=智能机柜,7=存储,8=全景相机")
    private Integer type;

    /** 主机IP */
    @Excel(name = "主机IP")
    private String mainIp;

    /** 备机IP */
    @Excel(name = "备机IP")
    private String standbyIp;

    /** 管理账户 */
    @Excel(name = "管理账户")
    private String loginName;

    /** 管理密码 */
    @Excel(name = "管理密码")
    private String loginPsw;

    /** 设备协议 */
    @Excel(name = "设备协议")
    private String protocol;

    public Long getId() { return id; }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getInfoId() { return infoId; }

    public void setInfoId(Long infoId)
    {
        this.infoId = infoId;
    }

    public Integer getType() { return type; }

    public void setType(Integer type)
    {
        this.type = type;
    }

    @Size(min = 0, max = 20, message = "主机IP长度不能超过20个字符")
    public String getMainIp()
    {
        return mainIp;
    }

    public void setMainIp(String mainIp)
    {
        this.mainIp = mainIp;
    }

    @Size(min = 0, max = 20, message = "备机IP长度不能超过20个字符")
    public String getStandbyIp()
    {
        return standbyIp;
    }

    public void setStandbyIp(String standbyIp)
    {
        this.standbyIp = standbyIp;
    }

    @Size(min = 0, max = 20, message = "管理账户长度不能超过20个字符")
    public String getLoginName()
    {
        return loginName;
    }

    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }

    @Size(min = 0, max = 50, message = "管理密码长度不能超过50个字符")
    public String getLoginPsw()
    {
        return loginPsw;
    }

    public void setLoginPsw(String loginPsw)
    {
        this.loginPsw = loginPsw;
    }

    @Size(min = 0, max = 32, message = "设备协议长度不能超过32个字符")
    public String getProtocol()
    {
        return protocol;
    }

    public void setProtocol(String protocol)
    {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id",getId())
                .append("infoId",getInfoId())
                .append("type",getType())
                .append("mainIp",getMainIp())
                .append("standbyIp",getStandbyIp())
                .append("loginName",getLoginName())
                .append("loginPsw",getLoginPsw())
                .append("protocol",getProtocol())
                .toString();
    }
}
