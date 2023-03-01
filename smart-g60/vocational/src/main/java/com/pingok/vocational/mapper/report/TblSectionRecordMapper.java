package com.pingok.vocational.mapper.report;

import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.domain.report.vo.SectionRecordVo;
import com.ruoyi.common.core.mapper.CommonRepository;
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
public interface TblSectionRecordMapper {

    @Select({"<script>" +
            "select to_char(a.WORK_DATE, 'yyyy-mm-dd') as \"workDate\" ," +
            "b.STATION_NAME as \"stationId\"," +
            "c.DICT_LABEL as \"direction\"," +
            "a.ETC_LOCAL as \"etcLocal\" ," +
            "a.ETC_ELSE as \"etcElse\" ," +
            "a.ETC_LOCAL+a.ETC_ELSE as \"etcTotal\" ," +
            "a.MTC_SINGLE as \"mtcSingle\" ," +
            "a.MTC_TRANS as \"mtcTrans\" ," +
            "a.MTC_SINGLE+a.MTC_TRANS as \"mtcTotal\" ," +
            "a.ETC_LOCAL+a.ETC_ELSE+a.MTC_SINGLE+a.MTC_TRANS as \"totalSum\" ," +
            "a.LICENSE as \"license\" from TBL_SECTION_RECORD a " +
            "left join TBL_BASE_STATION_INFO b on UPPER(b.STATION_HEX)=UPPER(CONCAT('3101',a.STATION_ID)) " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=to_char(a.DIRECTION) and c.DICT_TYPE='section_direction' " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and a.WORK_DATE &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and a.WORK_DATE &lt;= #{endDate} " +
            "</when>"+
            "<when test='stationId != null'> " +
            "and a.STATION_ID= #{stationId} " +
            "</when>"+
            "<when test='direction != null'> " +
            "and a.DIRECTION= #{direction} " +
            "</when>"+
            "order by WORK_DATE" +
            "</script>"})
    List<Map> selectSectionRecord(@Param("stationId") String stationId, @Param("startDate") Date startDate, @Param("endDate")  Date endDate,@Param("direction") Integer direction);

    @Select({"<script>" +
            "select to_char(a.WORK_DATE, 'yyyy-mm-dd') as \"workDate\" ," +
            "b.STATION_NAME as \"stationId\"," +
            "c.DICT_LABEL as \"direction\"," +
            "a.ETC_LOCAL as \"etcLocal\" ," +
            "a.ETC_ELSE as \"etcElse\" ," +
            "a.ETC_LOCAL+a.ETC_ELSE as \"etcTotal\" ," +
            "a.MTC_SINGLE as \"mtcSingle\" ," +
            "a.MTC_TRANS as \"mtcTrans\" ," +
            "a.MTC_SINGLE+a.MTC_TRANS as \"mtcTotal\" ," +
            "a.ETC_LOCAL+a.ETC_ELSE+a.MTC_SINGLE+a.MTC_TRANS as \"totalSum\" ," +
            "a.LICENSE as \"license\" from TBL_SECTION_RECORD a " +
            "left join TBL_BASE_STATION_INFO b on UPPER(b.STATION_HEX)=UPPER(CONCAT('3101',a.STATION_ID)) " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=to_char(a.DIRECTION) and c.DICT_TYPE='section_direction' " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and a.WORK_DATE &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and a.WORK_DATE &lt;= #{endDate} " +
            "</when>"+
            "<when test='stationId != null'> " +
            "and a.STATION_ID= #{stationId} " +
            "</when>"+
            "<when test='direction != null'> " +
            "and a.DIRECTION= #{direction} " +
            "</when>"+
            "order by WORK_DATE" +
            "</script>"})
    List<SectionRecordVo> selectSectionRecordList(ReportVo reportVo);


    @Select({"<script>" +
            "SELECT  " +
            "t.\"stationId\", " +
            "t.\"workDate\", " +
            "case when t.\"direction\" = 1 then '入口' when t.\"direction\" = 2 then '出口' end as \"direction\", " +
            "t.\"etcLocal\", " +
            "t.\"etcElse\", " +
            "t.\"etcTotal\", " +
            "t.\"mtcSingle\", " +
            "t.\"mtcTrans\", " +
            "t.\"mtcTotal\", " +
            "t.\"etcTotal\"+t.\"mtcTotal\" as \"totalSum\", " +
            "NVL(lpr.\"license\",0) as \"license\" " +
            "FROM ( " +
            "SELECT  " +
            "b.STATION_NAME as \"stationId\", " +
            "to_char(en.EN_TIME,'yyyy-mm-dd') as \"workDate\", " +
            "1 as \"direction\", " +
            "COUNT(case when en.MEDIA_TYPE = 1 AND en.EN_TOLL_LANE_HEX LIKE '310108%' then 1 else null end) as \"etcLocal\", " +
            "COUNT(case when en.MEDIA_TYPE = 1 AND en.EN_TOLL_LANE_HEX not LIKE '310108%' then 1 else null end) as \"etcElse\", " +
            "COUNT(case when en.MEDIA_TYPE = 1 AND en.EN_TOLL_LANE_HEX LIKE '310108%' then 1 else null end) +  " +
            "COUNT(case when en.MEDIA_TYPE = 1 AND en.EN_TOLL_LANE_HEX not LIKE '310108%' then 1 else null end) as \"etcTotal\", " +
            "COUNT(case when en.MEDIA_TYPE = 2 AND en.EN_TOLL_LANE_HEX LIKE '310108%' then 1 else null end) as \"mtcSingle\", " +
            "0 as \"mtcTrans\", " +
            "COUNT(case when en.MEDIA_TYPE = 2 AND en.EN_TOLL_LANE_HEX LIKE '310108%' then 1 else null end) as \"mtcTotal\" " +
            "FROM TBL_SHAR_ENPD_RES_SENDER_${year} en  " +
            "LEFT JOIN TBL_LANE_INFO a on a.LANE_HEX = en.EN_TOLL_LANE_HEX " +
            "LEFT JOIN TBL_BASE_STATION_INFO b on UPPER(b.STATION_HEX)=UPPER(CONCAT('3101',a.STATION_ID)) " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and en.EN_TIME &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and en.EN_TIME &lt;= #{endDate} " +
            "</when>"+
            "<when test='stationId != null'> " +
            "and UPPER(b.STATION_HEX)=  UPPER(CONCAT('3101',#{stationId})) " +
            "</when>"+
            "GROUP BY b.STATION_NAME,to_char(en.EN_TIME,'yyyy-mm-dd') " +
            "UNION ALL " +
            "SELECT  " +
            "ex.EX_TOLL_STATION_NAME as \"stationId\", " +
            "to_char(ex.EX_TIME,'yyyy-mm-dd') as \"workDate\", " +
            "2 as \"direction\", " +
            "COUNT(case when ex.MEDIA_TYPE = 1 AND ex.EN_TOLL_LANE_HEX LIKE '310108%' then 1 else null end) as \"etcLocal\", " +
            "COUNT(case when ex.MEDIA_TYPE = 1 AND ex.EN_TOLL_LANE_HEX NOT LIKE '310108%' then 1 else null end) as \"etcElse\", " +
            "COUNT(case when ex.MEDIA_TYPE = 1 AND ex.EN_TOLL_LANE_HEX LIKE '310108%' then 1 else null end) + " +
            "COUNT(case when ex.MEDIA_TYPE = 1 AND ex.EN_TOLL_LANE_HEX NOT LIKE '310108%' then 1 else null end)as \"etcTotal\", " +
            "COUNT(case when ex.MEDIA_TYPE = 2 AND ex.PROVINCE_COUNT = 1 then 1 else null end) as \"mtcSingle\", " +
            "COUNT(case when ex.MEDIA_TYPE = 2 AND ex.PROVINCE_COUNT > 1 then 1 else null end) as \"mtcTrans\", " +
            "COUNT(case when ex.MEDIA_TYPE = 2 AND ex.PROVINCE_COUNT = 1 then 1 else null end) +  " +
            "COUNT(case when ex.MEDIA_TYPE = 2 AND ex.PROVINCE_COUNT > 1 then 1 else null end) as \"mtcTotal\" " +
            "FROM ( " +
            "SELECT * FROM TBL_SHAR_ETCTD_RES_SENDER_${year} " +
            "UNION ALL  " +
            "SELECT * FROM TBL_SHAR_OTD_RES_SENDER_${year} " +
            ") ex " +
            "LEFT JOIN TBL_BASE_STATION_INFO s on s.STATION_NAME = ex.EX_TOLL_STATION_NAME " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and ex.EX_TIME &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and ex.EX_TIME &lt;= #{endDate} " +
            "</when>"+
            "<when test='stationId != null'> " +
            "and UPPER(s.STATION_HEX)=  UPPER(CONCAT('3101',#{stationId})) " +
            "</when>"+
            "GROUP BY ex.EX_TOLL_STATION_NAME,to_char(ex.EX_TIME,'yyyy-mm-dd'))t " +
            "LEFT JOIN  " +
            "( " +
            "SELECT  " +
            "\"opTime\", " +
            "\"stationName\", " +
            "SUM(\"license\") as \"license\", " +
            "\"mediaType\" " +
            "FROM ( " +
            "SELECT to_char(OP_TIME,'yyyy-mm-dd') as \"opTime\", " +
            "case when aa.LANE_TYPE= 1 OR aa.LANE_TYPE= 3 then 1 when aa.LANE_TYPE= 2 OR aa.LANE_TYPE= 4 then 2 end as \"mediaType\"" +
            ",bb.STATION_NAME as \"stationName\", " +
            "COUNT(*) as \"license\" FROM TBL_SHAR_SVID_RES_SENDER_${year} l " +
            "LEFT JOIN TBL_LANE_INFO aa on aa.LANE_GB = l.LANE_NUM " +
            "LEFT JOIN TBL_BASE_STATION_INFO bb on UPPER(bb.STATION_HEX)=UPPER(CONCAT('3101',aa.STATION_ID)) " +
            "GROUP BY to_char(OP_TIME,'yyyy-mm-dd'),aa.LANE_TYPE,bb.STATION_NAME) " +
            "GROUP BY \"opTime\",\"stationName\",\"mediaType\" " +
            "ORDER BY \"opTime\" " +
            ")lpr on lpr.\"opTime\" = t.\"workDate\" and lpr.\"mediaType\" = t.\"direction\" AND lpr.\"stationName\" = t.\"stationId\" " +
            "where 1=1 " +
            "<when test='direction != null'> " +
            "and t.\"direction\"= #{direction} " +
            "</when> " +
            "ORDER BY t.\"stationId\" DESC,t.\"workDate\" DESC "+
            "</script>"})
    List<Map> selectEnAndExFlow(@Param("year") String year,@Param("stationId") String stationId, @Param("startDate") Date startDate,
                                @Param("endDate")  Date endDate,@Param("direction") Integer direction);
}
