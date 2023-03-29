package com.pingok.datacenter.mapper.gantry;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_GANTRY_RECORD 数据层
 *
 * @author xia
 */
@Mapper
public interface TblGantryAlgorithmMapper {

    @Select({"<script>" +
            "select " +
            "a.DEVICE_ID as \"gantryId\" ," +
            "CASE WHEN b.VEHICLE_TYPE in(1,2,3,4) THEN 0 WHEN b.VEHICLE_TYPE in(11,12,13,14,15,16) THEN 1 END as \"vehicleStyle\" ," +
            "b.VEHICLE_TYPE as \"vehicleType\" ," +
            "CASE substr(b.VEHICLE_PLATE,instr(b.VEHICLE_PLATE,'_',-1)+1) WHEN '1' THEN 1 WHEN '4' THEN 4 END as \"vehicleColor\" ," +
            "substr(b.VEHICLE_PLATE,1,instr(b.VEHICLE_PLATE,'_',-1)-1) as \"licensePlate\" ," +
            "to_char(b.TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"passTime\" ," +
            "nvl(c.MILEAGE,0) as \"nextGantryDistance\"  from TBL_GANTRY_INFO a " +
            "left join TBL_GANTRY_TRANSACTION_${year} b on b.GANTRY_ID=a.DEVICE_ID and b.VEHICLE_TYPE in(1,2,3,4,11,12,13,14,15,16) and substr(b.VEHICLE_PLATE,instr(b.VEHICLE_PLATE,'_',-1)+1) in('1','4')" +
            "left join TBL_GANTRY_CHARGE_INFO c on c.START_PILE_NO=a.PILE_NO and c.DIRECTION=a.DIRECTION and c.CHARGING_UNIT_TYPE=1 " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and b.TRANS_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and b.TRANS_TIME &lt;= to_date(#{endTime},'yyyy-mm-dd hh24:mi:ss') " +
            "</when>"+
            "</script>"})
    List<Map> selectGantryAlgorithm(@Param("year") String year,@Param("startTime") Date startTime, @Param("endTime")  String endTime);


    @Select({"<script>" +
            "select a.DEVICE_ID as \"gantryId\" ," +
            "a.LANE_COUNT as \"laneCount\"," +
            "case a.DIRECTION when 1 then 0 when 2 then 1 end as \"direction\"," +
            "nvl(b.MILEAGE,0) as \"lastGantryDistance\"," +
            "0 as \"type\"," +
            "b.LIMIT_SPEED as \"limitSpeed\" from TBL_GANTRY_INFO a " +
            "left join TBL_GANTRY_CHARGE_INFO b on b.START_PILE_NO=a.PILE_NO and b.DIRECTION=a.DIRECTION and b.CHARGING_UNIT_TYPE=2 " +
            "where 1=1 " +
            "<when test='direction == 0'> " +
            " and a.DIRECTION = 1" +
            "</when>"+
            "<when test='direction == 1'> " +
            " and a.DIRECTION = 2" +
            "</when>"+
            "<when test='direction != null and direction !=0 and direction !=1 '> " +
            " and a.DIRECTION = 3" +
            "</when>" +
            "union all " +
            "select a.STATION_GB as \"gantryId\" ," +
            "a.LANE_COUNT as \"laneCount\"," +
            "case b.DIRECTION when 1 then 0 when 2 then 1 end as \"direction\"," +
            "nvl(b.MILEAGE,0) as \"lastGantryDistance\"," +
            "1 as \"type\"," +
            "b.LIMIT_SPEED as \"limitSpeed\" from TBL_BASE_STATION_INFO a " +
            "inner join TBL_GANTRY_CHARGE_INFO b on b.START_PILE_NO=a.PILE_NO and b.CHARGING_UNIT_TYPE=2 " +
            " where STATION_HEX like '310108%' and STATION_HEX !='31010804' " +
            "<when test='direction == 0'> " +
            " and b.DIRECTION = 1" +
            "</when>"+
            "<when test='direction == 1'> " +
            " and b.DIRECTION = 2" +
            "</when>"+
            "<when test='direction != null and direction !=0 and direction !=1 '> " +
            " and b.DIRECTION = 3" +
            "</when>" +
            "</script>"})
    List<Map> selectGantryAlgorithmList(@Param("direction") Integer direction);

    @Select({"<script>" +
            "select " +
            "GANTRY_ID as \"gantryId\" ," +
            "substr(VEHICLE_PLATE,1,instr(VEHICLE_PLATE,'_',-1)-1) as \"licensePlate\" ," +
            "to_char(TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"passTime\"  from TBL_GANTRY_TRANSACTION_${year} " +
            "where 1=1 " +
            "<when test='gantryId != null'> " +
            " and GANTRY_ID = #{gantryId} " +
            "</when>"+
            "<when test='startTime != null'> " +
            " and TRANS_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and TRANS_TIME &lt;= to_date(#{endTime},'yyyy-mm-dd hh24:mi:ss') " +
            "</when>" +
            "order by TRANS_TIME DESC"+
            "</script>"})
    List<Map> selectGantryAlgorithmPassRecord(@Param("year") String year,@Param("gantryId") String gantryId,@Param("startTime") Date startTime, @Param("endTime")  String endTime);
}
