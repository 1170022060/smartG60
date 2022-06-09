package com.pingok.devicemonitor.mapper.gantry;

import com.pingok.devicemonitor.domain.gantry.TblGantryInfo;

import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TblGantryInfoMapper extends CommonRepository<TblGantryInfo> {

    @Select("select ID as \"id\", DEVICE_ID as \"deviceId\", DEVICE_NAME as \"deviceName\" " +
            "from TBL_GANTRY_INFO where ID = #{infoId}")
    TblGantryInfo findByInfoId(@Param("infoId") Long infoId);
}