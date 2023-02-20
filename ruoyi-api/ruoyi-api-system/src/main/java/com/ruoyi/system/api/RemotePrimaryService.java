package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.factory.RemotePrimaryFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId = "remotePrimary", value = ServiceNameConstants.EXTERNAL_SERVICE, fallbackFactory = RemotePrimaryFallbackFactory.class)
public interface RemotePrimaryService {
    /**
     * 采集申请大件运输车辆信息数据
     *
     * @return
     */
    @GetMapping("/largeTransport")
    R largeTransportCollect();

    /**
     * 采集超限流量、超限率时间对比数据
     *
     * @return
     */
    @GetMapping("/olFlowAndRate")
    R olFlowAndRateCollect();

    /**
     * 采集超限流量分布数据
     *
     * @return
     */
    @GetMapping("/olFlowInfo")
    R olFlowCollect();

    /**
     * 采集超限重量占比对比数据
     *
     * @return
     */
    @GetMapping("/olWeightAndRate")
    R olWeightAndRateCollect();

    /**
     * 采集收费站经纬度信息数据
     *
     * @return
     */
    @GetMapping("/stationLLInfo")
    R stationLLInfoCollect();

    /**
     * 采集按治超站统计车货总重大于 100 吨超限量
     *
     * @return
     */
    @GetMapping("/totalWeightOver100")
    R totalWeightOver100Collect();

    /**
     * 采集货车超重分布数据
     *
     * @return
     */
    @GetMapping("/truckOW")
    R truckOWCollect();
}
