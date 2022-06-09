package com.pingok.vocational.service.emergency;

import com.pingok.vocational.domain.emergency.TblEmergencySupplies;

import java.util.List;
import java.util.Map;

/**
 * 应急资源信息 业务层
 *
 * @author ruoyi
 */
public interface TblEmergencySuppliesService {

    /**
     * 根据Id查询应急资源信息
     *
     * @param Id Id
     * @return 应急资源信息
     */
    public TblEmergencySupplies selectEmergencySuppliesById(Long Id);

    /**
     * 根据资源名称、车牌、专家名字、使用状态查询应急资源信息
     *
     * @param suppliesName 资源名称
     * @param vehPlate 车牌
     * @param expertName 专家名字
     * @param status 使用状态
     * @return 应急资源信息
     */
    public List<Map> selectSupplies(Integer suppliesType,String suppliesName, String vehPlate, String expertName,Integer status);

    /**
     * 根据资源类型查询应急资源名称
     *
     * @param suppliesType 资源类型
     * @return 应急资源名称
     */
    public List<Map> selectEmergencyName(Integer suppliesType);

    /**
     * 新增应急资源信息
     *
     * @param tblEmergencySupplies 应急资源信息
     * @return 结果
     */
    public int insertEmergencySupplies(TblEmergencySupplies tblEmergencySupplies);

    /**
     * 修改应急资源信息
     *
     * @param tblEmergencySupplies 应急资源信息
     * @return 结果
     */
    public int updateEmergencySupplies(TblEmergencySupplies tblEmergencySupplies);

    /**
     * 根据id更改应急资源信息状态类型
     *
     * @param id id
     * @param status 状态类型
     * @return 结果
     */
    public int updateStatus(Long id,Integer status);

    /**
     * 校验资源
     *
     * @param tblEmergencySupplies 应急资源信息
     * @return 结果
     */
    public String checkSuppliesNameUnique(TblEmergencySupplies tblEmergencySupplies);

    /**
     * 校验车辆
     *
     * @param tblEmergencySupplies 应急资源信息
     * @return 结果
     */
    public String checkVehPlateUnique(TblEmergencySupplies tblEmergencySupplies);

    /**
     * 校验专家
     *
     * @param tblEmergencySupplies 应急资源信息
     * @return 结果
     */
    public String checkExpertNameUnique(TblEmergencySupplies tblEmergencySupplies);
}
