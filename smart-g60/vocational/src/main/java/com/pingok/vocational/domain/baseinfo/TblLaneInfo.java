package com.pingok.vocational.domain.baseinfo;

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
 * 车道信息表 TBL_LANE_INFO
 *
 * @author ruoyi
 */
@Table(name = "TBL_LANE_INFO")
public class TblLaneInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 网络编号*/
    @Excel(name = "网络编号")
    private String netWork;

    /** 收费站id*/
    @Excel(name = "收费站id")
    private String stationId;

    /** 车道ID*/
    @Excel(name = "车道ID")
    private String laneId;

    /** 车道名称*/
    @Excel(name = "车道名称")
    private String laneName;

    /** 车道名称（圈圈号)*/
    @Excel(name = "车道名称（圈圈号)")
    private String markName;

    /** 车道类型*/
    @Excel(name = "车道类型",readConverterExp = "1=MTC入,2=MTC出,3=ETC入,4=ETC出")
    private Integer laneType;

    /** 车道Node国标*/
    @Excel(name = "车道Node国标")
    private String laneGb;

    /** 车道Node国标*/
    @Excel(name = "车道Node十六进制")
    private String laneHex;

    /** 车道IP*/
    @Excel(name = "车道IP")
    private String laneIp;

    /** 路段ID*/
    @Excel(name = "路段ID")
    private String roadId;

    /** 路网中心老ID*/
    @Excel(name = "路网中心老ID")
    private String nodeId;

    /** 亭内监控IP*/
    @Excel(name = "亭内监控IP")
    private String inCameraIp;

    /** 车道视频IP*/
    @Excel(name = "亭内监控IP")
    private String outCameraIp;

    /** 使用状态 ：1.启用 0.停用*/
    @Excel(name = "使用状态",readConverterExp = "1=启用,2=停用")
    private Integer status;

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

    /** 备注*/
    @Excel(name = "备注")
    private String remark;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
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

    @Size(min = 0, max = 2, message = "车道ID不能超过2个字符")
    public String getLaneId()
    {
        return laneId;
    }

    public void setLaneId(String laneId)
    {
        this.laneId = laneId;
    }

    @Size(min = 0, max = 48, message = "车道名称不能超过48个字符")
    public String getLaneName()
    {
        return laneName;
    }

    public void setLaneName(String laneName)
    {
        this.laneName = laneName;
    }

    @Size(min = 0, max = 12, message = "车道名称（圈圈号)不能超过12个字符")
    public String getMarkName()
    {
        return markName;
    }

    public void setMarkName(String markName)
    {
        this.markName = markName;
    }

    public Integer getLaneType()
    {
        return laneType;
    }

    public void setLaneType(Integer laneType) { this.laneType = laneType; }

    @Size(min = 0, max = 21, message = "车道Node国标不能超过12个字符")
    public String getLaneGb()
    {
        return laneGb;
    }

    public void setLaneGb(String laneGb)
    {
        this.laneGb = laneGb;
    }

    @Size(min = 0, max = 10, message = "车道Node十六进制不能超过12个字符")
    public String getLaneHex()
    {
        return laneHex;
    }

    public void setLaneHex(String laneHex)
    {
        this.laneHex = laneHex;
    }

    @Size(min = 0, max = 15, message = "车道IP不能超过12个字符")
    public String getLaneIp()
    {
        return laneIp;
    }

    public void setLaneIp(String laneIp)
    {
        this.laneIp = laneIp;
    }

    @Size(min = 0, max = 5, message = "路段ID不能超过12个字符")
    public String getRoadId()
    {
        return roadId;
    }

    public void setRoadId(String roadId)
    {
        this.roadId = roadId;
    }

    @Size(min = 0, max = 14, message = "路网中心老ID不能超过12个字符")
    public String getNodeId()
    {
        return nodeId;
    }

    public void setNodeId(String nodeId)
    {
        this.nodeId = nodeId;
    }

    @Size(min = 0, max = 15, message = "亭内监控IP不能超过15个字符")
    public String getInCameraIp()
    {
        return inCameraIp;
    }

    public void setInCameraIp(String inCameraIp)
    {
        this.inCameraIp = inCameraIp;
    }

    @Size(min = 0, max = 15, message = "车道视频IP不能超过15个字符")
    public String getOutCameraIp()
    {
        return outCameraIp;
    }

    public void setOutCameraIp(String outCameraIp)
    {
        this.outCameraIp = outCameraIp;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

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

    @Size(min = 0, max = 255, message = "备注长度不能超过255个字符")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("netWork", getNetWork())
                .append("stationId", getStationId())
                .append("laneId", getLaneId())
                .append("laneName", getLaneName())
                .append("markName", getMarkName())
                .append("laneType", getLaneType())
                .append("laneGb", getLaneGb())
                .append("laneHex", getLaneHex())
                .append("laneIp", getLaneIp())
                .append("roadId", getRoadId())
                .append("nodeId", getNodeId())
                .append("inCameraIp", getInCameraIp())
                .append("outCameraIp", getOutCameraIp())
                .append("remark", getRemark())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("createUserId", getCreateUserId())
                .append("updateUserId", getUpdateUserId())
                .toString();
    }
}
