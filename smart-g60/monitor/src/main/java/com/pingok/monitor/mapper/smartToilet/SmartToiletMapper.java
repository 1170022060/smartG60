package com.pingok.monitor.mapper.smartToilet;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *  数据层
 *
 * @author qiumin
 */
@Mapper
public interface SmartToiletMapper {

    @Select("SELECT " +
            "sti.ID AS \"id\", " +
            "sti.SER_NUM AS \"serNum\", " +
            "sti.SER_NAME AS \"serName\", " +
            "sti.SER_TYPE AS \"serType\", " +
            "sti.TOTAL as \"total\", " +
            "sti.SURPLUS as \"surplus\", " +
            "sti.RATE_IN as \"rateIn\", " +
            "sti.RATE_OUT as \"rateOut\", " +
            "sti.STATUS as \"status\", " +
            "sti.STATUS_DESC as \"statusDesc\"  " +
            "FROM " +
            "TBL_SMART_TOILET_INFO sti " +
            "JOIN TBL_FIELD_INFO tfi ON tfi.ID = sti.FIELD_ID " +
            "where sti.FIELD_ID in (3940,3941) ")
    List<Map> findToiletInfoByFieldNum(@Param("fieldNum") String fieldNum);

    @Select("SELECT " +
            "sth.ID AS \"id\", " +
            "sth.NH3 AS \"nh3\", " +
            "sth.H2S AS \"h2s\", " +
            "sth.HUM AS \"hum\", " +
            "sth.TEMP AS \"temp\", " +
            "sth.CO2 AS \"co2\", " +
            "sth.PM25 AS \"pm25\", " +
            "VOC AS \"voc\", " +
            "CASE sth.SMK_ALARM " +
            "WHEN 0 THEN " +
            "'正常' " +
            "ELSE " +
            "'烟雾告警' " +
            "END AS \"smkAlarm\", " +
            "sth.WM AS \"wm\", " +
            "sth.EM AS \"em\", " +
            "sth.PM AS \"pm\", " +
            "sth.EVL AS \"evl\"  " +
            "FROM " +
            "TBL_SMART_TOILET_HEALTH sth " +
            "where sth.SER_ID =  #{serId}")
    List<Map> findToiletHealthBySerId(@Param("serId") Long serId);


    @Select("SELECT " +
            "stc.ID AS \"id\", " +
            "stc.INDEX_ID AS \"index\", " +
            "stc.STATUS AS \"status\", " +
            "stc.ALARM AS \"alarm\",  " +
            "stc.POSITION AS \"position\"  " +
            "FROM " +
            "TBL_SMART_TOILET_CUBICLE stc  " +
            "WHERE " +
            "stc.SER_ID =  #{serId}")
    List<Map> findToiletCubicleBySerId(@Param("serId") Long serId);

    @Select("SELECT sts.ID as \"id\"," +
            "fi.FIELD_NAME as \"fieldName\"," +
            "sti.SER_NAME as \"serName\"," +
            "to_char(sts.WORK_DATE, 'yyyy-mm-dd') as \"workDate\"," +
            "sts.TOI_CHIEF as \"toiChief\"," +
            "sts.WORK_CLEANER_AM as \"workCleanerAm\"," +
            "sts.WORK_CLEANER_PM as \"workCleanerPm\"  " +
            "from TBL_SMART_TOILET_SCHEDULE sts  " +
            "JOIN TBL_FIELD_INFO fi on fi.ID = sts.FIELD_ID  " +
            "JOIN TBL_SMART_TOILET_INFO sti on sti.id = sts.TOILET_ID  " +
            "where " +
            "sts.TOILET_ID= #{toiletId} and sts.WORK_DATE= #{workDate}  ")
    List<Map> getTodaySchedule(@Param("toiletId") Long toiletId,@Param("workDate") Date workDate);
}
