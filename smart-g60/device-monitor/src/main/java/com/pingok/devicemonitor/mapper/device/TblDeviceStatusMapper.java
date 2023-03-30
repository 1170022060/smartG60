package com.pingok.devicemonitor.mapper.device;

import com.pingok.devicemonitor.domain.device.TblDeviceInfo;
import com.pingok.devicemonitor.domain.device.TblDeviceStatus;

import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TblDeviceStatusMapper extends CommonRepository<TblDeviceStatus> {
    @Select("SELECT * FROM TBL_DEVICE_STATUS where 1=1 and DEVICE_TYPE = 12 and DEVICE_ID = #{deviceId} ")
    TblDeviceStatus findLightRtStatus(@Param("deviceId") String deviceId);

    @Select("SELECT * FROM TBL_DEVICE_STATUS where 1=1 and DEVICE_ID = #{deviceId} ")
    TblDeviceStatus findByDeviceId(@Param("deviceId") Long deviceId);
}