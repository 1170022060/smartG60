package com.pingok.monitor.mapper.gantry;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Mapper
public interface TblGantryEventReleaseMapper {
    @Select({"<script>" +
            "SELECT  " +
            "tgi.DEVICE_NAME as \"gantryName\"," +
            "ger.GANTRY_ID as \"gantryId\", " +
            "ger.OBU_DELAY as \"obuDelay\", " +
            "ger.STAKE_NUM as \"stakeNum\", " +
            "case when ger.DIRECTION=1 then '上行' when ger.DIRECTION=2 then '下行' END as \"direction\", " +
            "ger.BROADCAST_RANGE as \"broadcastRange\", " +
            "case when ger.EVENT_TYPE='01' then '蜂鸣播报模式' when ger.EVENT_TYPE='02'  " +
            "then '事件编码模式发布' when ger.EVENT_TYPE='03' then '事件透传信息模式发布' END as \"eventType\", " +
            "c.DICT_LABEL as \"eventId\", " +
            "ger.EVENT_POSITION as \"eventPos\", " +
            "ger.EVENT_DISCOUNT as \"eventDis\", " +
            "ger.EVENT_INFO as \"eventInfo\", " +
            "to_char(ger.REPORT_BEGIN_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"reportBeginTime\", " +
            "to_char(ger.REPORT_END_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"reportEndTime\", " +
            "ger.CRYPTO_GRAPHIC_DIGEST as \"remark\", " +
            "case when ger.STATUS=0 then '发布失败' when ger.STATUS=1 then '发布成功' END as \"status\", " +
            "to_char(ger.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\", " +
            "to_char(ger.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\", " +
            "a.USER_NAME as \"createUser\", " +
            "b.USER_NAME as \"updateUser\" " +
            "FROM TBL_GANTRY_EVENT_RELEASE ger " +
            "LEFT JOIN TBL_GANTRY_INFO tgi on tgi.DEVICE_ID = ger.GANTRY_ID" +
            "LEFT JOIN SYS_USER a on a.USER_ID = ger.CREATE_USER_ID " +
            "LEFT JOIN SYS_USER b on b.USER_ID = ger.UPDATE_USER_ID " +
            "LEFT JOIN SYS_DICT_DATA c on c.DICT_VALUE=ger.EVENT_ID and c.DICT_TYPE='gantry_v2x_event_type' " +
            "where 1=1 " +
            "<when test='gantryId != null'> " +
            " and ger.GANTRY_ID like '%' || #{gantryId} || '%' " +
            "</when>"+
            "<when test='eventType != null'> " +
            " and ger.EVENT_TYPE = #{eventType} " +
            "</when>"+
            "<when test='status != null'> " +
            " and ger.STATUS = #{status} " +
            "</when>"+
            "<when test='startTime != null'> " +
            " and ger.CREATE_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and ger.CREATE_TIME &lt;= #{endTime} " +
            "</when>"+
            "<when test='status != null'> " +
            " and ger.STATUS = #{status} " +
            "</when>"+
            "</script>"})
    List<Map> getRecord(@Param("gantryId") String gantryId, @Param("eventType") String eventType,
                        @Param("status") Integer status, @Param("startTime")Date startTime
                        , @Param("endTime")Date endTime);
}
