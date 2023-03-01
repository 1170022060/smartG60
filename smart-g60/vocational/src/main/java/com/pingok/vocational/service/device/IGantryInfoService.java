package com.pingok.vocational.service.device;

import com.pingok.vocational.domain.device.TblGantryInfo;

import java.util.List;
import java.util.Map;

/**
 * 门架设备信息 服务层
 *
 * @author ruoyi
 */

public interface IGantryInfoService {

    /**
     * 查询所有门架信息
     * @return
     */
    List<TblGantryInfo> selectAll();

    /**
     * 根据Id查询门架设备信息
     *
     * @param Id Id
     * @return 设备类目信息
     */
    public TblGantryInfo selectDeviceInfoGantryById(Long Id);

    /**
     * 通过设备类目、设备状态、安装地点、设备ID、使用方、管理方、使用寿命、设备名称查询门架信息
     *
     * @param deviceCategory 设备类目
     * @param status 设备状态
     * @param fieldBelong 安装地点
     * @param deviceId 设备ID
     * @param userSide 使用方
     * @param managementSide 管理方
     * @param serviceLife 使用寿命
     * @param deviceName 设备名称
     * @return 设备对应信息
     */
    List<Map> selectDeviceInfoGantry(Long deviceCategory, Integer status, Long fieldBelong, String deviceId, Long userSide, Long managementSide, Integer serviceLife, String deviceName);

    /**
     * 新增设备信息
     *
     * @param tblDeviceInfoGantry 门架设备信息
     * @return 结果
     */
    public int insertDeviceInfoGantry(TblGantryInfo tblDeviceInfoGantry);

    /**
     * 修改设备信息
     *
     * @param tblDeviceInfoGantry 门架设备信息
     * @return 结果
     */
    public int updateDeviceInfoGantry(TblGantryInfo tblDeviceInfoGantry);

    /**
     * 校验设备ID
     *
     * @param tblDeviceInfoGantry 门架设备信息
     * @return 结果
     */
    public String checkDeviceIdUnique(TblGantryInfo tblDeviceInfoGantry);

    /**
     * 校验设备名称
     *
     * @param tblDeviceInfoGantry 门架设备信息
     * @return 结果
     */
    public String checkDeviceNameUnique(TblGantryInfo tblDeviceInfoGantry);

    /**
     * 查询门架设备名称
     *
     * @return 门架设备名称
     */
    List<Map> selectGantryName();
}
