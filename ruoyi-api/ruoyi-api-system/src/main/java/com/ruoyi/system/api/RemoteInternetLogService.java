package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.internet.InternetLogininfor;
import com.ruoyi.system.api.factory.RemoteIntegerLogFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 第三方日志服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteInternetLogService", value = ServiceNameConstants.EXTERNAL_SERVICE, fallbackFactory = RemoteIntegerLogFallbackFactory.class)
public interface RemoteInternetLogService {


    /**
     * 保存访问记录
     *
     * @param logininfor 访问实体
     * @param source     请求来源
     * @return 结果
     */
    @PostMapping("/internetLoginInfor")
    public R<Boolean> saveLogininfor(@RequestBody InternetLogininfor logininfor, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
