package com.pingok.vocational.mapper.report;

import com.pingok.vocational.domain.report.vo.IntransitRecordVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_INTRANSIT_RECORD 数据层
 *
 * @author xia
 */
public interface TblIntransitRecordMapper{

    @Select({"<script>" +
            "select to_char(a.WORK_DATE, 'yyyy-mm-dd') as \"workDate\" ," +
            "a.ETC_LOCAL as \"etcLocal\" ," +
            "a.ETC_ELSE as \"etcElse\" ," +
            "a.ETC_LOCAL+a.ETC_ELSE as \"etcTotal\" ," +
            "a.MTC_SINGLE as \"mtcSingle\" ," +
            "a.MTC_TRANS as \"mtcTrans\" ," +
            "a.MTC_SINGLE+a.MTC_TRANS as \"mtcTotal\" ," +
            "a.ETC_LOCAL+a.ETC_ELSE+a.MTC_SINGLE+a.MTC_TRANS as \"totalSum\" ," +
            "a.LICENSE as \"license\" from TBL_INTRANSIT_RECORD a " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and a.WORK_DATE &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and a.WORK_DATE &lt;= #{endDate} " +
            "</when>"+
            "order by WORK_DATE" +
            "</script>"})
    List<Map> selectIntransitRecord(@Param("startDate") Date startDate, @Param("endDate")  Date endDate);

    @Select({"<script>" +
            "select to_char(a.WORK_DATE, 'yyyy-mm-dd') as \"workDate\" ," +
            "a.ETC_LOCAL as \"etcLocal\" ," +
            "a.ETC_ELSE as \"etcElse\" ," +
            "a.ETC_LOCAL+a.ETC_ELSE as \"etcTotal\" ," +
            "a.MTC_SINGLE as \"mtcSingle\" ," +
            "a.MTC_TRANS as \"mtcTrans\" ," +
            "a.MTC_SINGLE+a.MTC_TRANS as \"mtcTotal\" ," +
            "a.ETC_LOCAL+a.ETC_ELSE+a.MTC_SINGLE+a.MTC_TRANS as \"totalSum\" ," +
            "a.LICENSE as \"license\" from TBL_INTRANSIT_RECORD a " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and a.WORK_DATE &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and a.WORK_DATE &lt;= #{endDate} " +
            "</when>"+
            "order by WORK_DATE" +
            "</script>"})
    List<IntransitRecordVo> selectIntransitRecordList(@Param("startDate") Date startDate, @Param("endDate")  Date endDate);

}
