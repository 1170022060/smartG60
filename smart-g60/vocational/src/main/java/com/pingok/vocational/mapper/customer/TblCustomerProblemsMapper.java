package com.pingok.vocational.mapper.customer;

import com.pingok.vocational.domain.customer.CustomerProblemsEx;
import com.pingok.vocational.domain.customer.TblCustomerProblems;
import com.pingok.vocational.domain.customer.vo.CustomerProblemsVo;
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
            "e.NICK_NAME as \"handler\"," +
            "case a.STATUS when 0 then '已回复' else '待回复' end as \"status\"," +
            "to_char(a.REPLY_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"replyTime\" ," +
            "c.NICK_NAME as \"replyUser\"," +
            "f.NICK_NAME as \"createUser\", " +
            "to_char(a.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\" " +
            " from TBL_CUSTOMER_PROBLEMS a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.COMPLAINT_TYPE and b.DICT_TYPE='complaint_type' " +
            "left join  SYS_USER c on a.REPLY_USER_ID=c.USER_ID " +
            "left join  SYS_DEPT d ON d.DEPT_ID = a.HANDLE_DEPT " +
            "left join  SYS_USER e on a.HANDLER=e.USER_ID " +
            "left join  SYS_USER f on a.CREATE_USER_ID=f.USER_ID " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and a.COMPLAINT_DATE &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and a.COMPLAINT_DATE &lt;= #{endTime} " +
            "</when>"+
            "<when test='replyUser != null'> " +
            "and c.NICK_NAME= #{replyUser} " +
            "</when>"+
            "<when test='handleDept != null'> " +
            "and (a.HANDLE_DEPT= #{handleDept} or a.HANDLE_DEPT in (SELECT DEPT_ID FROM SYS_DEPT CONNECT BY PRIOR DEPT_ID = PARENT_ID START WITH PARENT_ID = #{handleDept})) " +
            "</when>"+
            "order by a.COMPLAINT_DATE" +
            "</script>"})
    List<CustomerProblemsEx> selectCustomerProblems(CustomerProblemsVo customerProblemsVo);
}
