package com.pingok.vocational.service.device;

import com.pingok.vocational.domain.device.TblDeviceInfoLane;
import com.pingok.vocational.domain.device.vo.LaneEnum;

import java.util.List;
import java.util.Map;

/**
 * 车道设备信息 服务层
 *
 * @author ruoyi
 */
public interface TblDeviceInfoLaneService {

    /**
     * 根据Id查询设备类目信息
     *
     * @param Id Id
     * @return 设备类目信息
     */
    public TblDeviceInfoLane selectDeviceInfoLaneById(Long Id);

    /**
     * 通过设备类目、设备状态、所属站、设备ID、使用方、管理方、使用寿命、设备名称查询设备对应信息
     *
     * @param deviceCategory 设备类目
     * @param status 设备状态
     * @param laneBelong 所属车道
     * @param deviceId 设备ID
     * @param userSide 使用方
     * @param managementSide 管理方
     * @param serviceLife 使用寿命
     * @param deviceName 设备名称
     * @return 设备对应信息
     */
    List<Map> selectDeviceInfoLane(String laneHex,Long deviceCategory, Integer status, String laneBelong, String deviceId, Long userSide, Long managementSide, Integer serviceLife, String deviceName);


    /**
     * 新增设备信息
     * @param laneEnum 车道hex编码以及车道设备信息
     * @return 结果
     */
    public int insertDeviceInfoLane(LaneEnum laneEnum);

    /**
     * 修改设备信息
     *
     * @param tblDeviceInfoLane 车道设备信息
     * @return 结果
     */
    public int updateDeviceInfoLane(TblDeviceInfoLane tblDeviceInfoLane);

    /**
     * 根据id,设备状态类型修改车道设备状态
     *
     * @param id id
     * @param status 设备状态类型
     * @return 结果
     */
    public int updateStatus(Long id,Integer status);

    /**
     * 校验设备ID
     *
     * @param tblDeviceInfoLane 车道设备信息
     * @return 结果
     */
    public String checkDeviceIdUnique(TblDeviceInfoLane tblDeviceInfoLane);

    /**
     * 校验设备名称
     *
     * @param tblDeviceInfoLane 车道设备信息
     * @return 结果
     */
    public String checkDeviceNameUnique(TblDeviceInfoLane tblDeviceInfoLane);
}
