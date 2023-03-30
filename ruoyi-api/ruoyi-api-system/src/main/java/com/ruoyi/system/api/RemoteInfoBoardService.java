package com.ruoyi.system.api;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.factory.RemoteInfoBoardFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteInfoBoardService", value = ServiceNameConstants.DEVICE_MONITOR_SERVICE, fallbackFactory = RemoteInfoBoardFallbackFactory.class)
public interface RemoteInfoBoardService {

    @PostMapping("/infoBoard/publish")
    R publish(@RequestBody JSONObject vmsPublishInfo);

}
