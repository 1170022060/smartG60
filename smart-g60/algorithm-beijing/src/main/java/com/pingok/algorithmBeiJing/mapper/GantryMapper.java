package com.pingok.algorithmBeiJing.mapper;


import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 数据层
 *
 * @author qiumin
 */
public interface GantryMapper {

    @Select({"SELECT " +
            "TO_CHAR(gtd.TRANS_TIME,'yyyy-MM-dd HH24:mi:ss') as \"transTime\", " +
            "TO_CHAR(NVL( gtd.LAST_GANTRY_TIME, gtd.OBU_LAST_GANTRY_TIME ),'yyyy-MM-dd HH24:mi:ss') as \"lastGantryTime\", " +
            "SUBSTR( gtd.VEHICLE_PLATE, 0, LENGTH( gtd.VEHICLE_PLATE ) - 2 ) AS \"vehiclePlate\", " +
            "gtd.GANTRY_ID as \"gantryId\", " +
            "tgi1.DEVICE_NAME as \"gantryName\", " +
            "tgi.DEVICE_ID AS \"lastGantryId\",  " +
            "tgi.DEVICE_NAME AS \"lastGantryName\"  " +
            "FROM " +
            "TBL_SHAR_GTD_RES_SENDER_2023 gtd " +
            "LEFT JOIN ( SELECT DEVICE_HEX,DEVICE_NAME, MIN( DEVICE_ID ) AS DEVICE_ID FROM TBL_GANTRY_INFO GROUP BY DEVICE_HEX,DEVICE_NAME ) tgi ON tgi.DEVICE_HEX = NVL( gtd.LAST_GANTRY_HEX, gtd.OBU_LAST_GANTRY_HEX )  " +
            "LEFT JOIN TBL_GANTRY_INFO tgi1 ON tgi1.DEVICE_ID = gtd.GANTRY_ID " +
            "WHERE " +
            "gtd.TRANS_TIME >= TO_DATE( #{startTime}, 'yyyy-MM-dd HH24:mi:ss' )  " +
            "AND gtd.TRANS_TIME <= TO_DATE( #{endTime}, 'yyyy-MM-dd HH24:mi:ss' ) " +
            "AND SUBSTR( gtd.VEHICLE_PLATE, 0, LENGTH( gtd.VEHICLE_PLATE ) - 2 ) = #{plate} " +
            "ORDER BY gtd.TRANS_TIME"})
    List<Map> trajectoryData(@Param("plate") String plate,@Param("startTime") String startTime, @Param("endTime") String endTime);

    @Select({"SELECT " +
            "TO_CHAR(gtd.TRANS_TIME,'yyyy-MM-dd HH24:mi:ss') as \"transTime\", " +
            "TO_CHAR(NVL( gtd.LAST_GANTRY_TIME, gtd.OBU_LAST_GANTRY_TIME ),'yyyy-MM-dd HH24:mi:ss') as \"lastGantryTime\", " +
            "SUBSTR( gtd.VEHICLE_PLATE, 0, LENGTH( gtd.VEHICLE_PLATE ) - 2 ) AS \"vehiclePlate\", " +
            "gtd.GANTRY_ID as \"gantryId\", " +
            "tgi1.DEVICE_NAME as \"gantryName\", " +
            "tgi.DEVICE_ID AS \"lastGantryId\"  " +
            "FROM " +
            "TBL_SHAR_GTD_RES_SENDER_2023 gtd " +
            "LEFT JOIN ( SELECT DEVICE_HEX, MIN( DEVICE_ID ) AS DEVICE_ID FROM TBL_GANTRY_INFO GROUP BY DEVICE_HEX ) tgi ON tgi.DEVICE_HEX = NVL( gtd.LAST_GANTRY_HEX, gtd.OBU_LAST_GANTRY_HEX )  " +
            "LEFT JOIN TBL_GANTRY_INFO tgi1 ON tgi1.DEVICE_ID = gtd.GANTRY_ID " +
            "WHERE " +
            "gtd.TRANS_TIME >= TO_DATE( #{startTime}, 'yyyy-MM-dd HH24:mi:ss' )  " +
            "AND gtd.TRANS_TIME <= TO_DATE( #{endTime}, 'yyyy-MM-dd HH24:mi:ss' ) " +
            "AND gtd.GANTRY_ID = #{gantryId} " +
            "ORDER BY gtd.TRANS_TIME"})
    List<Map> gantryTransactionLog(@Param("gantryId") String gantryId,@Param("startTime") String startTime, @Param("endTime") String endTime);

    @Select({"SELECT " +
            "gtd.GANTRY_ID AS \"id\", " +
            "tgi1.DEVICE_NAME AS \"name\"  " +
            "FROM " +
            "TBL_SHAR_GTD_RES_SENDER_2023 gtd " +
            "RIGHT JOIN TBL_GANTRY_INFO tgi1 ON tgi1.DEVICE_ID = gtd.GANTRY_ID  " +
            "WHERE " +
            "gtd.TRANS_TIME >= TO_DATE( #{startTime}, 'yyyy-MM-dd HH24:mi:ss' )  " +
            "AND gtd.TRANS_TIME <= TO_DATE( #{endTime}, 'yyyy-MM-dd HH24:mi:ss' )  " +
            "GROUP BY " +
            "gtd.GANTRY_ID, " +
            "tgi1.DEVICE_NAME"})
    List<Map> gantrys(@Param("startTime") String startTime, @Param("endTime") String endTime);

}
