package com.pingok.vocational.mapper.emergency;

import com.pingok.vocational.domain.emergency.TblEventPaln;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_EVENT_PALN 数据层
 *
 * @author xia
 */
public interface TblEventPalnMapper extends CommonRepository<TblEventPaln> {

    @Select("SELECT " +
            "ID AS \"id\", " +
            "PLAN_TITLE AS \"planTitle\", " +
            "EVENT_TYPE AS \"eventType\", " +
            "PLAN_FUNCTION AS \"planFunction\", " +
            "STATUS AS \"status\", " +
            "to_char(CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "to_char(UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\"," +
            "CREATE_USER_ID AS \"createUserId\", " +
            "UPDATE_USER_ID AS \"updateUserId\"  " +
            "FROM " +
            "TBL_EVENT_PALN  " +
            "WHERE " +
            "instr( '\"' || EVENT_TYPE || '\"', '\"' || #{eventType} || '\"' ) <>0")
    List<TblEventPaln> findByEventType(@Param("eventType") String eventType);

    @Select("<script>" +
            "SELECT " +
            "tep.ID AS \"id\", " +
            "tep.PLAN_TITLE AS \"planTitle\", " +
            "to_char(tep.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "to_char(tep.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\"," +
            "tep.STATUS  as \"status\", " +
            "CASE tep.STATUS " +
            "WHEN 1 THEN " +
            "'启用' " +
            "ELSE " +
            "'停用' " +
            "END  as \"statusDesc\", " +
            "cusr.NICK_NAME as \"createUser\", " +
            "uusr.NICK_NAME as \"updateUser\" " +
            "FROM " +
            "TBL_EVENT_PALN tep " +
            "LEFT JOIN  SYS_USER cusr ON cusr.USER_ID = tep.CREATE_USER_ID " +
            "LEFT JOIN  SYS_USER uusr ON uusr.USER_ID = tep.UPDATE_USER_ID " +
            "WHERE 1=1  " +
            "<when test='planTitle != null'> " +
            "AND tep.PLAN_TITLE like '%'||#{planTitle}||'%' " +
            "</when>" +
            "</script>")
    List<Map> selectEventPalnList(@Param("planTitle") String planTitle);

    @Select("select EVENT_TYPE from TBL_EVENT_RECORD where ID = #{id}")
    Object eventType(@Param("id") Long id);
}
