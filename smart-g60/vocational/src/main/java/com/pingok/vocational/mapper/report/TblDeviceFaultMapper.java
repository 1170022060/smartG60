package com.pingok.vocational.mapper.report;

import com.pingok.vocational.domain.report.TblDeviceFault;
import com.pingok.vocational.domain.report.vo.DeviceFaultSearch;
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
            "sdd.DICT_LABEL AS \"deviceType\", " +
            "count( tdf.ID ) AS \"num\"  " +
            "FROM " +
            "TBL_DEVICE_FAULT tdf " +
            "JOIN TBL_DEVICE_INFO tdi ON tdf.DEVICE_ID = tdi.ID " +
            "JOIN SYS_DICT_DATA sdd ON sdd.DICT_VALUE = tdi.DEVICE_TYPE  " +
            "WHERE " +
            "tdf.STATUS IN ( 0, 1 )  " +
            "AND sdd.DICT_TYPE = 'device_type'  " +
            "GROUP BY " +
            "sdd.DICT_LABEL" +
            "</script>"})
    List<Map> faultStatistics();


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
            "tdf.DEVICE_ID AS \"deviceId\", " +
            "tdc.CATEGORY_NAME AS \"deviceCategory\"," +
            "tdi.PILE_NO as \"pileNo\" " +
            "FROM " +
            "TBL_DEVICE_FAULT tdf " +
            "JOIN TBL_DEVICE_INFO tdi ON tdi.ID = tdf.DEVICE_ID " +
            "LEFT JOIN TBL_DEVICE_CATEGORY tdc ON tdc.ID = tdi.DEVICE_CATEGORY " +
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
            "tdf.DEVICE_ID AS \"deviceId\",  " +
            "tdc.CATEGORY_NAME AS \"deviceCategory\"," +
            "tdi.PILE_NO as \"pileNo\" " +
            "FROM " +
            "TBL_DEVICE_FAULT tdf " +
            "JOIN TBL_DEVICE_INFO tdi ON tdi.ID = tdf.DEVICE_ID " +
            "LEFT JOIN TBL_DEVICE_CATEGORY tdc ON tdc.ID = tdi.DEVICE_CATEGORY " +
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
            "tdf.DEVICE_ID AS \"deviceId\",  " +
            "tdc.CATEGORY_NAME AS \"deviceCategory\"," +
            "tdi.PILE_NO as \"pileNo\" " +
            "FROM " +
            "TBL_DEVICE_FAULT tdf " +
            "JOIN TBL_DEVICE_INFO tdi ON tdi.ID = tdf.DEVICE_ID " +
            "LEFT JOIN TBL_DEVICE_CATEGORY tdc ON tdc.ID = tdi.DEVICE_CATEGORY " +
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
    List<DeviceFaultSearch> search(@Param("faultType") String faultType, @Param("deviceId") Long deviceId, @Param("faultId") String faultId, @Param("faultDescription") String faultDescription, @Param("status") Integer status);

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

    @Select("select POST_ID as \"postId\" from SYS_USER_POST where USER_ID= #{userId} ")
    Long selectPostId(@Param("userId") Long userId);

    @Select("select CATEGORY_POST as \"categoryPost\" from TBL_DEVICE_CATEGORY where ID= #{deviceCategory} ")
    String selectPostIDs(@Param("deviceCategory") Long deviceCategory);

    @Select({"<script>" +
            "SELECT tbi.DEVICE_NAME as \"deviceName\",tdf.FAULT_TYPE as \"faultType\", " +
            "tdf.FAULT_DESCRIPTION as \"faultDesc\",to_char(tdf.FAULT_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"faultTime\",COUNT(tbi.DEVICE_NAME)as \"count\"  " +
            "FROM TBL_DEVICE_FAULT tdf LEFT JOIN TBL_DEVICE_INFO tbi on tbi.ID=tdf.DEVICE_ID  " +
            "WHERE tbi.DEVICE_NAME IS NOT NULL  " +
            "<when test='deviceName != null'> " +
            " and tbi.DEVICE_NAME like '%' || #{deviceName} || '%' " +
            "</when>"+
            "<when test='startTime != null'> " +
            " and tdf.FAULT_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and tdf.FAULT_TIME &lt;= #{endTime} " +
            "</when>"+
            "GROUP BY tbi.DEVICE_NAME,tdf.FAULT_TYPE,tdf.FAULT_DESCRIPTION,tdf.FAULT_TIME  " +
            "ORDER BY tdf.FAULT_TIME DESC" +
            "</script>"})
    List<Map> selectFaultByDeviceName(@Param("deviceName") String deviceName,@Param("startTime") Date startTime, @Param("endTime")  Date endTime);

    @Select({"<script>" +
            "SELECT tbc.CATEGORY_NAME as \"categoryName\",COUNT(*)as \"count\"  " +
            "FROM TBL_DEVICE_FAULT tdf  " +
            "LEFT JOIN TBL_DEVICE_INFO tbi on tbi.ID=tdf.DEVICE_ID  " +
            "LEFT JOIN TBL_DEVICE_CATEGORY tbc on tbi.DEVICE_CATEGORY=tbc.ID  " +
            "WHERE tbi.DEVICE_CATEGORY = 927 " +
            "<when test='deviceTypeId != null'> " +
            " and tbi.DEVICE_CATEGORY = #{deviceTypeId} " +
            "</when>"+
            "<when test='startTime != null'> " +
            " and tdf.FAULT_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and tdf.FAULT_TIME &lt;= #{endTime} " +
            "</when>" +
            "GROUP BY tbc.CATEGORY_NAME"+
            "</script>"})
    List<Map> selectFaultByDeviceType(@Param("deviceTypeId") Long deviceTypeId,@Param("startTime") Date startTime, @Param("endTime")  Date endTime);

    @Select({"<script>" +
            "SELECT tdf.FAULT_TYPE as \"faultType\",tdf.FAULT_DESCRIPTION as \"faultDesc\",COUNT(*)as \"count\" FROM TBL_DEVICE_FAULT tdf  " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=tdf.FAULT_TYPE and b.DICT_TYPE='fault_type' " +
            "<when test='faultType != null'> " +
            " and tdf.FAULT_TYPE = #{faultType} " +
            "</when>"+
            "<when test='startTime != null'> " +
            " and tdf.FAULT_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and tdf.FAULT_TIME &lt;= #{endTime} " +
            "</when>"+
            "GROUP BY tdf.FAULT_TYPE,tdf.FAULT_DESCRIPTION " +
            "</script>"})
    List<Map> selectFaultByFaultType(@Param("faultType")Integer faultType,@Param("startTime") Date startTime, @Param("endTime")  Date endTime);
}
