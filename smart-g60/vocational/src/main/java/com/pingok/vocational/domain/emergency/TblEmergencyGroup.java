package com.pingok.vocational.domain.emergency;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 应急小组信息表 TBL_EMERGENCY_GROUP
 *
 * @author ruoyi
 */
@Table(name = "TBL_EMERGENCY_GROUP")
public class TblEmergencyGroup implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 组名*/
    @Excel(name = "组名")
    private String groupName;

    /** 组长*/
    @Excel(name = "组长")
    private Long groupLeader;

    /** 副组长*/
    @Excel(name = "副组长")
    private String groupLeaderDep;

    /** 组员*/
    @Excel(name = "组员")
    private String groupMembers;

    /** 职责*/
    @Excel(name = "职责")
    private String duty;

    /** 备注*/
    @Excel(name = "备注")
    private String remark;

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

    /** 副组长*/
    @Excel(name = "副组长")
    private Long[] groupLeaderDepStr;

    /** 组员*/
    @Excel(name = "组员")
    private Long[] groupMembersStr;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Size(min = 0, max = 20, message = "组名长度不能超过20个字符")
    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public Long getGroupLeader()
    {
        return groupLeader;
    }

    public void setGroupLeader(Long groupLeader)
    {
        this.groupLeader = groupLeader;
    }

    @Size(min = 0, max = 100, message = "副组长长度不能超过100个字符")
    public String getGroupLeaderDep()
    {
        return groupLeaderDep;
    }

    public void setGroupLeaderDep(String groupLeaderDep)
    {
        this.groupLeaderDep = groupLeaderDep;
    }

    @Size(min = 0, max = 100, message = "组员长度不能超过100个字符")
    public String getGroupMembers()
    {
        return groupMembers;
    }

    public void setGroupMembers(String groupMembers)
    {
        this.groupMembers = groupMembers;
    }

    @Size(min = 0, max = 100, message = "职责不能超过100个字符")
    public String getDuty()
    {
        return duty;
    }

    public void setDuty(String duty)
    {
        this.duty = duty;
    }

    @Size(min = 0, max = 255, message = "备注长度不能超过255个字符")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Long[] getGroupLeaderDepStr() {
        return groupLeaderDepStr;
    }

    public void setGroupLeaderDepStr(Long[] groupLeaderDepStr) {
        this.groupLeaderDepStr = groupLeaderDepStr;
    }

    public Long[] getGroupMembersStr() {
        return groupMembersStr;
    }

    public void setGroupMembersStr(Long[] groupMembersStr) {
        this.groupMembersStr = groupMembersStr;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id",getId())
                .append("groupName",getGroupName())
                .append("groupLeader",getGroupLeader())
                .append("groupLeaderDep",getGroupLeaderDep())
                .append("groupMembers",getGroupMembers())
                .append("duty",getDuty())
                .append("remark", getRemark())
                .append("status",getStatus())
                .append("createTime",getCreateTime())
                .append("updateTime",getUpdateTime())
                .append("createUserId",getCreateUserId())
                .append("updateUserId",getUpdateUserId())
                .toString();
    }
}
