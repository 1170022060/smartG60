package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.internet.InternetLogininfor;
import com.ruoyi.system.api.domain.internet.InternetUser;
import com.ruoyi.system.api.factory.RemoteIntegerUserFallbackFactory;
import com.ruoyi.system.api.model.LoginUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 第三方系统授权服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteInternetUserService", value = ServiceNameConstants.EXTERNAL_SERVICE, fallbackFactory = RemoteIntegerUserFallbackFactory.class)
public interface RemoteInternetUserService {


    @PostMapping("/internetUser/selectByUserName")
    R<LoginUser> selectByUserName(@RequestParam(value = "userName") String userName, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
