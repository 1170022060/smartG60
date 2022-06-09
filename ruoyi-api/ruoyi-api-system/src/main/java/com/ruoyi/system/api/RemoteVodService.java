package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.kafuka.TblKafkaFailInfo;
import com.ruoyi.system.api.factory.RemoteKafkaFallbackFactory;
import com.ruoyi.system.api.factory.RemoteVodFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * vod服务
 *
 * @author qiumin
 */
@FeignClient(contextId = "remoteVodService", value = ServiceNameConstants.VOD_SERVICE, fallbackFactory = RemoteVodFallbackFactory.class)
public interface RemoteVodService {


    @PutMapping("/vod")
    R updateCameraList();

    @PostMapping("/vod/heartbeat")
    R heartbeat();
}
