package com.pingok.datacenter.domain.baseinfo;

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
 * 收费站基础信息表 TBL_BASE_STATION_INFO
 *
 * @author ruoyi
 */
@Table(name = "TBL_BASE_STATION_INFO")
public class TblBaseStationInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 收费站编号 */
    @Excel(name = "收费站编号")
    private String recordNum;

    /** 版本号 */
    @Excel(name = "版本号")
    private String version;

    /** 网络编号*/
    @Excel(name = "网络编号")
    private String netWork;

    /** 收费站id*/
    @Excel(name = "收费站id")
    private String stationId;

    /** 收费站名称*/
    @Excel(name = "收费站名称")
    private String stationName;

    /** 收费站Node国标*/
    @Excel(name = "收费站Node国标")
    private String stationGb;

    /** 收费站Node十六进制*/
    @Excel(name = "收费站Node十六进制")
    private String stationHex;

    /** 桩号*/
    @Excel(name = "桩号")
    private String pileNo;

    /** 出入口类型*/
    @Excel(name = "出入口类型",readConverterExp = "1=封闭式出+封闭式入,2=封闭式入口,3=封闭式出口")
    private Integer gateType;

    /** 类型*/
    @Excel(name = "类型",readConverterExp = "1=主线收费站,2=匝道收费站")
    private Integer squareType;

    /** 坐标*/
    @Excel(name = "坐标")
    private String gps;

    /** 是否有治超*/
    @Excel(name = "是否有治超",readConverterExp = "1=是,2=否")
    private Integer overloadControl;

    /** 类型*/
    @Excel(name = "治超位置",readConverterExp = "1=广场,2=车道")
    private Integer overloadControlSite;

    /** 所属路段*/
    @Excel(name = "所属路段")
    private String roadBelong;

    /** 集中站主键ID */
    @Excel(name = "集中站主键ID")
    private Long adminStationId;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 创建时间 */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 创建用户ID */
    @Excel(name = "创建用户工号")
    private Long createUserId;

    /** 更新用户ID */
    @Excel(name = "更新用户ID")
    private Long updateUserId;


    /** 与DAAS平台通信服务专用IP */
    private String ip;

    private String port;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Size(min = 0, max = 50, message = "收费站编号不能超过50个字符")
    public String getRecordNum()
    {
        return recordNum;
    }

    public void setRecordNum(String recordNum)
    {
        this.recordNum = recordNum;
    }

    @Size(min = 0, max = 20, message = "版本号不能超过20个字符")
    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    @Size(min = 0, max = 4, message = "网络编号不能超过4个字符")
    public String getNetWork()
    {
        return netWork;
    }

    public void setNetWork(String netWork)
    {
        this.netWork = netWork;
    }

    @Size(min = 0, max = 4, message = "收费站id不能超过4个字符")
    public String getStationId()
    {
        return stationId;
    }

    public void setStationId(String stationId)
    {
        this.stationId = stationId;
    }

    @Size(min = 0, max = 48, message = "收费站名称不能超过48个字符")
    public String getStationName()
    {
        return stationName;
    }

    public void setStationName(String stationName)
    {
        this.stationName = stationName;
    }

    @Size(min = 0, max = 14, message = "收费站node国标不能超过14个字符")
    public String getStationGb()
    {
        return stationGb;
    }

    public void setStationGb(String stationGb)
    {
        this.stationGb = stationGb;
    }

    @Size(min = 0, max = 8, message = "收费站node十六进制不能超过8个字符")
    public String getStationHex()
    {
        return stationHex;
    }

    public void setStationHex(String stationHex)
    {
        this.stationHex = stationHex;
    }

    @Size(min = 0, max = 20, message = "桩号不能超过20个字符")
    public String getPileNo()
    {
        return pileNo;
    }

    public void setPileNo(String pileNo) { this.pileNo = pileNo; }

    public Integer getGateType()
    {
        return gateType;
    }

    public void setGateType(Integer gateType) { this.gateType = gateType; }

    public Integer getSquareType()
    {
        return squareType;
    }

    public void setSquareType(Integer squareType) { this.squareType = squareType; }

    @Size(min = 0, max = 100, message = "坐标不能超过100个字符")
    public String getGps()
    {
        return gps;
    }

    public void setGps(String gps) { this.gps = gps; }

    public Integer getOverloadControl()
    {
        return overloadControl;
    }

    public void setOverloadControl(Integer overloadControl) { this.overloadControl = overloadControl; }

    public Integer getOverloadControlSite()
    {
        return overloadControlSite;
    }

    public void setOverloadControlSite(Integer overloadControlSite) { this.overloadControlSite = overloadControlSite; }

    public String getRoadBelong()
    {
        return roadBelong;
    }

    public void setRoadBelong(String roadBelong) { this.roadBelong = roadBelong; }

    public Long getAdminStationId()
    {
        return adminStationId;
    }

    public void setAdminStationId(Long adminStationId) { this.adminStationId = adminStationId; }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId) { this.deptId = deptId; }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }

    public Long getCreateUserId()
    {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) { this.createUserId = createUserId; }

    public Long getUpdateUserId()
    {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) { this.updateUserId = updateUserId; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("recordNum" ,getRecordNum())
                .append("version" ,getVersion())
                .append("netWork" ,getNetWork())
                .append("stationId" ,getStationId())
                .append("stationName" ,getStationName())
                .append("stationGb" ,getStationGb())
                .append("stationHex" ,getStationHex())
                .append("pileNo" ,getPileNo())
                .append("gateType" ,getGateType())
                .append("squareType" ,getSquareType())
                .append("gps" ,getGps())
                .append("overloadControl" ,getOverloadControl())
                .append("overloadControlSite" ,getOverloadControlSite())
                .append("roadBelong" ,getRoadBelong())
                .append("adminStationId" ,getAdminStationId())
                .append("deptId" ,getDeptId())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("createUserId", getCreateUserId())
                .append("updateUserId", getUpdateUserId())
                .toString();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
