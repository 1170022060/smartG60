package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.amap.TblAutoNaviMapRecord;
import com.ruoyi.system.api.factory.RemoteAmapFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 高德服务
 *
 * @author qiumin
 */
@FeignClient(contextId = "remoteAmapService", value = ServiceNameConstants.EXTERNAL_SERVICE, fallbackFactory = RemoteAmapFallbackFactory.class)
public interface RemoteAmapService {

    @PostMapping("/amap")
    R eventPublish(@RequestBody TblAutoNaviMapRecord autoNaviMapRecord);

    @PutMapping("/amap")
    R eventRelieve(@RequestParam(value = "id") Long id);
}
