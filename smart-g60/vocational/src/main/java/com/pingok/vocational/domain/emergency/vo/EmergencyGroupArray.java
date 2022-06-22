package com.pingok.vocational.domain.emergency.vo;

import com.ruoyi.common.core.annotation.Excel;

import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class EmergencyGroupArray implements Serializable {

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
    private String[] groupLeaderDep;

    /** 组员*/
    @Excel(name = "组员")
    private String[] groupMembers;

    /** 职责*/
    @Excel(name = "职责")
    private String duty;

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

    public String[] getGroupLeaderDep()
    {
        return groupLeaderDep;
    }

    public void setGroupLeaderDep(String[] groupLeaderDep)
    {
        this.groupLeaderDep = groupLeaderDep;
    }

    public String[] getGroupMembers()
    {
        return groupMembers;
    }

    public void setGroupMembers(String[] groupMembers)
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

}
