package com.pingok.vocational.mapper.assist;

import com.pingok.vocational.domain.assist.TblTollForecastRecord;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_TOLL_FORECAST_RECORD 数据层
 *
 * @author xia
 */
public interface TblTollForecastRecordMapper extends CommonRepository<TblTollForecastRecord> {

    @Select({"<script>" +
            "select b.DICT_LABEL as \"forecastType\" ," +
            "FORECAST_FLOW as \"forecastFlow\" ," +
            "FORECAST_FEE as \"forecastFee\" ," +
            "to_char(a.FORECAST_DATE, 'yyyy-mm-dd') as \"forecastDate\" ," +
            "c.DICT_LABEL as \"week\"  from TBL_TOLL_FORECAST_RECORD a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=to_char(a.FORECAST_TYPE) and b.DICT_TYPE='forecast_type' " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.WEEK and c.DICT_TYPE='week' " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and a.FORECAST_DATE &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and a.FORECAST_DATE &lt;= #{endDate} " +
            "</when>"+
            "<when test='forecastType != null'> " +
            "and a.FORECAST_TYPE= #{forecastType} " +
            "</when>"+
            "order by FORECAST_DATE" +
            "</script>"})
    List<Map> selectTollForecast(@Param("forecastType") Integer forecastType,@Param("startDate") Date startDate, @Param("endDate")  Date endDate);

}
