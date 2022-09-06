package com.pingok.vocational.mapper.assist;

import com.pingok.vocational.domain.assist.TblSituationPrediction;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_SITUATION_PREDICTION 数据层
 *
 * @author xia
 */
public interface TblSituationPredictionMapper extends CommonRepository<TblSituationPrediction> {

    @Select({"<script>" +
            "select b.DICT_LABEL as \"trafficType\" ," +
            "to_char(a.WORK_DATE, 'yyyy-mm-dd') as \"workDate\" ," +
            "a.SECTION_INTERVAL as \"sectionInterval\"," +
            "c.DICT_LABEL as \"week\" ," +
            "a.FLOW as \"flow\" from TBL_SITUATION_PREDICTION a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=to_char(a.TRAFFIC_TYPE) and b.DICT_TYPE='traffic_type' " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.WEEK and c.DICT_TYPE='week' " +
            "where a.TRAFFIC_TYPE in (1,2) " +
            "<when test='startDate != null'> " +
            " and a.WORK_DATE &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and a.WORK_DATE &lt;= #{endDate} " +
            "</when>"+
            "order by a.WORK_DATE" +
            "</script>"})
    List<Map> selectSituationFlow(@Param("startDate") Date startDate, @Param("endDate")  Date endDate);

    @Select({"<script>" +
            "select a.WORK_DATE as \"workDate\" ," +
            "a.SECTION_INTERVAL as \"sectionInterval\"," +
            "c.DICT_LABEL as \"week\" ," +
            "a.STATUS as \"status\" from TBL_SITUATION_PREDICTION a " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.WEEK and c.DICT_TYPE='week' " +
            "where a.TRAFFIC_TYPE is null " +
            "<when test='startDate != null'> " +
            " and a.WORK_DATE &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and a.WORK_DATE &lt;= #{endDate} " +
            "</when>"+
            "order by a.WORK_DATE" +
            "</script>"})
    List<Map> selectSituationStatus(@Param("startDate") Date startDate, @Param("endDate")  Date endDate);

}
