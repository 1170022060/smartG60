package com.pingok.vocational.mapper.report;

import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.domain.report.vo.SpecialRecordVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_SECTION_RECORD 数据层
 *
 * @author xia
 */
public interface TblSpecialRecordMapper {

    @Select({"<script>" +
            "select b.LANE_NAME as \"laneName\" ," +
            "c.DICT_LABEL as \"laneType\" ," +
            "a.MARK_NAME as \"markName\" ," +
            "to_char(a.WORK_DATE, 'yyyy-mm-dd') as \"workDate\" ," +
            "to_char(a.TIME, 'yyyy-mm-dd hh24:mi:ss') as \"time\" ," +
            "a.OPT_ID as \"optId\" ," +
            "a.OPT_NAME as \"optName\" ," +
            "d.DICT_LABEL as \"shift\" ," +
            "e.DICT_LABEL as \"specialLevel\" ," +
            "f.DICT_LABEL as \"type\" ," +
            "a.MESSAGE as \"message\" ," +
            "g.DICT_LABEL as \"status\" ," +
            "a.WIN_ID as \"winId\" ," +
            "h.DICT_LABEL as \"optType\" ," +
            "a.HANDLE_MESSAGE as \"handleMessage\" from TBL_SPECIAL_RECORD a " +
            "left join TBL_LANE_INFO b on UPPER(CONCAT(b.STATION_ID,b.LANE_ID))=UPPER(CONCAT(a.STATION_ID,a.LANE_ID)) " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.LANE_TYPE and c.DICT_TYPE='lane_type' " +
            "left join  SYS_DICT_DATA d on d.DICT_VALUE=a.SHIFT and d.DICT_TYPE='shift' " +
            "left join  SYS_DICT_DATA e on e.DICT_VALUE=a.SPECIAL_LEVEL and e.DICT_TYPE='special_level' " +
            "left join  SYS_DICT_DATA f on f.DICT_VALUE=a.TYPE and f.DICT_TYPE='special_type' " +
            "left join  SYS_DICT_DATA g on g.DICT_VALUE=a.STATUS and g.DICT_TYPE='special_status' " +
            "left join  SYS_DICT_DATA h on h.DICT_VALUE=a.OPT_TYPE and h.DICT_TYPE='special_opt_type' " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and a.TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and a.TIME &lt;= #{endTime} " +
            "</when>"+
            "<when test='stationId != null'> " +
            "and a.STATION_ID= #{stationId} " +
            "</when>"+
            "<when test='laneId != null'> " +
            "and CONCAT(a.STATION_ID,a.LANE_ID)= CONCAT(#{stationId},#{laneId}) " +
            "</when>"+
            "order by CONCAT(a.STATION_ID,a.LANE_ID),a.TIME" +
            "</script>"})
    List<Map> selectSpecialRecord(@Param("startTime") Date startTime, @Param("endTime")  Date endTime, @Param("stationId") String stationId,@Param("laneId") String laneId);

    @Select({"<script>" +
            "select to_char(a.WORK_DATE, 'yyyy-mm-dd') as \"workDate\" ," +
            "b.STATION_NAME as \"stationName\" ," +
            "c.DICT_LABEL as \"type\" ," +
            "count(1) as \"count\"  from TBL_SPECIAL_RECORD a " +
            "left join TBL_BASE_STATION_INFO b on UPPER(CONCAT(b.NET_WORK,b.STATION_ID))=UPPER(CONCAT(a.NET_WORK,a.STATION_ID)) " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.TYPE and c.DICT_TYPE='special_type' " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and a.WORK_DATE &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and a.WORK_DATE &lt;= #{endDate} " +
            "</when>"+
            "group by a.WORK_DATE,b.STATION_NAME,c.DICT_LABEL "+
            "order by a.WORK_DATE,b.STATION_NAME,c.DICT_LABEL" +
            "</script>"})
    List<Map> selectSpecialStatistic(@Param("startDate") Date startDate, @Param("endDate")  Date endDate);

    @Select({"<script>" +
            "select to_char(a.WORK_DATE, 'yyyy-mm-dd') as \"workDate\" ," +
            "b.STATION_NAME as \"stationName\" ," +
            "c.DICT_LABEL as \"type\" ," +
            "count(1) as \"count\"  from TBL_SPECIAL_RECORD a " +
            "left join TBL_BASE_STATION_INFO b on UPPER(CONCAT(b.NET_WORK,b.STATION_ID))=UPPER(CONCAT(a.NET_WORK,a.STATION_ID)) " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.TYPE and c.DICT_TYPE='special_type' " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and a.WORK_DATE &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and a.WORK_DATE &lt;= #{endDate} " +
            "</when>"+
            "group by a.WORK_DATE,b.STATION_NAME,c.DICT_LABEL "+
            "order by a.WORK_DATE,b.STATION_NAME,c.DICT_LABEL" +
            "</script>"})
    List<SpecialRecordVo> selectSpecialStatisticList(ReportVo reportVo);

}
