package com.pingok.vocational.mapper.report;

import com.pingok.vocational.domain.report.vo.GantryRecordVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
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
public interface TblGantryRecordMapper {

    @Select({"<script>" +
            "select to_char(a.WORK_DATE, 'yyyy-mm-dd') as \"workDate\" ," +
            "a.GANTRY_ID as \"gantryId\" ," +
            "a.ETC_LOCAL as \"etcLocal\" ," +
            "a.ETC_ELSE as \"etcElse\" ," +
            "a.ETC_LOCAL+a.ETC_ELSE as \"etcTotal\" ," +
            "a.MTC_SINGLE as \"mtcSingle\" ," +
            "a.MTC_TRANS as \"mtcTrans\" ," +
            "a.MTC_SINGLE+a.MTC_TRANS as \"mtcTotal\" ," +
            "a.ETC_LOCAL+a.ETC_ELSE+a.MTC_SINGLE+a.MTC_TRANS as \"totalSum\" ," +
            "a.LICENSE as \"license\" from TBL_GANTRY_RECORD a " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and a.WORK_DATE &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and a.WORK_DATE &lt;= #{endDate} " +
            "</when>"+
            "<when test='gantryId != null'> " +
            "and a.GANTRY_ID like '%' || #{gantryId} || '%' " +
            "</when>"+
            "order by WORK_DATE" +
            "</script>"})
    List<Map> selectGantryRecord(@Param("gantryId") String gantryId,@Param("startDate")  Date startDate,@Param("endDate")  Date endDate);

    @Select({"<script>" +
            "select to_char(a.WORK_DATE, 'yyyy-mm-dd') as \"workDate\" ," +
            "a.GANTRY_ID as \"gantryId\" ," +
            "a.ETC_LOCAL as \"etcLocal\" ," +
            "a.ETC_ELSE as \"etcElse\" ," +
            "a.ETC_LOCAL+a.ETC_ELSE as \"etcTotal\" ," +
            "a.MTC_SINGLE as \"mtcSingle\" ," +
            "a.MTC_TRANS as \"mtcTrans\" ," +
            "a.MTC_SINGLE+a.MTC_TRANS as \"mtcTotal\" ," +
            "a.ETC_LOCAL+a.ETC_ELSE+a.MTC_SINGLE+a.MTC_TRANS as \"totalSum\" ," +
            "a.LICENSE as \"license\" from TBL_GANTRY_RECORD a " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and a.WORK_DATE &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and a.WORK_DATE &lt;= #{endDate} " +
            "</when>"+
            "<when test='gantryId != null'> " +
            "and a.GANTRY_ID like '%' || #{gantryId} || '%' " +
            "</when>"+
            "order by WORK_DATE" +
            "</script>"})
    List<GantryRecordVo> selectGantryRecordList(ReportVo reportVo);

}
