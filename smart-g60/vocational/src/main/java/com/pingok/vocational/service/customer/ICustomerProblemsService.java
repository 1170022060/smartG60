package com.pingok.vocational.service.customer;


import com.pingok.vocational.domain.customer.CustomerProblemsEx;
import com.pingok.vocational.domain.customer.TblCustomerProblems;
import com.pingok.vocational.domain.customer.vo.CustomerProblemsVo;

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
     * 分页查询
     * @return
     */
    List<Map> selectInfo(CustomerProblemsVo customerProblemsVo);

    /**
     * 导出时查询
     *
     * @return 客户投诉与咨询记录
     */
    List<CustomerProblemsEx> selectCustomerProblems(CustomerProblemsVo customerProblemsVo);

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
