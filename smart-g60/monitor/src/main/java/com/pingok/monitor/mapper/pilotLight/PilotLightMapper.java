package com.pingok.monitor.mapper.pilotLight;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
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

    @Select({"<script>" +"SELECT COUNT(*) as \"count\",EVENT_LEVEL as \"eventLevel\" from TBL_EVENT_RECORD a " +
            "WHERE EVENT_TYPE=23 " +
            "<when test='startTime != null'> " +
            "and a.EVENT_TIME &gt;= #{startTime} " +
            "</when> " +
            "<when test='endTime != null'> " +
            "and a.EVENT_TIME &lt;= #{endTime} " +
            "</when> " +
            "GROUP BY EVENT_LEVEL"+
            "</script>"})
    List<Map> visibilityTotal(@Param("startTime")Date startTime,@Param("endTime")Date endTime);

    @Select({"<script>" +
            "SELECT to_char(TIME,'yyyy-MM-dd HH24:mi:ss') as \"time\",STATUS_DETAILS as \"statusDetails\" " +
            " FROM TBL_DEVICE_STATUS_LOG " +
            "WHERE DEVICE_ID =10938 AND STATUS IS NULL " +
            "<when test='startTime != null'> " +
            "and TIME &gt;= #{startTime} " +
            "</when> " +
            "<when test='endTime != null'> " +
            "and TIME &lt;= #{endTime} " +
            "</when> " +
            "order by TIME " +
            "</script>"})
    List<Map> visibilityTrendXD(@Param("startTime")Date startTime,@Param("endTime")Date endTime);

    @Select({"<script>" +
            "SELECT to_char(TIME,'yyyy-MM-dd HH24:mi:ss') as \"time\",STATUS_DETAILS as \"statusDetails\" " +
            " FROM TBL_DEVICE_STATUS_LOG " +
            "WHERE DEVICE_ID =10939 AND STATUS IS NULL " +
            "<when test='startTime != null'> " +
            "and TIME &gt;= #{startTime} " +
            "</when> " +
            "<when test='endTime != null'> " +
            "and TIME &lt;= #{endTime} " +
            "</when> " +
            "order by TIME " +
            "</script>"})
    List<Map> visibilityTrendDZ(@Param("startTime")Date startTime,@Param("endTime")Date endTime);
}
