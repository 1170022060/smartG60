package com.pingok.monitor.mapper.pilotLight;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 数据层
 *
 * @author qiumin
 */
@Mapper
public interface PilotLightMapper {
    @Select("SELECT  " +
            "tdi.DEVICE_ID as \"deviceId\", " +
            "tdi.DEVICE_NAME as \"deviceName\", " +
            "tds.STATUS as \"status\", " +
            "tds.STATUS_DETAILS as \"statusDetails\", " +
            "tdi.CAMERA_ID as \"cameraId\" " +
            "FROM " +
            "TBL_DEVICE_INFO tdi  " +
            "LEFT JOIN TBL_DEVICE_STATUS tds ON tds.DEVICE_ID = tdi.ID " +
            "WHERE " +
            "DEVICE_TYPE = #{deviceType}")
    List<Map> pilotLightStatus(@Param("deviceType") Integer deviceType);

}
