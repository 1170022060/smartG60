package com.pingok.vocational.mapper.assist;

import com.pingok.vocational.domain.assist.TblTripTimeRecord;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_TRIP_TIME_RECORD 数据层
 *
 * @author xia
 */
public interface TblTripTimeRecordMapper extends CommonRepository<TblTripTimeRecord> {
    @Select({"<script>" +
            "select to_char(a.FORECAST_DATE, 'yyyy-mm-dd hh24:mi:ss') as \"forecastDate\" ," +
            "a.TRIP_TIME as \"tripTime\" ," +
            "a.FORECAST_ROAD as \"forecastRoad\" ," +
            "case when a.CREATE_USER_ID is null then null else a.CREATE_USER_ID || ':' || b.USER_NAME end as \"createUserName\" from TBL_TRIP_TIME_RECORD a " +
            "left join  SYS_USER b on a.CREATE_USER_ID=b.USER_ID " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and a.FORECAST_DATE &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and a.FORECAST_DATE &lt;= #{endDate} " +
            "</when>"+
            "order by a.FORECAST_DATE" +
            "</script>"})
    List<Map> selectTripTime(@Param("startDate") Date startDate, @Param("endDate")  Date endDate);

}
