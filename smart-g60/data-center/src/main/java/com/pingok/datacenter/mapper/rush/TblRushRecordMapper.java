package com.pingok.datacenter.mapper.rush;


import com.pingok.datacenter.domain.rush.TblRushRecord;
import com.ruoyi.common.core.mapper.CommonRepository;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Select({"SELECT\n" +
            "ex.PASS_ID AS \"passId\" " +
            "FROM " +
            "TBL_EX_TRANS_${year} ex " +
            " JOIN TBL_EX_LPR_TRANS_${year} exlpr ON ex.VEH_PLATE = exlpr.VEH_PLATE " +
            "AND ex.VEH_COLOR = exlpr.VEH_COLOR " +
            "and ex.TRANS_TIME >= TO_DATE('2022-12-15 15:34:38','yyyy-mm-dd hh24:mi:ss')-(1/24) " +
            "AND ex.TRANS_TIME <= TO_DATE('2022-12-15 15:34:38','yyyy-mm-dd hh24:mi:ss') +(1/24) " +
            "WHERE 1 = 1 " +
            "and trim(exlpr.VEH_PLATE)=#{vehPlate} " +
            "and CONCAT('3101', trim(exlpr.LANE_HEX)) =#{laneHex} " +
            "and rownum=1"})
    String getPassId(@Param("vehPlate") String vehPlate,@Param("laneHex") String laneHex,@Param("transTime") Date transTime,@Param("year") String year);

    @Select({"SELECT " +
            "en.PASS_ID AS \"passId\", " +
            "en.GID AS \"gid\", " +
            "0 AS \"amount\", " +
            "trim(en.VEH_PLATE) AS \"vehPlate\", " +
            "d.DICT_LABEL AS \"vehColor\", " +
            "b.DICT_LABEL AS \"vehClass\", " +
            "c.DICT_LABEL AS \"vehStatus\", " +
            "to_char(en.TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"transTime\"," +
            "e.DICT_LABEL AS \"passType\"," +
            "trim(enlpr.VEH_PLATE) AS \"vehPlateLpr\", " +
            "tli.LANE_HEX as \"laneHex\", " +
            "tli.LANE_NAME as \"laneName\", " +
            "tli.MARK_NAME as \"markName\", " +
            "tbsi.STATION_NAME AS \"stationName\", " +
            "tbsi.STATION_HEX AS \"stationHex\"  " +
            "FROM " +
            "TBL_EN_TRANS_${year} en " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=en.VEH_CLASS and b.DICT_TYPE='veh_class' " +
            "left join SYS_DICT_DATA c on c.DICT_VALUE=en.VEH_STATUS and c.DICT_TYPE='veh_status' " +
            "left join SYS_DICT_DATA d on d.DICT_VALUE=en.VEH_COLOR and d.DICT_TYPE='veh_color' " +
            "left join SYS_DICT_DATA e on e.DICT_VALUE=to_char(en.PASS_TYPE) and e.DICT_TYPE='pass_type' " +
            "LEFT JOIN TBL_EN_LPR_TRANS_${year} enlpr ON trim(en.VEH_PLATE) = enlpr.VEH_PLATE  " +
            "AND en.VEH_COLOR = enlpr.VEH_COLOR " +
            "and enlpr.TRANS_TIME >= en.TRANS_TIME-(1/24) " +
            "AND enlpr.TRANS_TIME <= en.TRANS_TIME +(1/24) " +
            "LEFT JOIN TBL_LANE_INFO tli ON tli.LANE_HEX = CONCAT('3101', enlpr.LANE_HEX) " +
            "LEFT JOIN TBL_BASE_STATION_INFO tbsi ON tbsi.NET_WORK = tli.NET_WORK  " +
            "AND tbsi.STATION_ID = tli.STATION_ID  " +
            "WHERE 1 = 1 " +
            "and PASS_ID=#{passId} "})
    Map entry(@Param("year") String year, @Param("passId") String passId);

    @Select({"SELECT " +
            "ex.PASS_ID AS \"passId\", " +
            "ex.GID AS \"gid\", " +
            "ex.AMOUNT AS \"amount\", " +
            "trim(ex.VEH_PLATE) AS \"vehPlate\", " +
            "d.DICT_LABEL AS \"vehColor\", " +
            "b.DICT_LABEL AS \"vehClass\", " +
            "c.DICT_LABEL AS \"vehStatus\", " +
            "ex.TRANS_TIME as \"transTime\"," +
            "e.DICT_LABEL AS \"passType\"," +
            "trim(exlpr.VEH_PLATE) AS \"vehPlateLpr\", " +
            "tli.LANE_HEX as \"laneHex\", " +
            "tli.LANE_NAME as \"laneName\", " +
            "tli.MARK_NAME as \"markName\", " +
            "tbsi.STATION_NAME AS \"stationName\", " +
            "tbsi.STATION_HEX AS \"stationHex\"  " +
            "FROM " +
            "TBL_EX_TRANS_${year} ex " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=ex.VEH_CLASS and b.DICT_TYPE='veh_class' " +
            "left join SYS_DICT_DATA c on c.DICT_VALUE=ex.VEH_STATUS and c.DICT_TYPE='veh_status' " +
            "left join SYS_DICT_DATA d on d.DICT_VALUE=ex.VEH_COLOR and d.DICT_TYPE='veh_color' " +
            "left join SYS_DICT_DATA e on e.DICT_VALUE=to_char(ex.PASS_TYPE) and e.DICT_TYPE='pass_type' " +
            "LEFT JOIN TBL_EX_LPR_TRANS_${year} exlpr ON ex.VEH_PLATE = exlpr.VEH_PLATE  " +
            "AND ex.VEH_COLOR = exlpr.VEH_COLOR " +
            "and exlpr.TRANS_TIME >= ex.TRANS_TIME-(1/24) " +
            "AND exlpr.TRANS_TIME <= ex.TRANS_TIME +(1/24)" +
            "LEFT JOIN TBL_LANE_INFO tli ON tli.LANE_HEX = CONCAT('3101', trim(exlpr.LANE_HEX)) " +
            "LEFT JOIN TBL_BASE_STATION_INFO tbsi ON tbsi.NET_WORK = tli.NET_WORK  " +
            "AND tbsi.STATION_ID = tli.STATION_ID  " +
            "WHERE 1 = 1 " +
            "and PASS_ID=#{passId} "})
    Map exit(@Param("year") String year, @Param("passId") String passId);

    @Select({"select b.* from( " +
            "select a.* from " +
            "(SELECT " +
            "row_number ( ) over (  ORDER BY ex.TRANS_TIME DESC ) AS \"rowNumber\"," +
            "ex.PASS_ID AS \"passId\", " +
            "ex.GID AS \"gid\", " +
            "ex.AMOUNT AS \"amount\", " +
            "trim(ex.VEH_PLATE) AS \"vehPlate\", " +
            "d.DICT_LABEL AS \"vehColor\", " +
            "b.DICT_LABEL AS \"vehClass\", " +
            "c.DICT_LABEL AS \"vehStatus\", " +
            "to_char(ex.TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"transTime\"," +
            "e.DICT_LABEL AS \"passType\"," +
            "trim(exlpr.VEH_PLATE) AS \"vehPlateLpr\", " +
            "tli.LANE_HEX as \"laneHex\", " +
            "tli.LANE_NAME as \"laneName\", " +
            "tli.MARK_NAME as \"markName\", " +
            "tbsi.STATION_NAME AS \"stationName\", " +
            "tbsi.STATION_HEX AS \"stationHex\"  " +
            "FROM " +
            "TBL_EX_TRANS_${year} ex " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=ex.VEH_CLASS and b.DICT_TYPE='veh_class' " +
            "left join SYS_DICT_DATA c on c.DICT_VALUE=ex.VEH_STATUS and c.DICT_TYPE='veh_status' " +
            "left join SYS_DICT_DATA d on d.DICT_VALUE=ex.VEH_COLOR and d.DICT_TYPE='veh_color' " +
            "left join SYS_DICT_DATA e on e.DICT_VALUE=to_char(ex.PASS_TYPE) and e.DICT_TYPE='pass_type' " +
            "LEFT JOIN TBL_EX_LPR_TRANS_${year} exlpr ON ex.VEH_PLATE = exlpr.VEH_PLATE  " +
            "AND ex.VEH_COLOR = exlpr.VEH_COLOR " +
            "and exlpr.TRANS_TIME >= ex.TRANS_TIME-(1/24) " +
            "AND exlpr.TRANS_TIME <= ex.TRANS_TIME +(1/24)" +
            "LEFT JOIN TBL_LANE_INFO tli ON tli.LANE_HEX = CONCAT('3101', trim(exlpr.LANE_HEX)) " +
            "LEFT JOIN TBL_BASE_STATION_INFO tbsi ON tbsi.NET_WORK = tli.NET_WORK  " +
            "AND tbsi.STATION_ID = tli.STATION_ID  " +
            "WHERE 1 = 1 " +
            "and ex.LANE_HEX=#{laneHex} " +
            "and ex.TRANS_TIME < #{transTime} " +
            ")a " +
            "where a.\"rowNumber\"<=2 " +
            "union all " +
            "SELECT " +
            " a.*  " +
            "FROM " +
            " ( " +
            "SELECT  " +
            "row_number ( ) over ( ORDER BY ex.TRANS_TIME ) AS \"rowNumber\",\n" +
            "ex.PASS_ID AS \"passId\", \n" +
            "ex.GID AS \"gid\", \n" +
            "ex.AMOUNT AS \"amount\", \n" +
            "trim(ex.VEH_PLATE) AS \"vehPlate\", \n" +
            "d.DICT_LABEL AS \"vehColor\", \n" +
            "b.DICT_LABEL AS \"vehClass\", \n" +
            "c.DICT_LABEL AS \"vehStatus\", \n" +
            "to_char( ex.TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss' ) AS \"transTime\",\n" +
            "e.DICT_LABEL AS \"passType\",\n" +
            "trim(exlpr.VEH_PLATE) AS \"vehPlateLpr\", \n" +
            "tli.LANE_HEX as \"laneHex\", \n" +
            "tli.LANE_NAME as \"laneName\", \n" +
            "tli.MARK_NAME as \"markName\", \n" +
            "tbsi.STATION_NAME AS \"stationName\", \n" +
            "tbsi.STATION_HEX AS \"stationHex\"  \n" +
            "FROM \n" +
            "TBL_EX_TRANS_2022 ex \n" +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=ex.VEH_CLASS and b.DICT_TYPE='veh_class' \n" +
            "left join SYS_DICT_DATA c on c.DICT_VALUE=ex.VEH_STATUS and c.DICT_TYPE='veh_status' \n" +
            "left join SYS_DICT_DATA d on d.DICT_VALUE=ex.VEH_COLOR and d.DICT_TYPE='veh_color' \n" +
            "left join SYS_DICT_DATA e on e.DICT_VALUE=to_char(ex.PASS_TYPE) and e.DICT_TYPE='pass_type' \n" +
            "LEFT JOIN TBL_EX_LPR_TRANS_2022 exlpr ON ex.VEH_PLATE = exlpr.VEH_PLATE  \n" +
            "AND ex.VEH_COLOR = exlpr.VEH_COLOR \n" +
            "and exlpr.TRANS_TIME >= ex.TRANS_TIME-(1/24) \n" +
            "AND exlpr.TRANS_TIME <= ex.TRANS_TIME +(1/24)" +
            "LEFT JOIN TBL_LANE_INFO tli ON tli.LANE_HEX = CONCAT('3101', trim(exlpr.LANE_HEX)) \n" +
            "LEFT JOIN TBL_BASE_STATION_INFO tbsi ON tbsi.NET_WORK = tli.NET_WORK  \n" +
            "AND tbsi.STATION_ID = tli.STATION_ID  " +
            "WHERE 1 = 1 \n" +
            "and PASS_ID=#{passId} )a " +
            " union all " +
            "select a.* from " +
            "(SELECT " +
            "row_number ( ) over (  ORDER BY ex.TRANS_TIME ) AS \"rowNumber\"," +
            "ex.PASS_ID AS \"passId\", " +
            "ex.GID AS \"gid\", " +
            "ex.AMOUNT AS \"amount\", " +
            "trim(ex.VEH_PLATE) AS \"vehPlate\", " +
            "d.DICT_LABEL AS \"vehColor\", " +
            "b.DICT_LABEL AS \"vehClass\", " +
            "c.DICT_LABEL AS \"vehStatus\", " +
            "to_char(ex.TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"transTime\"," +
            "e.DICT_LABEL AS \"passType\"," +
            "trim(exlpr.VEH_PLATE) AS \"vehPlateLpr\", " +
            "tli.LANE_HEX as \"laneHex\", " +
            "tli.LANE_NAME as \"laneName\", " +
            "tli.MARK_NAME as \"markName\", " +
            "tbsi.STATION_NAME AS \"stationName\", " +
            "tbsi.STATION_HEX AS \"stationHex\"  " +
            "FROM " +
            "TBL_EX_TRANS_${year} ex " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=ex.VEH_CLASS and b.DICT_TYPE='veh_class' " +
            "left join SYS_DICT_DATA c on c.DICT_VALUE=ex.VEH_STATUS and c.DICT_TYPE='veh_status' " +
            "left join SYS_DICT_DATA d on d.DICT_VALUE=ex.VEH_COLOR and d.DICT_TYPE='veh_color' " +
            "left join SYS_DICT_DATA e on e.DICT_VALUE=to_char(ex.PASS_TYPE) and e.DICT_TYPE='pass_type' " +
            "LEFT JOIN TBL_EX_LPR_TRANS_${year} exlpr ON ex.VEH_PLATE = exlpr.VEH_PLATE  " +
            "AND ex.VEH_COLOR = exlpr.VEH_COLOR " +
            "and exlpr.TRANS_TIME >= ex.TRANS_TIME-(1/24) " +
            "AND exlpr.TRANS_TIME <= ex.TRANS_TIME +(1/24)" +
            "LEFT JOIN TBL_LANE_INFO tli ON tli.LANE_HEX = CONCAT('3101', trim(exlpr.LANE_HEX)) " +
            "LEFT JOIN TBL_BASE_STATION_INFO tbsi ON tbsi.NET_WORK = tli.NET_WORK  " +
            "AND tbsi.STATION_ID = tli.STATION_ID  " +
            "WHERE 1 = 1 " +
            "and ex.LANE_HEX=#{laneHex} " +
            "and ex.TRANS_TIME > #{transTime} " +
            ")a " +
            "where a.\"rowNumber\"<=2 )b " +
            "order by b.\"transTime\""})
    List<Map> exitAll(@Param("year") String year, @Param("transTime") Date transTime,@Param("laneHex") String laneHex,@Param("passId") String passId);
}
