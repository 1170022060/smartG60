package com.pingok.external.service.user;

import com.ruoyi.system.api.domain.internet.InternetUser;

/**
 * 第三方系统授权账户信息 服务层
 *
 * @author pingok
 */
public interface IInternetUserService {
    InternetUser selectByUserName(String userName);
}
