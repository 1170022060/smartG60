package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.baidu.TblBaiDuMapRecord;
import com.ruoyi.system.api.factory.RemoteArtemisFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 执法记录仪服务
 *
 * @author qiumin
 */
@FeignClient(contextId = "remoteArtemisService", value = ServiceNameConstants.EXTERNAL_SERVICE, fallbackFactory = RemoteArtemisFallbackFactory.class)
public interface RemoteArtemisService {


    @GetMapping("/artemis")
    R updateStatus();
}
