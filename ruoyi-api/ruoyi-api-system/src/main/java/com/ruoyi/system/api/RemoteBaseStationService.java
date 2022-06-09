package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.BaseInfo.TblBaseStationInfo;
import com.ruoyi.system.api.factory.RemoteBaseStationFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteBaseStationService", value = ServiceNameConstants.VOCATIONAL, fallbackFactory = RemoteBaseStationFallbackFactory.class)
public interface RemoteBaseStationService {

    @GetMapping("/baseStation/findByNetWorkAndStationId")
    R<TblBaseStationInfo> findByNetWorkAndStationId(@RequestParam(name = "netWork") String netWork, @RequestParam(name = "stationId") String stationId);

}
