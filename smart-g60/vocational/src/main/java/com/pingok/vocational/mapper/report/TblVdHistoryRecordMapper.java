package com.pingok.vocational.mapper.report;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface TblVdHistoryRecordMapper {
    @Select({"<script>" +
            "SELECT  " +
            "vd.ID as \"id\", " +
            "a.DEVICE_NAME as \"deviceName\", " +
            "a.PILE_NO as \"pileNo\", " +
            "<when test='statisticsType == 1'> " +
            "to_char(vd.COLLECT_TIME, 'yyyy-mm-dd hh24')|| '时' as \"time\", " +
            "</when>"+
            "<when test='statisticsType == 2'> " +
            "to_char(vd.COLLECT_TIME, 'yyyy-mm-dd')|| '时' as \"time\", " +
            "</when>"+
            "<when test='statisticsType == 3'> " +
            "substr(to_char(vd.COLLECT_TIME,'yyyy-mm-dd'),1,7) AS \"time\"," +
            "</when>"+
            "<when test='statisticsType == 4'> " +
            "substr(to_char(vd.COLLECT_TIME,'yyyy-mm-dd'),1,4) AS \"time\"," +
            "</when>"+
            "vd.VOLUME as \"totalFlow\" " +
            "FROM TBL_VD_HISTORY_RECORD vd  " +
            "LEFT JOIN TBL_DEVICE_INFO a on a.DEVICE_ID = vd.DEVICE_ID" +
            "where 1=1" +
            "<when test='deviceName != null'> " +
            " and a.DEVICE_NAME like '%' || #{deviceName} || '%' " +
            "</when>"+
            "<when test='startDate != null'> " +
            " and vd.COLLECT_TIME &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and vd.COLLECT_TIME &lt;= #{endDate} " +
            "</when> "+
            " group by a.DEVICE_NAME,a.PILE_NO " +
            "<when test='statisticsType == 1'> " +
            " ,to_char(vd.COLLECT_TIME,'yyyy-mm-dd hh24') || '时' " +
            "</when>"+
            "<when test='statisticsType == 2'> " +
            " ,to_char(vd.COLLECT_TIME,'yyyy-mm-dd') " +
            "</when>"+
            "<when test='statisticsType == 3'> " +
            " ,substr(to_char(vd.COLLECT_TIME,'yyyy-mm-dd'),1,7) " +
            "</when>"+
            "<when test='statisticsType == 4'> " +
            " ,substr(to_char(vd.COLLECT_TIME,'yyyy-mm-dd'),1,4) " +
            "</when>" +
            "</script>"})
    List<Map> selectVdHistory(@Param("deviceName") String deviceName,
                              @Param("statisticsType") Integer statisticsType,
                              @Param("startDate")Date startDate,@Param("endDate") Date endDate);
}
