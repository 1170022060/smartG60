package com.pingok.vocational.service.device;


import com.pingok.vocational.domain.device.TblDeviceInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 设备信息 服务层
 *
 * @author ruoyi
 */
public interface TblDeviceInfoService {

    /**
     * 根据设备类型查询
     * @param deviceType
     * @return
     */
    List<TblDeviceInfo> selectByDeviceType(Integer deviceType);

    /**
     * 根据设备类目查询设备
     * @param deviceCategory 设备类目id
     * @return
     */
    List<Map> findBydeviceCategory(Long deviceCategory);
    /**
     * 根据Id查询设备类目信息
     *
     * @param Id Id
     * @return 设备类目信息
     */
    public TblDeviceInfo selectDeviceInfoById(Long Id);

    /**
     * 通过设备类目、设备状态、安装地点、设备ID、使用方、管理方、使用寿命、设备名称查询设备对应信息
     *
     * @param deviceCategory 设备类目
     * @param status 设备状态
     * @param fieldBelong 安装地点
     * @param deviceId 设备ID
     * @param userSide 使用方
     * @param managementSide 管理方
     * @param serviceLife 使用寿命
     * @param deviceName 设备名称
     * @param deviceType 设备类型(内部)
     * @return 设备对应信息
     */
    List<Map> selectDeviceInfo(Long deviceCategory, Integer status, Long fieldBelong, String deviceId, Long userSide, Long managementSide, Integer serviceLife, String deviceName,Integer deviceType);

    /**
     * 新增设备信息
     *
     * @param tblDeviceInfo 设备信息
     * @return 结果
     */
    public int insertDeviceInfo(TblDeviceInfo tblDeviceInfo);

    /**
     * 修改设备信息
     *
     * @param tblDeviceInfo 设备信息
     * @return 结果
     */
    public int updateDeviceInfo(TblDeviceInfo tblDeviceInfo);

    /**
     * 根据id,设备状态类型修改设备状态
     *
     * @param id id
     * @param status 设备状态类型
     * @return 结果
     */
    public int updateStatus(Long id,Integer status);

    /**
     * 校验设备ID
     *
     * @param tblDeviceInfo 设备信息
     * @return 结果
     */
    public String checkDeviceIdUnique(TblDeviceInfo tblDeviceInfo);

    /**
     * 校验设备名称
     *
     * @param tblDeviceInfo 设备信息
     * @return 结果
     */
    public String checkDeviceNameUnique(TblDeviceInfo tblDeviceInfo);

    /**
     * 返回ID和设备信息
     *
     * @return 设备信息
     */
    public List<Map> selectDeviceName();

    /**
     * 情报板查询
     *
     * @return 情报板信息
     */
    public List<Map> selectInfoBoard(Integer deviceType,String deviceName,String pileNo,String manufacturer,String deviceModel);
}
