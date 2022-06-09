package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteBaseStationService;
import com.ruoyi.system.api.domain.BaseInfo.TblBaseStationInfo;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.model.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 收费站基础信息服务降级处理
 * 
 * @author ruoyi
 */
@Component
public class RemoteBaseStationFallbackFactory implements FallbackFactory<RemoteBaseStationService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteBaseStationFallbackFactory.class);

    @Override
    public RemoteBaseStationService create(Throwable throwable)
    {
        log.error("收费站基础信息服务调用失败:{}", throwable.getMessage());
        return new RemoteBaseStationService()
        {

            @Override
            public R<TblBaseStationInfo> findByNetWorkAndStationId(String netWork, String stationId) {
                return null;
            }
        };
    }
}
