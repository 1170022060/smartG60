package com.pingok.vocational.service.device.impl;

import com.alibaba.fastjson.JSON;
import com.pingok.vocational.domain.device.TblDeviceCategory;
import com.pingok.vocational.domain.device.vo.TreeSelect;
import com.pingok.vocational.mapper.device.TblDeviceCategoryMapper;
import com.pingok.vocational.service.device.TblDeviceCategoryService;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.utils.PinYinUtil;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 设备类目信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class TblDeviceCategoryServiceImpl implements TblDeviceCategoryService {

    @Autowired
    private TblDeviceCategoryMapper tblDeviceCategoryMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public List<TblDeviceCategory> findByNum(String categoryNum) {
        return tblDeviceCategoryMapper.findByNum(categoryNum);
    }

    @Override
    public List<TblDeviceCategory> selectAll() {
        return tblDeviceCategoryMapper.selectAll();
    }

    @Override
    public TblDeviceCategory selectCategoryById(Long Id) {
        TblDeviceCategory tblDeviceCategory=tblDeviceCategoryMapper.selectByPrimaryKey(Id);
        tblDeviceCategory.setCategoryPostStr(JSON.parseObject(tblDeviceCategory.getCategoryPost(), Long[].class));
        return tblDeviceCategory;
    }


    @Override
    public List<Map> selectDeviceCategory(String categoryName, Long parentCategory, Integer status) {
        return tblDeviceCategoryMapper.selectDeviceCategory(categoryName, parentCategory, status);
    }

    @Override
    public int insertDeviceCategory(TblDeviceCategory tblDeviceCategory) {
        tblDeviceCategory.setId(remoteIdProducerService.nextId());
        if (tblDeviceCategory.getParentCategory() == null) {
            tblDeviceCategory.setParentCategory(0L);
        }
        tblDeviceCategory.setStatus(1);
        if(StringUtils.isNull(tblDeviceCategory.getCategoryNum()))
        {
            tblDeviceCategory.setCategoryNum(PinYinUtil.getPinYinHeadChar(tblDeviceCategory.getCategoryName()));
        }
        if(tblDeviceCategory.getCategoryPostStr()!=null)
        {
            tblDeviceCategory.setCategoryPost(JSON.toJSONString(tblDeviceCategory.getCategoryPostStr()));
        }
        tblDeviceCategory.setCreateTime(new Date());
        tblDeviceCategory.setCreateUserId(SecurityUtils.getUserId());
        return tblDeviceCategoryMapper.insert(tblDeviceCategory);
    }

    @Override
    public int updateDeviceCategory(TblDeviceCategory tblDeviceCategory) {
        if(tblDeviceCategory.getCategoryPostStr()!=null)
        {
            tblDeviceCategory.setCategoryPost(JSON.toJSONString(tblDeviceCategory.getCategoryPostStr()));
        }
        tblDeviceCategory.setUpdateTime(new Date());
        tblDeviceCategory.setUpdateUserId(SecurityUtils.getUserId());
        return tblDeviceCategoryMapper.updateByPrimaryKey(tblDeviceCategory);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        TblDeviceCategory tblDeviceCategory = tblDeviceCategoryMapper.selectByPrimaryKey(id);
        tblDeviceCategory.setUpdateTime(new Date());
        tblDeviceCategory.setUpdateUserId(SecurityUtils.getUserId());
        tblDeviceCategory.setStatus(status);
        return tblDeviceCategoryMapper.updateByPrimaryKey(tblDeviceCategory);
    }


    @Override
    public String checkCategoryNameUnique(TblDeviceCategory tblDeviceCategory) {
        Long id = StringUtils.isNull(tblDeviceCategory.getId()) ? -1L : tblDeviceCategory.getId();
        Long parentCategory = StringUtils.isNull(tblDeviceCategory.getParentCategory()) ? 0L : tblDeviceCategory.getParentCategory();
        TblDeviceCategory info = tblDeviceCategoryMapper.checkCategoryNameUnique(tblDeviceCategory.getCategoryName(), parentCategory);
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public List<Map> selectPost() {
        return tblDeviceCategoryMapper.selectPost();
    }
    /**
     * 构建前端所需要树结构
     *
     * @param deviceCategoryList 设备类目列表
     * @return 树结构列表
     */
    @Override
    public List<TblDeviceCategory> buildMenuTree(List<TblDeviceCategory> deviceCategoryList) {
        List<TblDeviceCategory> returnList = new ArrayList<TblDeviceCategory>();
        List<Long> tempList = new ArrayList<Long>();
        for (TblDeviceCategory tblDeviceCategory : deviceCategoryList) {
            tempList.add(tblDeviceCategory.getId());
        }
        for (Iterator<TblDeviceCategory> iterator = deviceCategoryList.iterator(); iterator.hasNext(); ) {
            TblDeviceCategory category = (TblDeviceCategory) iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(category.getParentCategory())) {
                recursionFn(deviceCategoryList, category);
                returnList.add(category);
            }
        }
        if (returnList.isEmpty()) {
            returnList = deviceCategoryList;
        }
        return returnList;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param deviceCategoryList 设备类目列表
     * @return 下拉树结构列表
     */
    @Override
    public List<TreeSelect> buildMenuTreeSelect(List<TblDeviceCategory> deviceCategoryList) {
        List<TblDeviceCategory> categoryTrees = buildMenuTree(deviceCategoryList);
        return categoryTrees.stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list     分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<TblDeviceCategory> getChildPerms(List<TblDeviceCategory> list, int parentId) {
        List<TblDeviceCategory> returnList = new ArrayList<TblDeviceCategory>();
        for (Iterator<TblDeviceCategory> iterator = list.iterator(); iterator.hasNext(); ) {
            TblDeviceCategory t = (TblDeviceCategory) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentCategory() == parentId) {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<TblDeviceCategory> list, TblDeviceCategory t) {
        // 得到子节点列表
        List<TblDeviceCategory> childList = getChildList(list, t);
        t.setChildren(childList);
        for (TblDeviceCategory tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<TblDeviceCategory> getChildList(List<TblDeviceCategory> list, TblDeviceCategory t) {
        List<TblDeviceCategory> tlist = new ArrayList<TblDeviceCategory>();
        Iterator<TblDeviceCategory> it = list.iterator();
        while (it.hasNext()) {
            TblDeviceCategory n = (TblDeviceCategory) it.next();
            if (n.getParentCategory().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<TblDeviceCategory> list, TblDeviceCategory t) {
        return getChildList(list, t).size() > 0;
    }
}
