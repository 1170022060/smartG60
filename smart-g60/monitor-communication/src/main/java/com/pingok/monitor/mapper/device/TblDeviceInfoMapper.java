package com.pingok.monitor.mapper.device;

import com.pingok.monitor.domain.device.TblDeviceInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TblDeviceInfoMapper extends CommonRepository<TblDeviceInfo> {
    @Select("SELECT * FROM TBL_DEVICE_INFO where 1=1 and DEVICE_ID = #{deviceId} ")
    TblDeviceInfo findByDeviceId(@Param("deviceId") String deviceId);
}