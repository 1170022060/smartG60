package com.pingok.vocational.service.device;

import com.pingok.vocational.domain.device.vo.TreeSelect;
import com.pingok.vocational.domain.device.TblDeviceCategory;

import java.util.List;
import java.util.Map;

/**
 * 设备类目信息 业务层
 *
 * @author ruoyi
 */
public interface TblDeviceCategoryService {

    /**
     * 根据分类编码查询分类及其子类
     * @param categoryNum
     * @return
     */
    List<TblDeviceCategory> findByNum(String categoryNum);

    /**
     * 查询所有设备类目信息
     *
     * @return 设备类目信息
     */
    public List<TblDeviceCategory> selectAll();

    /**
     * 根据Id查询设备类目信息
     *
     * @param Id Id
     * @return 设备类目信息
     */
    public TblDeviceCategory selectCategoryById(Long Id);
    /**
     * 根据类目名称、父级类目、状态查询设备类目信息
     *
     * @param categoryName 类目名称
     * @param parentCategory 父级类目
     * @param status 状态
     * @return 设备类目信息
     */
    public List<Map> selectDeviceCategory(String categoryName, Long parentCategory, Integer status);

    /**
     * 新增设备类目
     *
     * @param tblDeviceCategory 设备类目
     * @return 结果
     */
    public int insertDeviceCategory(TblDeviceCategory tblDeviceCategory);

    /**
     * 修改设备类目
     *
     * @param tblDeviceCategory 设备类目
     * @return 结果
     */
    public int updateDeviceCategory(TblDeviceCategory tblDeviceCategory);

    /**
     * 根据id,设备状态类型修改设备状态
     *
     * @param id id
     * @param status 设备状态类型
     * @return 结果
     */
    public int updateStatus(Long id,Integer status);

    /**
     * 构建前端所需要树结构
     *
     * @param menus 菜单列表
     * @return 树结构列表
     */
    public List<TblDeviceCategory> buildMenuTree(List<TblDeviceCategory> menus);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param menus 菜单列表
     * @return 下拉树结构列表
     */
    public List<TreeSelect> buildMenuTreeSelect(List<TblDeviceCategory> menus);

    /**
     * 校验类目名称
     *
     * @param tblDeviceCategory 设备类目
     * @return 结果
     */
    public String checkCategoryNameUnique(TblDeviceCategory tblDeviceCategory);
}
