package com.pingok.vocational.mapper.primaryCar;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface TblOwInfoMapper {
    @Select({"<script>" +
            "SELECT  " +
            "ow.ID as \"id\", " +
            "ow.UNIQUE_ID as \"uniqueId\", " +
            "ow.SITE_NAME as \"stationName\", " +
            "ow.SITE_CODE as \"stationId\", " +
            "ow.EQUIP_CODE as \"equipCode\", " +
            "ow.CHECK_TIME as \"checkTime\", " +
            "ow.VEHICLE_NO as \"vehPlate\", " +
            "a.DICT_LABEL as \"vehColor\", " +
            "ow.VEHICLE_AXLES_TYPE as \"vehAxlesType\", " +
            "ow.TOTAL as \"totalWeight\", " +
            "ow.AXLES as \"axles\", " +
            "ow.SPEED as \"speed\", " +
            "ow.LIMIT_WEIGHT as \"limitWeight\", " +
            "ow.OVER_WEIGHT as \"overWeight\", " +
            "ow.OVER_RATE as \"overRate\", " +
            "case when ow.FLOW_STAUS=1 then '未处置' when ow.FLOW_STAUS=2 then '立案'  " +
            "when ow.FLOW_STAUS=3 then '不予立案' when ow.FLOW_STAUS=4 then '结案' ELSE null end as \"flowStatus\", " +
            "ow.PLATE_PIC as \"platePic\", " +
            "ow.FIRSTHEADER_PIC as \"firstHeaderPic\", " +
            "ow.DEGREE45_PIC as \"degree45Pic\", " +
            "ow.SIDE_PIC as \"sidePic\", " +
            "ow.VIDEO as \"video\" " +
            "FROM TBL_OW_INFO ow " +
            "left join  SYS_DICT_DATA a on a.DICT_VALUE=ow.PLATE_COLOR and a.DICT_TYPE='veh_color' " +
            "where ow.EQUIP_CODE like 'G0060%' " +
            "<when test='vehPlate != null'> " +
            "and ow.VEHICLE_NO like '%' || #{vehPlate} || '%' " +
            "</when>"+
            "<when test='checkStartTime != null'> " +
            "and ow.CHECK_TIME &gt;= #{checkStartTime} " +
            "</when>"+
            "<when test='checkEndTime != null'> " +
            "and ow.CHECK_TIME &lt;= #{checkEndTime} " +
            "</when>" +
            "order by ow.CHECK_TIME desc " +
            "</script>"})
    List<Map> selectOwInfo(@Param("vehPlate") String vehPlate, @Param("checkStartTime") Date checkStartTime,@Param("checkEndTime") Date checkEndTime);
}
