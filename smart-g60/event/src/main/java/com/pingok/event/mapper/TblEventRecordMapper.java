package com.pingok.event.mapper;

import com.pingok.event.domain.TblEventRecord;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_EVENT_RECORD 数据层
 *
 * @author qiumin
 */
public interface TblEventRecordMapper extends CommonRepository<TblEventRecord> {

    @Select("<script>" +
            "SELECT " +
            "ter.ID AS \"id\", " +
            "sdd.DICT_LABEL AS \"eventType\", " +
            "ter.LOCATION_INTERVAL AS \"locationInterval\", " +
            "sdd1.DICT_LABEL AS \"vehClass\", " +
            "ter.VEH_PLATE AS \"vehPlate\", " +
            "sdd2.DICT_LABEL AS \"vehColor\", " +
            "ter.EVENT_PHOTO AS \"eventPhoto\", " +
            "ter.EVENT_TIME AS \"eventTime\", " +
            "ter.SPEED AS \"speed\", " +
            "ter.LANE AS \"lane\", " +
            "ter.VIDEO AS \"video\", " +
            "ter.STATUS AS \"status\", " +
            "CASE " +
            "ter.STATUS  " +
            "WHEN 0 THEN " +
            "'待确认'  " +
            "WHEN 1 THEN " +
            "'已确认'  " +
            "WHEN 2 THEN " +
            "'已处置'  " +
            "WHEN -1 THEN " +
            "'误报'  " +
            "END AS \"statusDesc\", " +
            "ter.CONFIRM_TIME AS \"confirmTime\", " +
            "ter.RELIEVE_TIME AS \"relieveTime\", " +
            "usr.USER_NAME AS \"confirmUser\", " +
            "usr1.USER_NAME AS \"relieveUser\"  " +
            "FROM " +
            "TBL_EVENT_RECORD ter " +
            "LEFT JOIN  SYS_DICT_DATA sdd ON sdd.DICT_VALUE = ter.EVENT_TYPE  " +
            "AND sdd.DICT_TYPE = 'event_type' " +
            "LEFT JOIN  SYS_DICT_DATA sdd1 ON sdd1.DICT_VALUE = ter.VEH_CLASS  " +
            "AND sdd1.DICT_TYPE = 'veh_class' " +
            "LEFT JOIN  SYS_DICT_DATA sdd2 ON sdd2.DICT_VALUE = ter.VEH_COLOR  " +
            "AND sdd2.DICT_TYPE = 'veh_color' " +
            "LEFT JOIN  SYS_USER usr ON usr.USER_ID = ter.CONFIRM_USER_ID " +
            "LEFT JOIN  SYS_USER usr1 ON usr1.USER_ID = ter.RELIEVE_USER_ID " +
            "where 1=1 " +
            "<when test='status != null'>" +
            "AND ter.STATUS = #{status} " +
            "</when>" +
            "ORDER BY ter.EVENT_TIME DESC" +
            "</script>")
    List<Map> search(@Param("status") Integer status);

    @Select("SELECT DICT_LABEL FROM SYS_DICT_DATA WHERE DICT_TYPE = 'event_type' AND DICT_VALUE = #{eventType}")
    Object translateEventType(@Param("eventType") String eventType);
}
