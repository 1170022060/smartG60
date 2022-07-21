package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteRoadDoctorService;
import com.ruoyi.system.api.domain.SysPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 道路医生服务降级处理
 *
 * @author ruoyi
 */
@Component
public class RemoteRoadDoctorFallbackFactory implements FallbackFactory<RemoteRoadDoctorService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteRoadDoctorFallbackFactory.class);

    @Override
    public RemoteRoadDoctorService create(Throwable throwable) {
        log.error("道路医生服务调用失败:{}", throwable.getMessage());
        return new RemoteRoadDoctorService() {


            @Override
            public R updateDisease(String source) {
                return null;
            }
        };
    }
}
