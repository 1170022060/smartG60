package com.pingok.vocational.mapper.report;


import com.pingok.vocational.domain.report.vo.OdRecordStaVo;
import com.pingok.vocational.domain.report.vo.OdRecordVelVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_OD_RECORD 数据层
 *
 * @author xia
 */
public interface TblOdRecordMapper {

    @Select({"<script>" +
            "select  " +
            "to_char(a.WORK_DATE, 'yyyy-mm-dd') as \"workDate\" ," +
            "a.HOUR as \"hour\"," +
            "sdd.DICT_LABEL AS \"vehClass\", " +
            "b.STATION_NAME as \"stationId\"," +
            "sum(a.FLOW) as \"flow\" from TBL_OD_RECORD a " +
            "left join TBL_BASE_STATION_INFO b on UPPER(b.STATION_HEX)=UPPER(CONCAT('3101',a.STATION_ID)) " +
            "JOIN  SYS_DICT_DATA sdd ON sdd.DICT_VALUE = a.VEH_CLASS  " +
            "AND sdd.DICT_TYPE = 'veh_class' " +
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
            "<when test='hour != null'> " +
            "and a.HOUR= #{hour} " +
            "</when>"+
            "group by a.WORK_DATE,a.HOUR,b.STATION_NAME,a.VEH_CLASS order by a.WORK_DATE,b.STATION_NAME,a.HOUR,a.VEH_CLASS " +
            "</script>"})
    List<Map> selectOdRecordBySta(@Param("stationId") String stationId, @Param("startDate") Date startDate, @Param("endDate")  Date endDate, @Param("hour")  Integer hour);

    @Select({"<script>" +
            "select  " +
            "to_char(a.WORK_DATE, 'yyyy-mm-dd') as \"workDate\" ," +
            "a.HOUR as \"hour\"," +
            "b.STATION_NAME as \"stationId\"," +
            "sum(a.FLOW) as \"flow\" from TBL_OD_RECORD a " +
            "left join TBL_BASE_STATION_INFO b on UPPER(b.STATION_HEX)=UPPER(CONCAT('3101',a.STATION_ID)) " +
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
            "<when test='hour != null'> " +
            "and a.HOUR= #{hour} " +
            "</when>"+
            "group by a.WORK_DATE,a.HOUR,b.STATION_NAME order by a.WORK_DATE,b.STATION_NAME,a.HOUR" +
            "</script>"})
    List<OdRecordStaVo> selectOdRecordByStaList(ReportVo reportVo);


    @Select({"<script>" +
            "select  " +
            "to_char(a.WORK_DATE, 'yyyy-mm-dd') as \"workDate\" ," +
            "a.HOUR as \"hour\"," +
            "c.DICT_LABEL as \"vehClass\"," +
            "sum(a.FLOW) as \"flow\" from TBL_OD_RECORD a " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=to_char(a.VEH_CLASS) and c.DICT_TYPE='veh_class' " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and a.WORK_DATE &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and a.WORK_DATE &lt;= #{endDate} " +
            "</when>"+
            "<when test='vehClass != null'> " +
            "and a.VEH_CLASS= #{vehClass} " +
            "</when>"+
            "<when test='hour != null'> " +
            "and a.HOUR= #{hour} " +
            "</when>"+
            "group by a.WORK_DATE,a.HOUR,c.DICT_LABEL order by a.WORK_DATE,c.DICT_LABEL,a.HOUR" +
            "</script>"})
    List<Map> selectOdRecordByClass(@Param("vehClass") Integer vehClass, @Param("startDate") Date startDate, @Param("endDate")  Date endDate, @Param("hour")  Integer hour);

    @Select({"<script>" +
            "select  " +
            "to_char(a.WORK_DATE, 'yyyy-mm-dd') as \"workDate\" ," +
            "a.HOUR as \"hour\"," +
            "c.DICT_LABEL as \"vehClass\"," +
            "sum(a.FLOW) as \"flow\" from TBL_OD_RECORD a " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=to_char(a.VEH_CLASS) and c.DICT_TYPE='veh_class' " +
            "where a.WORK_DATE &gt;= #{startDate} and a.WORK_DATE &lt;= #{endDate} " +
            "<when test='vehClass != null'> " +
            "and a.VEH_CLASS= #{vehClass} " +
            "</when>"+
            "<when test='hour != null'> " +
            "and a.HOUR= #{hour} " +
            "</when>"+
            "group by a.WORK_DATE,a.HOUR,c.DICT_LABEL order by a.WORK_DATE,c.DICT_LABEL,a.HOUR" +
            "</script>"})
    List<OdRecordVelVo> selectOdRecordByClassList(ReportVo reportVo);

}
