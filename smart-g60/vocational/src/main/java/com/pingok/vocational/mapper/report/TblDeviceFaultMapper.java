package com.pingok.vocational.mapper.report;

import com.pingok.vocational.domain.report.TblDeviceFault;
import com.pingok.vocational.domain.report.vo.DeviceFaultTypeVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_DEVICE_FAULT 数据层
 *
 * @author xia
 */
public interface TblDeviceFaultMapper extends CommonRepository<TblDeviceFault> {

    @Select({"<script>" +
            "SELECT " +
            "f.*  " +
            "FROM " +
            "( " +
            "SELECT " +
            "tdf.ID AS \"id\", " +
            "tdf.FAULT_TYPE AS \"faultType\", " +
            "tdf.FAULT_ID AS \"faultId\", " +
            "tdf.FAULT_DESCRIPTION AS \"faultDescription\", " +
            "tdf.REGISTER_TYPE AS \"registerType\", " +
            "tdf.STATUS AS \"status\", " +
            "CASE " +
            "tdf.REGISTER_TYPE  " +
            "WHEN 1 THEN " +
            "'人工登记'  " +
            "WHEN 2 THEN " +
            "'系统登记'  " +
            "END AS \"registerTypeDesc\", " +
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
            "tdf.REMARK AS \"remark\", " +
            "to_char( tdf.FAULT_TIME, 'yyyy-mm-dd hh24:mi:ss' ) AS \"faultTime\", " +
            "to_char( tdf.HANDLE_TIME, 'yyyy-mm-dd hh24:mi:ss' ) AS \"handleTime\", " +
            "to_char( tdf.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss' ) AS \"createTime\", " +
            "to_char( tdf.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss' ) AS \"updateTime\", " +
            "handleUser.NICK_NAME AS \"handleUser\", " +
            "creatUser.NICK_NAME AS \"createUser\", " +
            "updateUser.NICK_NAME AS \"updateUser\", " +
            "tdi.DEVICE_ID AS \"deviceNum\", " +
            "tdi.DEVICE_NAME AS \"deviceName\",  " +
            "tdf.DEVICE_ID AS \"deviceId\"  " +
            "FROM " +
            "TBL_DEVICE_FAULT tdf " +
            "JOIN TBL_DEVICE_INFO tdi ON tdi.ID = tdf.DEVICE_ID " +
            "LEFT JOIN  SYS_USER handleUser ON handleUser.USER_ID = tdf.HANDLE_USER_ID " +
            "LEFT JOIN  SYS_USER creatUser ON creatUser.USER_ID = tdf.CREATE_USER_ID " +
            "LEFT JOIN  SYS_USER updateUser ON updateUser.USER_ID = tdf.UPDATE_USER_ID UNION " +
            "SELECT " +
            "tdf.ID AS \"id\", " +
            "tdf.FAULT_TYPE AS \"faultType\", " +
            "tdf.FAULT_ID AS \"faultId\", " +
            "tdf.FAULT_DESCRIPTION AS \"faultDescription\", " +
            "tdf.REGISTER_TYPE AS \"registerType\", " +
            "tdf.STATUS AS \"status\", " +
            "CASE " +
            "tdf.REGISTER_TYPE  " +
            "WHEN 1 THEN " +
            "'人工登记'  " +
            "WHEN 2 THEN " +
            "'系统登记'  " +
            "END AS \"registerTypeDesc\", " +
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
            "tdf.REMARK AS \"remark\", " +
            "to_char( tdf.FAULT_TIME, 'yyyy-mm-dd hh24:mi:ss' ) AS \"faultTime\", " +
            "to_char( tdf.HANDLE_TIME, 'yyyy-mm-dd hh24:mi:ss' ) AS \"handleTime\", " +
            "to_char( tdf.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss' ) AS \"createTime\", " +
            "to_char( tdf.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss' ) AS \"updateTime\", " +
            "handleUser.NICK_NAME AS \"handleUser\", " +
            "creatUser.NICK_NAME AS \"createUser\", " +
            "updateUser.NICK_NAME AS \"updateUser\", " +
            "tdi.DEVICE_ID AS \"deviceNum\", " +
            "tdi.DEVICE_NAME AS \"deviceName\",  " +
            "tdf.DEVICE_ID AS \"deviceId\"  " +
            "FROM " +
            "TBL_DEVICE_FAULT tdf " +
            "JOIN TBL_GANTRY_INFO tdi ON tdi.ID = tdf.DEVICE_ID " +
            "LEFT JOIN  SYS_USER handleUser ON handleUser.USER_ID = tdf.HANDLE_USER_ID " +
            "LEFT JOIN  SYS_USER creatUser ON creatUser.USER_ID = tdf.CREATE_USER_ID " +
            "LEFT JOIN  SYS_USER updateUser ON updateUser.USER_ID = tdf.UPDATE_USER_ID UNION " +
            "SELECT " +
            "tdf.ID AS \"id\", " +
            "tdf.FAULT_TYPE AS \"faultType\", " +
            "tdf.FAULT_ID AS \"faultId\", " +
            "tdf.FAULT_DESCRIPTION AS \"faultDescription\", " +
            "tdf.REGISTER_TYPE AS \"registerType\", " +
            "tdf.STATUS AS \"status\", " +
            "CASE " +
            "tdf.REGISTER_TYPE  " +
            "WHEN 1 THEN " +
            "'人工登记'  " +
            "WHEN 2 THEN " +
            "'系统登记'  " +
            "END AS \"registerTypeDesc\", " +
            "CASE " +
            "tdf.STATUS  " +
            "WHEN 0 THEN " +
            "'待确认'  " +
            "WHEN 1 THEN " +
            "'已确认'  " +
            "WHEN 2 THEN " +
            "'已解决'  " +
            "WHEN -1 THEN " +
            "'误报'  " +
            "END AS \"statusDesc\", " +
            "tdf.REMARK AS \"remark\", " +
            "to_char( tdf.FAULT_TIME, 'yyyy-mm-dd hh24:mi:ss' ) AS \"faultTime\", " +
            "to_char( tdf.HANDLE_TIME, 'yyyy-mm-dd hh24:mi:ss' ) AS \"handleTime\", " +
            "to_char( tdf.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss' ) AS \"createTime\", " +
            "to_char( tdf.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss' ) AS \"updateTime\", " +
            "handleUser.NICK_NAME AS \"handleUser\", " +
            "creatUser.NICK_NAME AS \"createUser\", " +
            "updateUser.NICK_NAME AS \"updateUser\", " +
            "tdi.DEVICE_ID AS \"deviceNum\", " +
            "tdi.DEVICE_NAME AS \"deviceName\",  " +
            "tdf.DEVICE_ID AS \"deviceId\"  " +
            "FROM " +
            "TBL_DEVICE_FAULT tdf " +
            "JOIN TBL_DEVICE_INFO_LANE tdi ON tdi.ID = tdf.DEVICE_ID " +
            "LEFT JOIN  SYS_USER handleUser ON handleUser.USER_ID = tdf.HANDLE_USER_ID " +
            "LEFT JOIN  SYS_USER creatUser ON creatUser.USER_ID = tdf.CREATE_USER_ID " +
            "LEFT JOIN  SYS_USER updateUser ON updateUser.USER_ID = tdf.UPDATE_USER_ID  " +
            ") f " +
            "where 1=1 " +
            "<when test='faultType != null'> " +
            "and f.\"faultType\"= #{faultType} " +
            "</when>"+
            "<when test='deviceId != null'> " +
            "and f.\"deviceId\"= #{deviceId} " +
            "</when>"+
            "<when test='faultId != null'> " +
            "and f.\"faultId\"= #{faultId} " +
            "</when>"+
            "<when test='faultDescription != null'> " +
            "and f.\"faultDescription\" like CONCAT(CONCAT('%',#{faultDescription}),'%') " +
            "</when>"+
            "<when test='status != null'> " +
            "and f.\"status\"= #{status} " +
            "</when>"+
            "</script>"})
    List<Map> search(@Param("faultType") String faultType, @Param("deviceId") Long deviceId, @Param("faultId") String faultId, @Param("faultDescription") String faultDescription, @Param("status") Integer status);

    @Select({"<script>" +
            "select b.DICT_LABEL as \"faultType\"," +
            "count(1) as \"count\"," +
            "to_char(#{startTime}, 'yyyy-mm-dd hh24:mi:ss') || ' - ' ||to_char(#{endTime}, 'yyyy-mm-dd hh24:mi:ss') as \"time\" from TBL_DEVICE_FAULT a " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=a.FAULT_TYPE and b.DICT_TYPE='fault_type' " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and a.FAULT_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and a.FAULT_TIME &lt;= #{endTime} " +
            "</when>"+
            "<when test='faultType != null'> " +
            "and a.FAULT_TYPE= #{faultType} " +
            "</when>"+
            "group by b.DICT_LABEL order by b.DICT_LABEL" +
            "</script>"})
    List<Map> selectDeviceFaultByType(@Param("faultType") String faultType, @Param("startTime") Date startTime, @Param("endTime")  Date endTime);

    @Select({"<script>" +
            "select b.DICT_LABEL as \"faultType\"," +
            "count(1) as \"count\"," +
            "to_char(#{startTime}, 'yyyy-mm-dd hh24:mi:ss') || ' - ' ||to_char(#{endTime}, 'yyyy-mm-dd hh24:mi:ss') as \"time\" from TBL_DEVICE_FAULT a " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=a.FAULT_TYPE and b.DICT_TYPE='fault_type' " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and a.FAULT_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and a.FAULT_TIME &lt;= #{endTime} " +
            "</when>"+
            "<when test='faultType != null'> " +
            "and a.FAULT_TYPE= #{faultType} " +
            "</when>"+
            "group by b.DICT_LABEL order by b.DICT_LABEL" +
            "</script>"})
    List<DeviceFaultTypeVo> selectDeviceFaultByTypeList(ReportVo reportVo);
}
