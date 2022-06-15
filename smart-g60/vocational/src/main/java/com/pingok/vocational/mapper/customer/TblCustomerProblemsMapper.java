package com.pingok.vocational.mapper.customer;

import com.pingok.vocational.domain.customer.TblCustomerProblems;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_CUSTOMER_PROBLEMS 数据层
 *
 * @author xia
 */
public interface TblCustomerProblemsMapper extends CommonRepository<TblCustomerProblems> {

    @Select({"<script>" +
            "select a.ID as \"id\", " +
            "to_char(a.COMPLAINT_DATE, 'yyyy-mm-dd hh24:mi:ss') as \"complaintDate\" ," +
            "b.DICT_LABEL as \"complaintType\" ," +
            "a.DESCRIBE as \"describe\" ," +
            "a.RESULT as \"result\" ," +
            "a.COMPLAINT_NAME as \"complaintName\" ," +
            "a.CONTACT_INFO as \"contactInfo\" ," +
            "d.DEPT_NAME as \"handleDept\" ," +
            "to_char(a.HANDLE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"handleTime\" ," +
            "case when a.HANDLE_USER_ID is null then null else a.HANDLE_USER_ID || ':' || c.USER_NAME end as \"handleUserNum\" from TBL_CUSTOMER_PROBLEMS a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.COMPLAINT_TYPE and b.DICT_TYPE='complaint_type' " +
            "left join  SYS_USER c on a.HANDLE_USER_ID=c.USER_ID " +
            "left join  SYS_DEPT d ON d.DEPT_ID = a.HANDLE_DEPT " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and a.COMPLAINT_DATE &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and a.COMPLAINT_DATE &lt;= #{endTime} " +
            "</when>"+
            "<when test='handleUserId != null'> " +
            "and a.HANDLE_USER_ID= #{handleUserId} " +
            "</when>"+
            "<when test='handleDept != null'> " +
            "and (a.HANDLE_DEPT= #{handleDept} or a.HANDLE_DEPT in (SELECT DEPT_ID FROM SYS_DEPT CONNECT BY PRIOR DEPT_ID = PARENT_ID START WITH PARENT_ID = #{handleDept})) " +
            "</when>"+
            "order by a.COMPLAINT_DATE" +
            "</script>"})
    List<Map> selectCustomerProblems(@Param("handleUserId") Long handleUserId, @Param("startTime") Date startTime, @Param("endTime")  Date endTime,@Param("handleDept") Long handleDept);
}
