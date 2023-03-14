package com.pingok.vocational.mapper.assist;

import com.pingok.vocational.domain.assist.TblCarbonEmission;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_CARBON_EMISSION 数据层
 *
 * @author xia
 */
public interface TblCarbonEmissionMapper extends CommonRepository<TblCarbonEmission> {

    @Select({"<script>" +
            "select b.DICT_LABEL as \"carbonType\" ," +
            "to_char(a.WORK_DATE, 'yyyy-mm-dd') as \"workDate\" ," +
            "c.DICT_LABEL as \"week\" ," +
            "a.DISCHARGE_AMOUNT as \"dischargeAmount\" from TBL_CARBON_EMISSION a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=to_char(a.CARBON_TYPE) and b.DICT_TYPE='carbon_type' " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.WEEK and c.DICT_TYPE='week' " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and a.WORK_DATE &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and a.WORK_DATE &lt;= #{endDate} " +
            "</when>"+
            "order by a.WORK_DATE" +
            "</script>"})
    List<Map> selectCarbonEmission(@Param("startDate") Date startDate, @Param("endDate")  Date endDate);

    @Select("<script>" +
            "select " +
            "<when test='type == 1'> " +
            "SUBSTR(to_char(TIME,'yyyy-mm-dd hh24:mi:ss'), 0, 13) as \"time\", " +
            "</when>"+
            "<when test='type == 2'> " +
            "SUBSTR(to_char(TIME,'yyyy-mm-dd hh24:mi:ss'), 0, 10) as \"time\", " +
            "</when>"+
            "<when test='type == 3'> " +
            "substr(to_char(TIME,'yyyy-mm-dd hh24:mi:ss'),0,7) AS \"time\"," +
            "</when>"+
            "sum(CO2_PER_PROFIT) as \"co2PerProfit\",sum(INCREMENT_PER_HOUR) as \"incrementPerHour\" " +
            "from TBL_ROAD_CO2_PER_PROFIT " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and TIME &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and TIME &lt;= #{endDate} " +
            "</when>"+
            " group by " +
            "<when test='type == 1'> " +
            " SUBSTR(to_char(TIME,'yyyy-mm-dd hh24:mi:ss'), 0, 13) " +
            "</when>"+
            "<when test='type == 2'> " +
            " SUBSTR(to_char(TIME,'yyyy-mm-dd hh24:mi:ss'), 0, 10) " +
            "</when>"+
            "<when test='type == 3'> " +
            " SUBSTR(to_char(TIME,'yyyy-mm-dd hh24:mi:ss'), 0, 7) " +
            "</when>"+
            "<when test='type == 1'> " +
            " order by SUBSTR(to_char(TIME,'yyyy-mm-dd hh24:mi:ss'), 0, 13) " +
            "</when>"+
            "<when test='type == 2'> " +
            " order by SUBSTR(to_char(TIME,'yyyy-mm-dd hh24:mi:ss'), 0, 10) " +
            "</when>"+
            "<when test='type == 3'> " +
            " order by SUBSTR(to_char(TIME,'yyyy-mm-dd hh24:mi:ss'), 0, 7) " +
            "</when>"+
            "</script>")
    List<Map> Co2Emission(@Param("startDate") Date startDate, @Param("endDate")  Date endDate,@Param("type")Integer type);
}
