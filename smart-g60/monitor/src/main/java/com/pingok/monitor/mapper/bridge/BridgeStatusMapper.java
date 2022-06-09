package com.pingok.monitor.mapper.bridge;

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
public interface BridgeStatusMapper {

    @Select("<script>" +
            "SELECT " +
            "tbi.ID AS \"id\", " +
            "tbi.CODE as \"code\", " +
            "tbi.LOCAL_CODE as \"localCode\", " +
            "tbi.NAME AS \"name\",  " +
            "(tbi.LONGITUDE || ',' || tbi.LATITUDE) as \"gps\", " +
            "tbi.LOCATION as \"location\", " +
            "tbi.ENTIRETY_RATING as \"entiretyRating\", " +
            "tbi.COMPONENT_RATING as \"componentRating\", " +
            "tbi.DEVICE_STATUS as \"deviceStatus\", " +
            "tbi.UPDATE_TIME as \"time\" " +
            "FROM " +
            "TBL_BRIDGE_INFO tbi " +
            "WHERE 1=1 " +
            "<when test='name != null'> " +
            "and tbi.NAME like ('%'||#{name}||'%') " +
            "</when>" +
            "</script>")
    List<Map> list(@Param("name") String name);

    @Select("<script>" +
            "SELECT " +
            "tba.ID AS \"id\", " +
            "tba.SENSOR_NO as \"sensorNo\", " +
            "tba.STATUS as \"status\" " +
            "FROM " +
            "TBL_BRIDGE_ACQUISITION tba " +
            "WHERE 1=1 " +
            "<when test='structureId != null'> " +
            "and tba.STRUCTURE_ID = #{structureId} " +
            "</when>" +
            "</script>")
    List<Map> acquisition(@Param("structureId") Long structureId);

    @Select("<script>" +
            "SELECT " +
            "tbc.ID AS \"id\", " +
            "tbc.ACQUISITION_NO as \"acquisitionNo\", " +
            "tbc.STATUS as \"status\" " +
            "FROM " +
            "TBL_BRIDGE_COLLECTION tbc " +
            "WHERE 1=1 " +
            "<when test='structureId != null'> " +
            "and tbc.STRUCTURE_ID = #{structureId} " +
            "</when>" +
            "</script>")
    List<Map> collection(@Param("structureId") Long structureId);

}
