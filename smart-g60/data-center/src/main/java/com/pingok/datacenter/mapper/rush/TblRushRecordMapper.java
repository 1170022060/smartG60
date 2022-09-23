package com.pingok.datacenter.mapper.rush;


import com.pingok.datacenter.domain.rush.TblRushRecord;
import com.ruoyi.common.core.mapper.CommonRepository;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * TBL_BASE_STATION_INFO 数据层
 *
 * @author xia
 */
public interface TblRushRecordMapper extends CommonRepository<TblRushRecord> {


    @Select({"SELECT " +
            "a.*  " +
            "FROM " +
            "( " +
            "SELECT " +
            "row_number ( ) over ( PARTITION BY exlpr.VEH_PLATE, exlpr.VEH_COLOR, exlpr.LANE_HEX ORDER BY exlpr.TRANS_TIME DESC ) AS \"rowNumber\", " +
            "trim(exlpr.VEH_PLATE) AS \"vehPlate\", " +
            "sdd.DICT_LABEL AS \"vehColorDesc\", " +
            "exlpr.TRANS_TIME AS \"transTime\", " +
            "tli.MARK_NAME AS \"markName\", " +
            "tli.LANE_NAME AS \"laneName\", " +
            "tli.LANE_HEX AS \"laneHex\", " +
            "tbsi.STATION_NAME AS \"stationName\", " +
            "tbsi.STATION_HEX AS \"stationHex\"  " +
            "FROM " +
            "TBL_EX_LPR_TRANS_${year} exlpr " +
            "LEFT JOIN SYS_DICT_DATA sdd on sdd.DICT_TYPE = 'veh_color' and sdd.DICT_VALUE = exlpr.VEH_COLOR " +
            "LEFT JOIN TBL_EX_TRANS_${year} ex ON ex.VEH_PLATE = exlpr.VEH_PLATE  " +
            "AND ex.VEH_COLOR = exlpr.VEH_COLOR " +
            "JOIN TBL_LANE_INFO tli ON tli.LANE_HEX = exlpr.LANE_HEX " +
            "JOIN TBL_BASE_STATION_INFO tbsi ON tbsi.NET_WORK = tli.NET_WORK  " +
            "AND tbsi.STATION_ID = tli.STATION_ID  " +
            "WHERE " +
            "1 = 1  " +
            "AND exlpr.TRANS_TIME >= to_date( #{startTime}, 'yyyy-mm-dd hh24:mi:ss' )  " +
            "AND exlpr.TRANS_TIME <= to_date( #{endTime}, 'yyyy-mm-dd hh24:mi:ss' )  " +
            "AND EX.VEH_PLATE IS NULL  " +
            "AND trim(exlpr.VEH_PLATE) IN ( " +
            "SELECT " +
            "SUBSTR( VEHICLE_PLATE, 0, LENGTH( VEHICLE_PLATE ) - 2 )  " +
            "FROM " +
            "TBL_GANTRY_TRANSACTION_${year}  " +
            "WHERE " +
            "TRANS_TIME >= to_date( #{twoHours}, 'yyyy-mm-dd hh24:mi:ss' )  " +
            "AND TRANS_TIME <= to_date( #{endTime}, 'yyyy-mm-dd hh24:mi:ss' )  " +
            ")  " +
            ") a  " +
            "WHERE " +
            "a.\"rowNumber\" = 1"})
    List<TblRushRecord> rushRecord(@Param("year") String year, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("twoHours") String twoHours);

    @Select({"<script>" +
            "SELECT " +
            "trr.ID as \"id\", " +
            "trr.VEH_PLATE as \"vehPlate\", " +
            "trr.VEH_COLOR as \"vehColor\", " +
            "trr.TRANS_TIME as \"transTime\", " +
            "trr.LANE_HEX as \"laneHex\", " +
            "trr.LANE_NAME as \"laneName\", " +
            "trr.MARK_NAME as \"markName\", " +
            "trr.STATION_NAME as \"stationName\", " +
            "trr.STATION_HEX as \"stationHex\", " +
            "trr.STATUS as \"status\", " +
            "trr.CONFIRM_TIME as \"confirmTime\", " +
            "trr.RELIABILITY as \"reliability\", " +
            "usr.NICK_NAME as \"confirmUser\" " +
            "FROM " +
            "TBL_RUSH_RECORD trr " +
            "LEFT JOIN SYS_USER usr ON usr.USER_ID = trr.CONFIRM_USER_ID " +
            "where 1=1 " +
            "<when test='stationName != null'> " +
            "and trr.STATION_NAME like '%'||#{stationName}||'%' " +
            "</when>" +
            "<when test='vehPlate != null'> " +
            "and trr.VEH_PLATE like '%'||#{vehPlate}||'%' " +
            "</when>" +
            "<when test='startTime != null'> " +
            "and trr.TRANS_TIME <![CDATA[>=]]> to_date(#{startTime},'yyyy-mm-dd hh24:mi:ss') " +
            "</when>" +
            "<when test='endTime != null'> " +
            "and trr.TRANS_TIME <![CDATA[<=]]> to_date(#{endTime},'yyyy-mm-dd hh24:mi:ss') " +
            "</when>" +
            "</script>"})
    List<TblRushRecord> list(@Param("stationName") String stationName, @Param("vehPlate") String vehPlate, @Param("startTime") String startTime, @Param("endTime") String endTime);
}
