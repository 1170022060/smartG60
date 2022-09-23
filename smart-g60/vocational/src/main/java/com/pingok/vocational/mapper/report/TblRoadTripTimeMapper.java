package com.pingok.vocational.mapper.report;

import com.pingok.vocational.domain.report.TblRoadTripTime;
import com.pingok.vocational.domain.report.vo.IntransitRecordVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.domain.report.vo.RoadTripTimeVo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_ROAD_TRIP_TIME 数据层
 *
 * @author xia
 */
public interface TblRoadTripTimeMapper extends CommonRepository<TblRoadTripTime> {

    @Select({"<script>" +
            "select to_char(a.ACTUAL_DATE, 'yyyy-mm-dd') as \"actualDate\" ," +
            "b.STATION_NAME as \"enStationId\" ," +
            "c.STATION_NAME as \"exStationId\" ," +
            "a.HOUR as \"hour\" ," +
            "a.TRIP_TIME as \"tripTime\"  from TBL_ROAD_TRIP_TIME a " +
            "left join TBL_BASE_STATION_INFO b on UPPER(b.STATION_HEX)=UPPER(CONCAT('3101',a.EN_STATION_ID)) " +
            "left join TBL_BASE_STATION_INFO c on UPPER(c.STATION_HEX)=UPPER(CONCAT('3101',a.EX_STATION_ID)) " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and a.ACTUAL_DATE &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and a.ACTUAL_DATE &lt;= #{endDate} " +
            "</when>"+
            "<when test='hour != null'> " +
            "and a.HOUR= #{hour} " +
            "</when>" +
            "order by a.ACTUAL_DATE" +
            "</script>"})
    List<Map> selectRoadTripTime(@Param("hour") Integer hour,@Param("startDate")  Date startDate,@Param("endDate")  Date endDate);

    @Select({"<script>" +
            "select to_char(a.ACTUAL_DATE, 'yyyy-mm-dd') as \"actualDate\" ," +
            "b.STATION_NAME as \"enStationId\" ," +
            "c.STATION_NAME as \"exStationId\" ," +
            "a.HOUR as \"hour\" ," +
            "a.TRIP_TIME as \"tripTime\"  from TBL_ROAD_TRIP_TIME a " +
            "left join TBL_BASE_STATION_INFO b on UPPER(b.STATION_HEX)=UPPER(CONCAT('3101',a.EN_STATION_ID)) " +
            "left join TBL_BASE_STATION_INFO c on UPPER(c.STATION_HEX)=UPPER(CONCAT('3101',a.EX_STATION_ID)) " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and a.ACTUAL_DATE &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and a.ACTUAL_DATE &lt;= #{endDate} " +
            "</when>"+
            "<when test='hour != null'> " +
            "and a.HOUR= #{hour} " +
            "</when>" +
            "order by a.ACTUAL_DATE" +
            "</script>"})
    List<RoadTripTimeVo> selectRoadTripTimeList(ReportVo reportVo);

    @Select("SELECT null as \"id\"," +
            "to_char(ACTUAL_DATE, 'yyyy-mm-dd') as \"actualDate\"," +
            "EN_STATION as \"enStationId\"," +
            "EX_STATION as \"exStationId\"," +
            "Hour as \"hour\"," +
            "round(AVG(Minite)) as  \"tripTime\" FROM ( " +
            "SELECT to_date(to_char(TRANS_TIME,'yyyy-mm-dd'),'YYYY-MM-DD HH24:MI:SS') as \"actualDate\"," +
            "EN_STATION as \"enStationId\"," +
            "SUBSTR(LANE_HEX, 5, 4) as \"exStationId\"," +
            "to_char(TRANS_TIME,'hh24') as \"hour\" ," +
            "round(AVG((round(to_number(TRANS_TIME-EN_TIME)*1440)))) as \"tripTime\" FROM TBL_EX_TRANS_CPC " +
            "where EN_NET='3101' and SUBSTR(LANE_HEX, 1, 4)='3101' and TRANS_TIME between to_date(concat(to_char(sysdate,'yyyy-mm-dd ')||(to_char(sysdate,'hh24')-1),':00:00'),'YYYY-MM-DD HH24:MI:SS') and to_date(concat(to_char(sysdate,'yyyy-mm-dd hh24'),':00:00'),'YYYY-MM-DD HH24:MI:SS') " +
            "group by to_date(to_char(TRANS_TIME,'yyyy-mm-dd'),'YYYY-MM-DD HH24:MI:SS'),EN_STATION,SUBSTR(LANE_HEX, 5, 4) ,to_char(TRANS_TIME,'hh24') " +
            "order by to_date(to_char(TRANS_TIME,'yyyy-mm-dd'),'YYYY-MM-DD HH24:MI:SS'),EN_STATION,SUBSTR(LANE_HEX, 5, 4)")
    List<TblRoadTripTime> selectTripTimeHour();
}
