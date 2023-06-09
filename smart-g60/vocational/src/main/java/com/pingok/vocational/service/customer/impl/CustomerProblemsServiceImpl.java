package com.pingok.vocational.service.customer.impl;

import com.pingok.vocational.domain.customer.CustomerProblemsEx;
import com.pingok.vocational.domain.customer.TblCustomerProblems;
import com.pingok.vocational.domain.customer.vo.CustomerProblemsVo;
import com.pingok.vocational.mapper.customer.TblCustomerProblemsMapper;
import com.pingok.vocational.service.customer.ICustomerProblemsService;
import com.ruoyi.common.core.utils.PinYinUtil;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客户投诉与咨询记录表 服务层处理
 *
 * @author ruoyi
 */
@Service
public class CustomerProblemsServiceImpl implements ICustomerProblemsService {

    @Autowired
    private TblCustomerProblemsMapper tblCustomerProblemsMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public TblCustomerProblems selectCustomerProblemsById(Long Id) {
        return tblCustomerProblemsMapper.selectByPrimaryKey(Id);
    }

    @Override
    public List<Map> selectInfo(CustomerProblemsVo customerProblemsVo) {
        return tblCustomerProblemsMapper.selectInfo(customerProblemsVo);
    }

    @Override
    public List<CustomerProblemsEx> selectCustomerProblems(CustomerProblemsVo customerProblemsVo) {
        return tblCustomerProblemsMapper.selectCustomerProblems(customerProblemsVo);
    }

    @Override
    public int insertCustomer(TblCustomerProblems tblCustomerProblems) {
        tblCustomerProblems.setId(remoteIdProducerService.nextId());
        tblCustomerProblems.setCreateTime(new Date());
        tblCustomerProblems.setCreateUserId(SecurityUtils.getUserId());
        return tblCustomerProblemsMapper.insert(tblCustomerProblems);
    }

    @Override
    public int updateCustomer(TblCustomerProblems tblCustomerProblems) {
//        tblCustomerProblems.setUpdateTime(new Date());
//        tblCustomerProblems.setUpdateUserId(SecurityUtils.getUserId());
        tblCustomerProblems.setReplyTime(new Date());
        tblCustomerProblems.setReplyUserId(SecurityUtils.getUserId());
        if (tblCustomerProblems.getResult() !=null){
            tblCustomerProblems.setStatus(0);
        }
        return tblCustomerProblemsMapper.updateByPrimaryKey(tblCustomerProblems);
    }
}
