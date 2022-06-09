package com.pingok.vocational.service.emergency;

import com.pingok.vocational.domain.emergency.TblEmergencyGroup;

import java.util.List;
import java.util.Map;

/**
 * 应急小组信息 业务层
 *
 * @author ruoyi
 */
public interface TblEmergencyGroupService {

    /**
     * 根据Id查询应急资源信息
     *
     * @param Id Id
     * @return 应急资源信息
     */
    public TblEmergencyGroup selectEmergencyGroupById(Long Id);

    /**
     * 根据组名、组长、使用状态查询应急资源信息
     *
     * @param groupName 组名
     * @param groupLeader 组长
     * @param status 使用状态
     * @return 应急资源信息
     */
    public List<Map> selectEmergencyGroup(String groupName, Long groupLeader,Integer status);

    /**
     * 新增应急小组信息
     *
     * @param tblEmergencyGroup 应急小组信息
     * @return 结果
     */
    public int insertEmergencyGroup(TblEmergencyGroup tblEmergencyGroup);

    /**
     * 修改应急小组信息
     *
     * @param tblEmergencyGroup 应急小组信息
     * @return 结果
     */
    public int updateEmergencyGroup(TblEmergencyGroup tblEmergencyGroup);

    /**
     * 根据id更改应急小组信息状态类型
     *
     * @param id id
     * @param status 状态类型
     * @return 结果
     */
    public int updateStatus(Long id,Integer status);

    /**
     * 校验组名
     *
     * @param tblEmergencyGroup 应急小组信息
     * @return 结果
     */
    public String checkGroupNameUnique(TblEmergencyGroup tblEmergencyGroup);

    /**
     * 查询部门-人员下拉列表
     *
     * @return 结果
     */
    public List<Map> selectDeptUser();
}
