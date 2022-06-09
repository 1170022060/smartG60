package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.device.TblDeviceInfo;
import com.ruoyi.system.api.domain.device.TblGantryInfo;
import com.ruoyi.system.api.factory.RemoteDeviceInfoFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 信息发布记录服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteDeviceInfoService", value = ServiceNameConstants.VOCATIONAL, fallbackFactory = RemoteDeviceInfoFallbackFactory.class)
public interface RemoteDeviceInfoService {

    @GetMapping("/device/idInfo")
    R<TblDeviceInfo> idInfo(@RequestParam(name = "id") Long id);

    @GetMapping("/deviceGantry/idInfo")
    R<TblGantryInfo> idGantryInfo(@RequestParam(name = "id") Long id);

}
