package com.pingok.external.service.user.impl;

import com.pingok.external.mapper.user.InternetUserMapper;
import com.pingok.external.service.user.IInternetUserService;
import com.ruoyi.system.api.domain.internet.InternetUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * 第三方系统授权账户信息 服务层处理
 *
 * @author pingok
 */
@Service
public class InternetUserServiceImpl implements IInternetUserService {

    @Autowired
    private InternetUserMapper internetUserMapper;

    @Override
    public InternetUser selectByUserName(String userName) {
        Example example = new Example(InternetUser.class);
        example.createCriteria().andEqualTo("userName", userName);
        return internetUserMapper.selectOneByExample(example);
    }
}
