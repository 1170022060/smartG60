package com.pingok.vocational.service.customer;


import com.pingok.vocational.domain.customer.TblCustomerProblems;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客户投诉与咨询记录表 服务层
 *
 * @author ruoyi
 */
public interface ICustomerProblemsService {

    /**
     * 根据Id查询客户投诉与咨询记录
     *
     * @param Id Id
     * @return 场地信息
     */
    public TblCustomerProblems selectCustomerProblemsById(Long Id);

    /**
     * 通过处理人ID、起止时间查询客户投诉与咨询记录
     *
     * @param handleUserId 处理人ID
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param handleDept 处理部门
     * @return 客户投诉与咨询记录
     */
    List<Map> selectCustomerProblems(Long handleUserId, Date startTime, Date endTime,Long handleDept);

    /**
     * 新增客户投诉与咨询记录
     *
     * @param tblCustomerProblems 客户投诉与咨询记录
     * @return 结果
     */
    public int insertCustomer(TblCustomerProblems tblCustomerProblems);

    /**
     * 修改客户投诉与咨询记录(回复)
     *
     * @param tblCustomerProblems 客户投诉与咨询记录
     * @return 结果
     */
    public int updateCustomer(TblCustomerProblems tblCustomerProblems);
}
