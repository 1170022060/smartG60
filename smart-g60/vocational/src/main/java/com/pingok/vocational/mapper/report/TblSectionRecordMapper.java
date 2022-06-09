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
            "select to_char(a.WORK_DATE, 'yyyy-mm-dd hh24:mi:ss') as \"workDate\" ," +
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
            "select to_char(a.WORK_DATE, 'yyyy-mm-dd hh24:mi:ss') as \"workDate\" ," +
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

}
