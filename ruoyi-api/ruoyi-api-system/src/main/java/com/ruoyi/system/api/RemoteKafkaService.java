package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.kafuka.TblKafkaFailInfo;
import com.ruoyi.system.api.factory.RemoteKafkaFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    R send(@Validated @RequestBody TblKafkaFailInfo tblKafkaFailInfo);

    /**
     * 查询kafka发送失败数据
     *
     * @return 结果
     */
    @GetMapping("/kafka")
    R<List<TblKafkaFailInfo>> findAll();

    /**
     * 删除已发送数据
     *
     * @return 结果
     */
    @DeleteMapping("/kafka")
    R delete(@RequestParam("id") Long id);
}
