package com.pingok.vocational.mapper.emergency;

import com.pingok.vocational.domain.emergency.TblEmergencyGroup;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_EMERGENCY_GROUP 数据层
 *
 * @author xia
 */
public interface TblEmergencyGroupMapper extends CommonRepository<TblEmergencyGroup> {
    @Select({"<script>" +
            "select a.ID as \"id\"," +
            "a.GROUP_NAME as \"groupName\" ," +
            "e.DEPT_NAME || '_' || d.USER_NAME as \"groupLeader\" ," +
            "a.DUTY as \"duty\" ," +
            "a.STATUS as \"status\"," +
            "a.REMARK as \"remark\"," +
            "to_char(a.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "to_char(a.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\"," +
            "case when a.CREATE_USER_ID is null then null else a.CREATE_USER_ID || ':' || b.USER_NAME end as \"createUserName\"," +
            "case when a.UPDATE_USER_ID is null then null else a.UPDATE_USER_ID || ':' || c.USER_NAME end as \"updateUserName\" from TBL_EMERGENCY_GROUP a " +
            "left join  SYS_USER b on a.CREATE_USER_ID=b.USER_ID " +
            "left join  SYS_USER c on a.UPDATE_USER_ID=c.USER_ID " +
            "left join  SYS_USER d on a.GROUP_LEADER=d.USER_ID " +
            "left join  SYS_DEPT e on d.DEPT_ID=e.DEPT_ID " +
            "where 1=1" +
            "<when test='groupName != null'> " +
            "and a.GROUP_NAME like CONCAT(CONCAT('%',#{groupName}),'%') " +
            "</when>"+
            "<when test='groupLeader != null'> " +
            "and a.GROUP_LEADER = #{groupLeader} " +
            "</when>"+
            "<when test='status != null'> " +
            "and a.STATUS= #{status} " +
            "</when>"+
            "</script>"})
    List<Map> selectEmergencyGroup(@Param("groupName") String groupName, @Param("groupLeader") Long groupLeader, @Param("status") Integer status);

    @Select("select * from TBL_EMERGENCY_GROUP where GROUP_NAME= #{groupName} and rownum = 1")
    TblEmergencyGroup checkGroupNameUnique(@Param("groupName") String groupName);

    @Select("select USER_ID as \"userId\",DEPT_NAME || '_' || USER_NAME as \"userName\" from  SYS_USER a " +
            "left join  SYS_DEPT b on b.DEPT_ID=a.DEPT_ID " +
            "order by a.DEPT_ID")
    List<Map> selectDeptUser();

}
