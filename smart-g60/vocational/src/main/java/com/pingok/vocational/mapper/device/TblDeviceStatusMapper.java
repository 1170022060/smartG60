package com.pingok.vocational.mapper.device;

import com.pingok.vocational.domain.device.TblDeviceStatus;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_DEVICE_STATUS_CURRENT 数据层
 *
 * @author xia
 */
public interface TblDeviceStatusMapper extends CommonRepository<TblDeviceStatus> {


    @Select("<script>" +
            "select * from ( " +
            "SELECT " +
            "tdi.ID as \"id\", " +
            "tfi.FIELD_NAME as \"fieldName\"," +
            "tfi.FIELD_NUM as fieldNum, " +
            "tdi.DEVICE_NAME as \"deviceName\", " +
            "tdi.DEVICE_ID as \"deviceId\", " +
            "sdd.DICT_LABEL as \"deviceType\"," +
            "tdi.DEVICE_TYPE as \"type\", " +
            "tdi.FIX_POSITION as \"fixPosition\", " +
            "nvl(tds.STATUS,0) as \"status\", " +
            "nvl(tds.STATUS_DESC,'未知') as \"statusDesc\", " +
            "tds.STATUS_DETAILS as \"statusDetails\" " +
            "FROM " +
            "TBL_DEVICE_INFO tdi " +
            "LEFT JOIN TBL_FIELD_INFO tfi on tfi.ID = tdi.FIELD_BELONG " +
            "LEFT JOIN TBL_DEVICE_STATUS tds ON tds.DEVICE_ID = tdi.ID  " +
            "LEFT JOIN SYS_DICT_DATA sdd on sdd.DICT_VALUE=to_char(tdi.DEVICE_TYPE) and sdd.DICT_TYPE='device_type' " +
            "where tfi.FIELD_NAME like '%枫泾服务区%' and tfi.TYPE=4 " +
            "UNION ALL " +
            "SELECT sti.ID as id,case sti.FIELD_ID WHEN 3940 then '枫泾服务区南区' else '枫泾服务区北区' END as fieldName," +
            "case sti.FIELD_ID WHEN 3940 then 'fjfwqnq' else 'fjfwqbq' END as fieldNum," +
            "sti.SER_NAME as deviceName,null as deviceId,sdd.DICT_LABEL as deviceType,13 as \"type\"," +
            "null as fixPosition,sti.STATUS as status," +
            "sti.STATUS_DESC as statusDesc,null as statusDetails " +
            "FROM TBL_SMART_TOILET_INFO sti " +
            "LEFT JOIN SYS_DICT_DATA sdd on sdd.DICT_VALUE=13 and sdd.DICT_TYPE='device_type' )a " +
            "where 1=1 " +
            "<when test='fieldNum != null'> " +
            "and a.\"fieldNum\" = #{fieldNum} " +
            "</when>" +
            "<when test='deviceType != null'> " +
            "and a.\"type\" = #{deviceType} " +
            "</when>" +
            "<when test='status != null'> " +
            "and nvl(a.\"status\",0) = #{status} " +
            "</when>"+
            "</script>")
    List<Map> serviceDevice(@Param("fieldNum") String fieldNum, @Param("deviceType") Integer deviceType, @Param("status") Integer status);

    @Select("<script>" +
            "SELECT " +
            "tfi.FIELD_NAME as \"fieldName\", " +
            "sdd.DICT_LABEL as \"deviceType\", " +
            "100*round(sum(case tds.STATUS_DESC when '网络异常' then 0 else 1 end)/count(1),4) || '%' as \"online\", " +
            "100*round(sum(case tds.STATUS_DESC when '网络异常' then 1 when '正常' then 1 else 0 end)/count(1),4) || '%' as \"intact\", " +
            "100*round(sum(case tds.STATUS_DESC when '网络异常' then 0 when '正常' then 0 else 1 end)/count(1),4) || '%' as \"fault\" " +
            "FROM " +
            "TBL_DEVICE_INFO tdi " +
            "LEFT JOIN TBL_FIELD_INFO tfi on tfi.ID = tdi.FIELD_BELONG " +
            "LEFT JOIN TBL_DEVICE_STATUS tds ON tds.DEVICE_ID = tdi.ID  " +
            "LEFT JOIN SYS_DICT_DATA sdd on sdd.DICT_VALUE=to_char(tdi.DEVICE_TYPE) and sdd.DICT_TYPE='device_type' " +
            "where tfi.FIELD_NAME like '%枫泾服务区%' and tfi.TYPE=4 " +
            "<when test='fieldNum != null'> " +
            "and tfi.FIELD_NUM = #{fieldNum} " +
            "</when>" +
            "<when test='deviceType != null'> " +
            "and tdi.DEVICE_TYPE = #{deviceType} " +
            "</when>" +
            "group by tfi.FIELD_NAME,sdd.DICT_LABEL "+
            "</script>")
    List<Map> serviceDeviceStatus(@Param("fieldNum") String fieldNum,@Param("deviceType") Integer deviceType);

    @Select({"<script>" +
            "SELECT " +
            "tdf.ID AS \"id\", " +
            "tfi.FIELD_NAME as \"fieldName\", " +
            "tdi.DEVICE_NAME as \"deviceName\", " +
            "tdf.FAULT_TYPE AS \"faultType\", " +
            "tdf.FAULT_ID AS \"faultId\", " +
            "tdf.FAULT_DESCRIPTION AS \"faultDescription\", " +
            "tdf.STATUS AS \"status\", " +
            "CASE " +
            "tdf.STATUS  " +
            "WHEN 0 THEN " +
            "'待确认'  " +
            "WHEN 1 THEN " +
            "'已确认'  " +
            "WHEN 2 THEN " +
            "'已解决'  " +
            "WHEN - 1 THEN " +
            "'误报'  " +
            "END AS \"statusDesc\", " +
            "to_char( tdf.FAULT_TIME, 'yyyy-mm-dd hh24:mi:ss' ) AS \"faultTime\", " +
            "to_char( tdf.HANDLE_TIME, 'yyyy-mm-dd hh24:mi:ss' ) AS \"handleTime\", " +
            "to_char( tdf.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss' ) AS \"createTime\", " +
            "to_char( tdf.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss' ) AS \"updateTime\", " +
            "handleUser.NICK_NAME AS \"handleUser\", " +
            "creatUser.NICK_NAME AS \"createUser\", " +
            "updateUser.NICK_NAME AS \"updateUser\", " +
            "tdi.DEVICE_ID AS \"deviceId\" " +
            "FROM " +
            "TBL_DEVICE_FAULT tdf " +
            "JOIN TBL_DEVICE_INFO tdi ON tdi.ID = tdf.DEVICE_ID " +
            "LEFT JOIN  TBL_FIELD_INFO tfi on tfi.ID = tdi.FIELD_BELONG " +
            "LEFT JOIN  SYS_USER handleUser ON handleUser.USER_ID = tdf.HANDLE_USER_ID " +
            "LEFT JOIN  SYS_USER creatUser ON creatUser.USER_ID = tdf.CREATE_USER_ID " +
            "LEFT JOIN  SYS_USER updateUser ON updateUser.USER_ID = tdf.UPDATE_USER_ID " +
            "LEFT JOIN SYS_DICT_DATA sdd on sdd.DICT_VALUE=to_char(tdi.DEVICE_TYPE) and sdd.DICT_TYPE='device_type' " +
            "where tfi.FIELD_NAME like '%枫泾服务区%' and tfi.TYPE=4 " +
            "<when test='fieldNum != null'> " +
            "and tfi.FIELD_NUM = #{fieldNum} " +
            "</when>" +
            "<when test='deviceType != null'> " +
            "and tdi.DEVICE_TYPE = #{deviceType} " +
            "</when>" +
            "<when test='deviceId != null'> " +
            "and tdi.DEVICE_ID = #{deviceId} " +
            "</when>"+
            "<when test='faultId != null'> " +
            "and tdf.FAULT_ID = #{faultId} " +
            "</when>"+
            "<when test='faultDescription != null'> " +
            "and tdf.FAULT_DESCRIPTION like CONCAT(CONCAT('%',#{faultDescription}),'%') " +
            "</when>"+
            "<when test='status != null'> " +
            "and tdf.STATUS = #{status} " +
            "</when>"+
            "</script>"})
    List<Map> serviceDeviceFault(@Param("fieldNum") String fieldNum,@Param("deviceType") Integer deviceType,@Param("deviceId") String deviceId,@Param("faultId") String faultId, @Param("faultDescription") String faultDescription, @Param("status") Integer status);

    @Select("<script>" +
            "select a.ID as \"id\", a.DEVICE_NAME as \"deviceName\", a.DEVICE_TYPE as \"deviceType\", " +
            "b.STATUS as \"deviceStatus\", b.TIME as \"statusTime\", " +
            "b.STATUS_DESC as \"statusDesc\", b.STATUS_DETAILS as \"statusDetails\" " +
            " from TBL_DEVICE_INFO a " +
            " LEFT JOIN TBL_DEVICE_STATUS b on a.ID = b.DEVICE_ID " +
            " LEFT JOIN  TBL_FIELD_INFO tfi on tfi.ID = a.FIELD_BELONG " +
            "where tfi.FIELD_NAME like '%枫泾服务区%' and tfi.TYPE=4" +
            "UNION ALL " +
            "SELECT sti.ID as \"id\",sti.SER_NAME as \"deviceName\",13 as \"deviceType\"," +
            "sti.STATUS as \"deviceStatus\",sti.UPDATE_TIME as \"statusTime\"," +
            "sti.STATUS_DESC as \"statusDesc\",null as \"statusDetails\" " +
            "FROM TBL_SMART_TOILET_INFO sti " +
            "</script>")
    List<Map> getDeviceStatusDesc();
}
