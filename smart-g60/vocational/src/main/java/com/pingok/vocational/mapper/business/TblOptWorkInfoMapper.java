package com.pingok.vocational.mapper.business;

import com.pingok.vocational.domain.business.TblOptWorkInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_OPT_WORK_INFO 数据层
 *
 * @author xia
 */
public interface TblOptWorkInfoMapper extends CommonRepository<TblOptWorkInfo> {

    @Select({"<script>" +
            "select a.OPT_ID as \"optId\" , " +
            "b.OPT_NAME as \"optName\" , " +
            "to_char(a.IN_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"inTime\" , " +
            "to_char(a.OUT_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"outTime\" , " +
            "to_char(a.WORK_DATE, 'yyyy-mm-dd hh24:mi:ss') as \"workDate\" ," +
            "d.MARK_NAME as \"markName\" , " +
            "c.STATION_NAME as \"stationId\" , " +
            "d.LANE_NAME as \"laneId\" , " +
            "e.DICT_LABEL as \"laneType\" , " +
            "f.DICT_LABEL as \"shift\" , " +
            "round(to_number(a.OUT_TIME-a.IN_TIME)*1440) as \"workingMins\" , " +
            "a.CASH+a.M_PAY+a.OBU+a.SPTCC as \"totalFee\" , " +
            "a.CARD as \"card\" , " +
            "a.RECEIPT as \"receipt\" from TBL_OPT_WORK_INFO a " +
            "left join TBL_OPT_INFO b on a.OPT_ID=b.OPT_ID " +
            "left join TBL_BASE_STATION_INFO c on UPPER(c.STATION_HEX)=UPPER(CONCAT('3101',a.STATION_ID)) " +
            "left join TBL_LANE_INFO d on UPPER(CONCAT(d.STATION_ID,d.LANE_ID))=UPPER(CONCAT(a.STATION_ID,a.LANE_ID)) " +
            "left join SYS_DICT_DATA e on e.DICT_VALUE=a.LANE_TYPE and e.DICT_TYPE='lane_type' " +
            "left join SYS_DICT_DATA f on f.DICT_VALUE=a.SHIFT and f.DICT_TYPE='shift' " +
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
            "<when test='optName != null'> " +
            "and b.USER_NAME= #{optName} " +
            "</when>"+
            "<when test='shift != null'> " +
            "and a.SHIFT= #{shift} " +
            "</when>"+
            "<when test='optId != null'> " +
            "and a.OPT_ID= #{optId} " +
            "</when>"+
            "order by a.WORK_DATE" +
            "</script>"})
    List<Map> selectOptWorkInfo( @Param("startDate") Date startDate,@Param("endDate") Date endDate,@Param("stationId") String stationId, @Param("optName") String optName,@Param("shift") Integer shift,@Param("optId") Integer optId);

}
