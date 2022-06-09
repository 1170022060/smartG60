package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.system.api.factory.RemoteIdProducerbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * kafka服务
 *
 * @author qiumin
 */
@FeignClient(contextId = "remoteIdProducerService", value = ServiceNameConstants.ID_PRODUCER, fallbackFactory = RemoteIdProducerbackFactory.class)
public interface RemoteIdProducerService {


    @GetMapping("/idProducer")
    Long nextId();

}
