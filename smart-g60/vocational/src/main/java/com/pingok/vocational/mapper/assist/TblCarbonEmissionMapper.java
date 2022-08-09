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

}
