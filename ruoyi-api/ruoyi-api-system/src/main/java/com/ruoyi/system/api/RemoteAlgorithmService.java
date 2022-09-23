package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.factory.RemoteAlgorithmFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Algorithm服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteAlgorithmService", value = ServiceNameConstants.ALGORITHM_SERVICE, fallbackFactory = RemoteAlgorithmFallbackFactory.class)
public interface RemoteAlgorithmService {

    /**
     * 保存碳排放记录
     * @return
     */
    @PostMapping("/algVehCarbonEmission/")
    R<String> saveAlgVehCarbonEmission();

    /**
     * 保存交通状态记录
     * @return
     */
    @PostMapping("/algTrafficStatus/")
    R<String> saveAlgTrafficStatus(@RequestParam("direction") Integer direction);

    /**
     * 保存收费预测记录
     * @return
     */
    @GetMapping("/predictCharge/autoCurrentDayPredictCharge")
    R<String> autoCurrentDayPredictCharge();
}
