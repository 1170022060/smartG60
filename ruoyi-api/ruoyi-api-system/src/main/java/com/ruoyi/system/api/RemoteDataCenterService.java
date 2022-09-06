package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.gantry.ChargeFlowModel;
import com.ruoyi.system.api.domain.gantry.TblGantryChargeInfo;
import com.ruoyi.system.api.factory.RemoteDataCenterFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 数据中心服务
 *
 * @author xia
 */
@FeignClient(contextId = "remoteDataCenterService", value = ServiceNameConstants.DATA_CENTER_SERVICE, fallbackFactory = RemoteDataCenterFallbackFactory.class)
public interface RemoteDataCenterService {


    /**
     * 获取收费区间流量
     *
     * @return
     */
    @GetMapping("/gantryAlgorithm/selectChargeFlow")
    R<ChargeFlowModel> selectChargeFlow(@RequestParam(name = "chargingUnitId") String chargingUnitId,
                                        @RequestParam(name = "statisticsDate") String statisticsDate);

    /**
     * 获取收费区间流量列表
     *
     * @return
     */
    @GetMapping("/gantryAlgorithm/selectChargeFlowList")
    R<List<ChargeFlowModel>> selectChargeFlowList(@RequestParam(name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate);

    /**
     * 获取收费区间数据
     *
     * @return
     */
    @GetMapping("/gantryAlgorithm/chargeInfo")
    R<List<TblGantryChargeInfo>> chargeInfo(@RequestParam(name = "chargingUnitId", required = false) String chargingUnitId);

    /**
     * 获取门架算法参数接口
     *
     * @return
     */
    @GetMapping("/gantryAlgorithm/info")
    R<List<Map>> info(@RequestParam(name = "startTime", required = false) String startTime, @RequestParam(name = "endTime", required = false) String endTime);

    /**
     * 根据上下行获取对应门架集合接口
     *
     * @return
     */
    @GetMapping("/gantryAlgorithm/gantryList")
    R<List<Map>> gantryList(@RequestParam(name = "direction", required = false) Integer direction);

    /**
     * 根据门架编号、开始时间、结束时间、查询门架过车记录接口
     *
     * @return
     */
    @GetMapping("/gantryAlgorithm/passRecord")
    R<List<Map>> passRecord(@RequestParam(name = "gantryId", required = false) String gantryId, @RequestParam(name = "startTime", required = false) String startTime, @RequestParam(name = "endTime", required = false) String endTime);
}
