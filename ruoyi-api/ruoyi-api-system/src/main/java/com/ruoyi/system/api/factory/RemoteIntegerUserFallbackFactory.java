package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteInternetUserService;
import com.ruoyi.system.api.domain.internet.InternetLogininfor;
import com.ruoyi.system.api.domain.internet.InternetUser;
import com.ruoyi.system.api.model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 第三方授权服务降级处理
 *
 * @author ruoyi
 */
@Component
public class RemoteIntegerUserFallbackFactory implements FallbackFactory<RemoteInternetUserService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteIntegerUserFallbackFactory.class);

    @Override
    public RemoteInternetUserService create(Throwable throwable) {
        log.error("第三方授权服务调用失败:{}", throwable.getMessage());
        return new RemoteInternetUserService() {

            @Override
            public R<LoginUser> selectByUserName(String userName, String source) {
                return R.fail("获取第三方系统授权账户失败:" + throwable.getMessage());
            }
        };

    }
}
