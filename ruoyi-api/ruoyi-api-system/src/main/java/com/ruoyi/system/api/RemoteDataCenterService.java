package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.gantry.ChargeFlowModel;
import com.ruoyi.system.api.domain.gantry.TblGantryChargeInfo;
import com.ruoyi.system.api.factory.RemoteDataCenterFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
     * 闯关分拣
     * @param year
     * @param startTime
     * @param endTime
     * @param twoHours
     * @return
     */
    @PostMapping("/rush")
    R rushRecord(@RequestParam(value = "year") String year, @RequestParam(value = "startTime") String startTime, @RequestParam(value = "endTime") String endTime, @RequestParam(value = "twoHours") String twoHours);

    /**
     * 模拟清分数据统计
     * @param year
     * @param startTime
     * @param endTime
     * @return
     */
    @PostMapping("/simulatedSorting")
    R simulatedSorting(@RequestParam(value = "year") String year, @RequestParam(value = "startTime") String startTime, @RequestParam(value = "endTime") String endTime);

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
    R<List<ChargeFlowModel>> selectChargeFlowList(@RequestParam(name = "chargingUnitId") String chargingUnitId,
                                                  @RequestParam(name = "startDate") String startDate,
                                                  @RequestParam(name = "endDate") String endDate);

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
    R<List<Map>> info(@RequestParam(name = "startTime",required = false) String startTime, @RequestParam(name = "endTime",required = false) String endTime);

    /**
     * 根据上下行获取对应门架集合接口
     *
     * @return
     */
    @GetMapping("/gantryAlgorithm/gantryList")
    R<List<Map>> gantryList(@RequestParam(name = "direction",required = false) Integer direction);

    /**
     * 根据门架编号、开始时间、结束时间、查询门架过车记录接口
     *
     * @return
     */
    @GetMapping("/gantryAlgorithm/passRecord")
    R<List<Map>> passRecord(@RequestParam(name = "gantryId",required = false) String gantryId,@RequestParam(name = "startTime",required = false) String startTime, @RequestParam(name = "endTime",required = false) String endTime);

    /**
     * 状态名单增量
     * @return
     */
    @PostMapping("/blackcard/blackIncr")
    R blackIncr();

    /**
     * 中高风险名单
     * @return
     */
    @PostMapping("/epidemic/epidemicDownload")
    R epidemicDownload();

    /**
     * 中高风险车牌前缀名单
     * @return
     */
    @PostMapping("/epidemic/prefixDownload")
    R prefixDownload();

    /**
     * 最小费率名单
     * @return
     */
    @PostMapping("/rate/rateDownload")
    R rateDownload();

    /**
     * 追缴名单增量
     * @return
     */
    @PostMapping("/recovery/recoveryIncr")
    R recoveryIncr();

    /**
     * 抢险救灾名单增量
     * @return
     */
    @PostMapping("/rescue/rescueIncr")
    R rescueIncr();

    /**
     * 绿通名单
     * @return
     */
    @PostMapping("/green/greenDownload")
    R greenDownload();
}
