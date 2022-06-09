package com.ruoyi.system.api.factory;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteDeviceInfoService;
import com.ruoyi.system.api.domain.device.TblDeviceInfo;
import com.ruoyi.system.api.domain.device.TblGantryInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 设备基础信息服务降级处理
 *
 * @author ruoyi
 */
@Component
public class RemoteDeviceInfoFallbackFactory implements FallbackFactory<RemoteDeviceInfoService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteDeviceInfoFallbackFactory.class);

    @Override
    public RemoteDeviceInfoService create(Throwable throwable) {
        log.error("设备基础信息服务调用失败:{}", throwable.getMessage());
        return new RemoteDeviceInfoService() {

            @Override
            public R<TblDeviceInfo> idInfo(Long id) {
                return null;
            }

            @Override
            public R<TblGantryInfo> idGantryInfo(Long id) {
                return null;
            }
        };
    }
}
