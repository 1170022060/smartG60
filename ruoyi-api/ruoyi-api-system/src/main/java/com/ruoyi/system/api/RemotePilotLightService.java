package com.ruoyi.system.api;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.factory.RemoteInfoBoardFallbackFactory;
import com.ruoyi.system.api.factory.RemotePilotLightFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remotePilotLightService", value = ServiceNameConstants.DEVICE_MONITOR_SERVICE, fallbackFactory = RemotePilotLightFallbackFactory.class)
public interface RemotePilotLightService {

    @PostMapping("/pilotLight/send")
    R sendCmdToDeviceV2(@RequestBody JSONObject body);

    @GetMapping("/pilotLight/rtStatus")
    R getRtStatus();
}
