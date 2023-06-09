package com.pingok.vocational.mapper.event;

import com.pingok.vocational.domain.event.TblEventRecord;
import com.pingok.vocational.domain.report.vo.EventRecordClassVo;
import com.pingok.vocational.domain.report.vo.EventRecordSiteVo;
import com.pingok.vocational.domain.report.vo.EventRecordTypeVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_EVENT_RECORD 数据层
 *
 * @author xia
 */
public interface TblEventRecordMapper extends CommonRepository<TblEventRecord> {

    @Select({"<script>" +
            "select b.DICT_LABEL as \"eventType\"," +
            "count(1) as \"count\" ," +
            "to_char(#{startTime}, 'yyyy-mm-dd hh24:mi:ss') || ' - ' ||to_char(#{endTime}, 'yyyy-mm-dd hh24:mi:ss') as \"time\" from TBL_EVENT_RECORD a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.EVENT_TYPE and b.DICT_TYPE='event_type' " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and a.EVENT_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and a.EVENT_TIME &lt;= #{endTime} " +
            "</when>"+
            "<when test='eventType != null'> " +
            "and a.EVENT_TYPE= #{eventType} " +
            "</when>"+
            "group by b.DICT_LABEL order by b.DICT_LABEL" +
            "</script>"})
    List<Map> selectEventRecordByType(@Param("eventType") String eventType, @Param("startTime") Date startTime, @Param("endTime")  Date endTime);

    @Select({"<script>" +
            "select b.DICT_LABEL as \"eventType\"," +
            "count(1) as \"count\" ," +
            "to_char(#{startTime}, 'yyyy-mm-dd hh24:mi:ss') || ' - ' ||to_char(#{endTime}, 'yyyy-mm-dd hh24:mi:ss') as \"time\" from TBL_EVENT_RECORD a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.EVENT_TYPE and b.DICT_TYPE='event_type' " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and a.EVENT_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and a.EVENT_TIME &lt;= #{endTime} " +
            "</when>"+
            "<when test='eventType != null'> " +
            "and a.EVENT_TYPE= #{eventType} " +
            "</when>"+
            "group by b.DICT_LABEL order by b.DICT_LABEL" +
            "</script>"})
    List<EventRecordTypeVo> selectEventRecordByTypeList(ReportVo reportVo);

    @Select({"<script>" +
            "select a.PILE_NO as \"locationInterval\"," +
            "count(1) as \"count\" ," +
            "to_char(#{startTime}, 'yyyy-mm-dd hh24:mi:ss') || ' - ' ||to_char(#{endTime}, 'yyyy-mm-dd hh24:mi:ss') as \"time\" from TBL_EVENT_RECORD a " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and a.EVENT_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and a.EVENT_TIME &lt;= #{endTime} " +
            "</when>"+
            "<when test='locationInterval != null'> " +
            "and a.PILE_NO like CONCAT(CONCAT('%',#{locationInterval}),'%') " +
            "</when>"+
            "group by a.PILE_NO order by a.PILE_NO" +
            "</script>"})
    List<Map> selectEventRecordBySite(@Param("locationInterval") String locationInterval, @Param("startTime") Date startTime, @Param("endTime")  Date endTime);

    @Select({"<script>" +
            "select a.PILE_NO as \"locationInterval\"," +
            "count(1) as \"count\" ," +
            "to_char(#{startTime}, 'yyyy-mm-dd hh24:mi:ss') || ' - ' ||to_char(#{endTime}, 'yyyy-mm-dd hh24:mi:ss') as \"time\" from TBL_EVENT_RECORD a " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and a.EVENT_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and a.EVENT_TIME &lt;= #{endTime} " +
            "</when>"+
            "<when test='locationInterval != null'> " +
            "and a.PILE_NO like CONCAT(CONCAT('%',#{locationInterval}),'%') " +
            "</when>"+
            "group by a.PILE_NO order by a.PILE_NO" +
            "</script>"})
    List<EventRecordSiteVo> selectEventRecordBySiteList(ReportVo reportVo);

    @Select({"<script>" +
            "select b.DICT_LABEL as \"vehClass\"," +
            "count(1) as \"count\" ," +
            "to_char(#{startTime}, 'yyyy-mm-dd hh24:mi:ss') || ' - ' ||to_char(#{endTime}, 'yyyy-mm-dd hh24:mi:ss') as \"time\" from TBL_EVENT_RECORD a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=to_char(a.VEH_CLASS) and b.DICT_TYPE='veh_class' " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and a.EVENT_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and a.EVENT_TIME &lt;= #{endTime} " +
            "</when>"+
            "<when test='vehClass != null'> " +
            "and a.VEH_CLASS= #{vehClass} " +
            "</when>"+
            "group by b.DICT_LABEL order by b.DICT_LABEL" +
            "</script>"})
    List<Map> selectEventRecordByClass(@Param("vehClass") Integer vehClass, @Param("startTime") Date startTime, @Param("endTime")  Date endTime);

    @Select({"<script>" +
            "select b.DICT_LABEL as \"vehClass\"," +
            "count(1) as \"count\" ," +
            "to_char(#{startTime}, 'yyyy-mm-dd hh24:mi:ss') || ' - ' ||to_char(#{endTime}, 'yyyy-mm-dd hh24:mi:ss') as \"time\" from TBL_EVENT_RECORD a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=to_char(a.VEH_CLASS) and b.DICT_TYPE='veh_class' " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and a.EVENT_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and a.EVENT_TIME &lt;= #{endTime} " +
            "</when>"+
            "<when test='vehClass != null'> " +
            "and a.VEH_CLASS= #{vehClass} " +
            "</when>"+
            "group by b.DICT_LABEL order by b.DICT_LABEL" +
            "</script>"})
    List<EventRecordClassVo> selectEventRecordByClassList(ReportVo reportVo);

    @Select({"<script>" +
            "SELECT " +
            "\"count\"," +
            "CASE when STATUS =0 then '未处置' when STATUS =1 then '处置中' " +
            "when STATUS =2 then '已解除' when STATUS =-1 then '误报' END as \"status\", " +
            "to_char(#{startTime}, 'yyyy-mm-dd hh24:mi:ss') || ' - ' ||to_char(#{endTime}, 'yyyy-mm-dd hh24:mi:ss') as \"time\" "+
            "FROM ( " +
            "SELECT COUNT(*) as \"count\",STATUS FROM TBL_EVENT_RECORD " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and CREATE_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and CREATE_TIME &lt;= #{endTime} " +
            "</when>"+
            "<when test='status != null'> " +
            "and STATUS= #{status} " +
            "</when>"+
            "GROUP BY STATUS) " +
            "</script>"})
    List<Map> selectEventRecordByStatusType(@Param("status") Integer status, @Param("startTime") Date startTime, @Param("endTime")  Date endTime);
}
