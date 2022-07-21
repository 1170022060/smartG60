package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import com.ruoyi.system.api.factory.RemoteKafkaFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * kafka服务
 *
 * @author qiumin
 */
@FeignClient(contextId = "remoteKafkaService", value = ServiceNameConstants.KAFKA_SERVICE, fallbackFactory = RemoteKafkaFallbackFactory.class)
public interface RemoteKafkaService {

    /**
     * 发送kafka消息
     *
     * @return 结果
     */
    @PostMapping("/kafka")
    R send(@Validated @RequestBody KafkaEnum kafkaEnum);

}
