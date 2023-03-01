package com.pingok.vocational.domain.device.vo;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pingok.vocational.domain.device.TblDeviceCategory;
import com.pingok.vocational.domain.field.TblFieldInfo;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.domain.SysDept;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Treeselect树结构实体类
 * 
 * @author ruoyi
 */
public class TreeSelect implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long value;

    /** 节点名称 */
    private String label;

    /** 岗位 */
    private Long[] post;

    /** 子节点 */
    private List<TreeSelect> children;

    public TreeSelect()
    {

    }

    public TreeSelect(TblDeviceCategory tblDeviceCategory)
    {
        this.value = tblDeviceCategory.getId();
        this.label = tblDeviceCategory.getCategoryName();
        if(StringUtils.isNull(tblDeviceCategory.getCategoryPost()))
        {
            this.post = null;
        }
        else
        {
            this.post = JSON.parseObject(tblDeviceCategory.getCategoryPost(), Long[].class);
        }
        this.children = tblDeviceCategory.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(TblFieldInfo tblFieldInfo) {
        this.value = tblFieldInfo.getId();
        this.label = tblFieldInfo.getFieldName();
        this.children = tblFieldInfo.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public Long getValue()
    {
        return value;
    }

    public void setValue(Long value)
    {
        this.value = value;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public Long[] getPost() {
        return post;
    }

    public void setPost(Long[] post) {
        this.post = post;
    }

    public List<TreeSelect> getChildren()
    {
        return children;
    }

    public void setChildren(List<TreeSelect> children)
    {
        this.children = children;
    }
}
