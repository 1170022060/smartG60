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

    @Select({"SELECT  " +
            "TO_CHAR(gtd.TRANS_TIME,'yyyy-MM-dd HH24:mi:ss') as \"transTime\",  " +
            "TO_CHAR(NVL( gtd.LAST_GANTRY_TIME, gtd.OBU_LAST_GANTRY_TIME ),'yyyy-MM-dd HH24:mi:ss') as \"lastGantryTime\",  " +
            "SUBSTR( gtd.VEHICLE_PLATE, 0, LENGTH( gtd.VEHICLE_PLATE ) - 2 ) AS \"vehiclePlate\",  " +
            "tgi1.ID as \"gantryId\",  " +
            "tgi1.NAME as \"gantryName\",  " +
            "tgi.DEVICE_ID AS \"lastGantryId\",   " +
            "tgi.DEVICE_NAME AS \"lastGantryName\"   " +
            "FROM  " +
            "TBL_SHAR_GTD_RES_SENDER_${year} gtd  " +
            "LEFT JOIN ( SELECT HEX as DEVICE_HEX,NAME as DEVICE_NAME, MAX( ID ) AS DEVICE_ID FROM TBL_ROAD_NODES_INFO GROUP BY HEX,NAME ) tgi ON tgi.DEVICE_HEX = NVL( gtd.LAST_GANTRY_HEX, gtd.OBU_LAST_GANTRY_HEX )   " +
            "LEFT JOIN TBL_ROAD_NODES_INFO tgi1 ON tgi1.ID = gtd.GANTRY_ID  " +
            "WHERE  " +
            "gtd.TRANS_TIME >= TO_DATE( #{startTime}, 'yyyy-MM-dd HH24:mi:ss' )   " +
            "AND gtd.TRANS_TIME <= TO_DATE( #{endTime}, 'yyyy-MM-dd HH24:mi:ss' )  " +
            "AND SUBSTR( gtd.VEHICLE_PLATE, 0, LENGTH( gtd.VEHICLE_PLATE ) - 2 ) = #{plate}  " +
            "union " +
            "SELECT  " +
            "TO_CHAR(gtd.EN_TIME,'yyyy-MM-dd HH24:mi:ss') as \"transTime\",  " +
            "NULL as \"lastGantryTime\",  " +
            "SUBSTR( gtd.VEHICLE_ID, 0, LENGTH( gtd.VEHICLE_ID ) - 2 ) AS \"vehiclePlate\",  " +
            "tgi.ID as \"gantryId\",  " +
            "tgi.NAME as \"gantryName\",  " +
            "NULL AS \"lastGantryId\",   " +
            "NULL AS \"lastGantryName\"   " +
            "FROM  " +
            "TBL_SHAR_ENPD_RES_SENDER_${year} gtd  " +
            "LEFT JOIN TBL_LANE_INFO tli ON tli.LANE_HEX = gtd.EN_TOLL_LANE_HEX " +
            "LEFT JOIN TBL_BASE_STATION_INFO tbsi ON tbsi.NET_WORK = '3101' AND tbsi.STATION_ID = tli.STATION_ID " +
            "LEFT JOIN TBL_ROAD_NODES_INFO tgi ON tgi.HEX = tbsi.STATION_HEX  " +
            "WHERE  " +
            "gtd.EN_TIME >= TO_DATE( #{startTime}, 'yyyy-MM-dd HH24:mi:ss' )   " +
            "AND gtd.EN_TIME <= TO_DATE( #{endTime}, 'yyyy-MM-dd HH24:mi:ss' )  " +
            "AND SUBSTR( gtd.VEHICLE_ID, 0, LENGTH( gtd.VEHICLE_ID ) - 2 ) = #{plate}  " +
            "union " +
            "SELECT  " +
            "TO_CHAR(gtd.EX_TIME,'yyyy-MM-dd HH24:mi:ss') as \"transTime\",  " +
            "NULL as \"lastGantryTime\",  " +
            "SUBSTR( gtd.VEHICLE_ID, 0, LENGTH( gtd.VEHICLE_ID ) - 2 ) AS \"vehiclePlate\",  " +
            "tgi.ID as \"gantryId\",  " +
            "tgi.NAME as \"gantryName\",  " +
            "NULL AS \"lastGantryId\",   " +
            "NULL AS \"lastGantryName\"   " +
            "FROM  " +
            "TBL_SHAR_ETCTD_RES_SENDER_${year} gtd  " +
            "LEFT JOIN TBL_ROAD_NODES_INFO tgi ON tgi.NAME = gtd.EX_TOLL_STATION_NAME  " +
            "WHERE  " +
            "gtd.EX_TIME >= TO_DATE( #{startTime}, 'yyyy-MM-dd HH24:mi:ss' )   " +
            "AND gtd.EX_TIME <= TO_DATE( #{endTime}, 'yyyy-MM-dd HH24:mi:ss' )  " +
            "AND SUBSTR( gtd.VEHICLE_ID, 0, LENGTH( gtd.VEHICLE_ID ) - 2 ) = #{plate}  " +
            "union " +
            "SELECT  " +
            "TO_CHAR(gtd.EX_TIME,'yyyy-MM-dd HH24:mi:ss') as \"transTime\",  " +
            "NULL as \"lastGantryTime\",  " +
            "SUBSTR( gtd.VEHICLE_ID, 0, LENGTH( gtd.VEHICLE_ID ) - 2 ) AS \"vehiclePlate\",  " +
            "tgi.ID as \"gantryId\",  " +
            "tgi.NAME as \"gantryName\",  " +
            "NULL AS \"lastGantryId\",   " +
            "NULL AS \"lastGantryName\"   " +
            "FROM  " +
            "TBL_SHAR_OTD_RES_SENDER_${year} gtd  " +
            "LEFT JOIN TBL_ROAD_NODES_INFO tgi ON tgi.NAME = gtd.EX_TOLL_STATION_NAME  " +
            "WHERE  " +
            "gtd.EX_TIME >= TO_DATE( #{startTime}, 'yyyy-MM-dd HH24:mi:ss' )   " +
            "AND gtd.EX_TIME <= TO_DATE( #{endTime}, 'yyyy-MM-dd HH24:mi:ss' )  " +
            "AND SUBSTR( gtd.VEHICLE_ID, 0, LENGTH( gtd.VEHICLE_ID ) - 2 ) = #{plate} "})
    List<Map> trajectoryData(@Param("year") String year,@Param("plate") String plate,@Param("startTime") String startTime, @Param("endTime") String endTime);

    @Select({"SELECT  " +
            "TO_CHAR(gtd.TRANS_TIME,'yyyy-MM-dd HH24:mi:ss') as \"transTime\",  " +
            "TO_CHAR(NVL( gtd.LAST_GANTRY_TIME, gtd.OBU_LAST_GANTRY_TIME ),'yyyy-MM-dd HH24:mi:ss') as \"lastGantryTime\",  " +
            "SUBSTR( gtd.VEHICLE_PLATE, 0, LENGTH( gtd.VEHICLE_PLATE ) - 2 ) AS \"vehiclePlate\",  " +
            "SUBSTR( gtd.VEHICLE_PLATE, LENGTH( gtd.VEHICLE_PLATE ), 2 ) AS \"vehicleColor\",  " +
            "gtd.FEE AS \"fee\",  " +
            "gtd.VEHICLE_TYPE AS \"vehicleType\",  " +
            "tgi1.ID as \"gantryId\",  " +
            "tgi1.NAME as \"gantryName\",  " +
            "tgi.DEVICE_ID AS \"lastGantryId\",   " +
            "tgi.DEVICE_NAME AS \"lastGantryName\"   " +
            "FROM  " +
            "TBL_SHAR_GTD_RES_SENDER_${year} gtd  " +
            "LEFT JOIN ( SELECT HEX as DEVICE_HEX,NAME as DEVICE_NAME, MAX( ID ) AS DEVICE_ID FROM TBL_ROAD_NODES_INFO GROUP BY HEX,NAME ) tgi ON tgi.DEVICE_HEX = NVL( gtd.LAST_GANTRY_HEX, gtd.OBU_LAST_GANTRY_HEX )   " +
            "LEFT JOIN TBL_ROAD_NODES_INFO tgi1 ON tgi1.ID = gtd.GANTRY_ID  " +
            "WHERE  " +
            "gtd.TRANS_TIME >= TO_DATE( #{startTime}, 'yyyy-MM-dd HH24:mi:ss' )   " +
            "AND gtd.TRANS_TIME <= TO_DATE( #{endTime}, 'yyyy-MM-dd HH24:mi:ss' )  " +
            "AND gtd.GANTRY_ID = #{gantryId} " +
            "union " +
            "SELECT  " +
            "TO_CHAR(gtd.EN_TIME,'yyyy-MM-dd HH24:mi:ss') as \"transTime\",  " +
            "NULL as \"lastGantryTime\",  " +
            "SUBSTR( gtd.VEHICLE_ID, 0, LENGTH( gtd.VEHICLE_ID ) - 2 ) AS \"vehiclePlate\",  " +
            "SUBSTR( gtd.VEHICLE_ID, LENGTH( gtd.VEHICLE_ID ), 2 ) AS \"vehicleColor\",  " +
            "gtd.TRANS_FEE AS \"fee\",  " +
            "gtd.VEHICLE_TYPE AS \"vehicleType\",  " +
            "tgi.ID as \"gantryId\",  " +
            "tgi.NAME as \"gantryName\",  " +
            "NULL AS \"lastGantryId\",   " +
            "NULL AS \"lastGantryName\"   " +
            "FROM  " +
            "TBL_SHAR_ENPD_RES_SENDER_${year} gtd  " +
            "LEFT JOIN TBL_LANE_INFO tli ON tli.LANE_HEX = gtd.EN_TOLL_LANE_HEX " +
            "LEFT JOIN TBL_BASE_STATION_INFO tbsi ON tbsi.NET_WORK = '3101' AND tbsi.STATION_ID = tli.STATION_ID " +
            "LEFT JOIN TBL_ROAD_NODES_INFO tgi ON tgi.HEX = tbsi.STATION_HEX  " +
            "WHERE  " +
            "gtd.EN_TIME >= TO_DATE( #{startTime}, 'yyyy-MM-dd HH24:mi:ss' )   " +
            "AND gtd.EN_TIME <= TO_DATE( #{endTime}, 'yyyy-MM-dd HH24:mi:ss' )  " +
            "AND tgi.ID = #{gantryId} " +
            "union " +
            "SELECT  " +
            "TO_CHAR(gtd.EX_TIME,'yyyy-MM-dd HH24:mi:ss') as \"transTime\",  " +
            "NULL as \"lastGantryTime\",  " +
            "SUBSTR( gtd.VEHICLE_ID, 0, LENGTH( gtd.VEHICLE_ID ) - 2 ) AS \"vehiclePlate\",  " +
            "SUBSTR( gtd.VEHICLE_ID, LENGTH( gtd.VEHICLE_ID ), 2 ) AS \"vehicleColor\",  " +
            "gtd.FEE AS \"fee\",  " +
            "gtd.VEHICLE_TYPE AS \"vehicleType\",  " +
            "tgi.ID as \"gantryId\",  " +
            "tgi.NAME as \"gantryName\",  " +
            "NULL AS \"lastGantryId\",   " +
            "NULL AS \"lastGantryName\"   " +
            "FROM  " +
            "TBL_SHAR_ETCTD_RES_SENDER_${year} gtd  " +
            "LEFT JOIN TBL_ROAD_NODES_INFO tgi ON tgi.NAME = gtd.EX_TOLL_STATION_NAME  " +
            "WHERE  " +
            "gtd.EX_TIME >= TO_DATE( #{startTime}, 'yyyy-MM-dd HH24:mi:ss' )   " +
            "AND gtd.EX_TIME <= TO_DATE( #{endTime}, 'yyyy-MM-dd HH24:mi:ss' )  " +
            "AND tgi.ID = #{gantryId} " +
            "union " +
            "SELECT  " +
            "TO_CHAR(gtd.EX_TIME,'yyyy-MM-dd HH24:mi:ss') as \"transTime\",  " +
            "NULL as \"lastGantryTime\",  " +
            "SUBSTR( gtd.VEHICLE_ID, 0, LENGTH( gtd.VEHICLE_ID ) - 2 ) AS \"vehiclePlate\",  " +
            "SUBSTR( gtd.VEHICLE_ID, LENGTH( gtd.VEHICLE_ID ), 2 ) AS \"vehicleColor\",  " +
            "gtd.FEE AS \"fee\",  " +
            "gtd.VEHICLE_TYPE AS \"vehicleType\",  " +
            "tgi.ID as \"gantryId\",  " +
            "tgi.NAME as \"gantryName\",  " +
            "NULL AS \"lastGantryId\",   " +
            "NULL AS \"lastGantryName\"   " +
            "FROM  " +
            "TBL_SHAR_OTD_RES_SENDER_${year} gtd  " +
            "LEFT JOIN TBL_ROAD_NODES_INFO tgi ON tgi.NAME = gtd.EX_TOLL_STATION_NAME  " +
            "WHERE  " +
            "gtd.EX_TIME >= TO_DATE( #{startTime}, 'yyyy-MM-dd HH24:mi:ss' )   " +
            "AND gtd.EX_TIME <= TO_DATE( #{endTime}, 'yyyy-MM-dd HH24:mi:ss' )  " +
            "AND tgi.ID = #{gantryId} "})
    List<Map> gantryTransactionLog(@Param("year") String year,@Param("gantryId") String gantryId,@Param("startTime") String startTime, @Param("endTime") String endTime);

    @Select({"SELECT  " +
            " ID AS \"id\",  " +
            " NAME AS \"name\"   " +
            "FROM  " +
            " TBL_ROAD_NODES_INFO"})
    List<Map> gantrys();

}
