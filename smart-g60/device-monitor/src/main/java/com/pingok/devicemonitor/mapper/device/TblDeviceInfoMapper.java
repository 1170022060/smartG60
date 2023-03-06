package com.pingok.devicemonitor.mapper.device;

import com.pingok.devicemonitor.domain.device.TblDeviceInfo;

import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TblDeviceInfoMapper extends CommonRepository<TblDeviceInfo> {
    @Select("SELECT * FROM TBL_DEVICE_INFO where 1=1 and DEVICE_TYPE = #{deviceType} ")
    List<TblDeviceInfo> findByDeviceType(@Param("deviceType") String deviceType);

    @Select("SELECT ID as \"id\", DEVICE_ID as \"deviceId\", DEVICE_IP as \"deviceIp\" " +
            " FROM TBL_DEVICE_INFO where 1=1 and DEVICE_TYPE = 9 and FIELD_BELONG = 5 ")
    List<TblDeviceInfo> selectVmsInfo();

    @Select("SELECT ID as \"id\", DEVICE_ID as \"deviceId\", DEVICE_IP as \"deviceIp\", " +
            "PORT as \"port\", DIRECTION as \"direction\", PILE_NO as \"pileNo\", " +
            "REMARK as \"remark\", SLAVE_ID as \"slaveId\" " +
            " FROM TBL_DEVICE_INFO where 1=1 and DEVICE_TYPE = 11 ")
    List<TblDeviceInfo> selectVdtInfo();
}