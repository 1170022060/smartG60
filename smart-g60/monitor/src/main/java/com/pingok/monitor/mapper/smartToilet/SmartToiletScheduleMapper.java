package com.pingok.monitor.mapper.smartToilet;

import com.pingok.monitor.domain.smartToilet.TblSmartToiletSchedule;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface SmartToiletScheduleMapper extends CommonRepository<TblSmartToiletSchedule> {
    
    @Select({"<script>" +
            "SELECT sts.ID as \"id\"," +
            "fi.FIELD_NAME as \"fieldName\"," +
            "sti.SER_NAME as \"serName\"," +
            "sts.WORK_DATE as \"workDate\"," +
            "sts.TOI_CHIEF as \"toiChief\"," +
            "sts.WORK_CLEANER_AM as \"workCleanerAm\"," +
            "sts.WORK_CLEANER_PM as \"workCleanerPm\"  " +
            "from TBL_SMART_TOILET_SCHEDULE sts  " +
            "JOIN TBL_FIELD_INFO fi on fi.ID = sts.FIELD_ID  " +
            "JOIN TBL_SMART_TOILET_INFO sti on sti.id = sts.TOILET_ID  " +
            "where 1=1 " +
            "<when test='fieldId != null'>" +
            "and sts.FIELD_ID= #{fieldId}" +
            "</when>"+
            "<when test='toiletId != null'>" +
            "and sts.TOILET_ID= #{toiletId}" +
            "</when>"+
            "<when test='workDate != null'>" +
            "and sts.WORK_DATE= #{workDate}" +
            "</when>"+
            "order by sts.WORK_DATE" +
            "</script>"})
    List<Map> findToiletScheduleList(@Param("fieldId") Long fieldId,@Param("TOILET_ID")Long toiletId, @Param("workDate") Date workDate);

    @Select("SELECT sti.ID as \"id\",sti.SER_NAME as \"toiType\" from TBL_SMART_TOILET_INFO sti " +
            "ORDER BY sti.SER_NAME")
    List<Map> getToiType();

}
