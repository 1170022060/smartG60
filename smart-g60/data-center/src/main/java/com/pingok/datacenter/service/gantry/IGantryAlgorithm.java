package com.pingok.datacenter.service.gantry;

/**
 * 门架流水 业务层
 *
 * @author ruoyi
 */

import com.pingok.datacenter.domain.gantry.TblGantryChargeInfo;
import com.pingok.datacenter.domain.gantry.model.ChargeFlowModel;

import java.util.List;
import java.util.Map;

public interface IGantryAlgorithm {

    /**
     * 通过起止时间查询算法所需数据
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 结果
     */
    List<Map> selectGantryAlgorithm(String startTime, String endTime);

    /**
     * 根据上下行获取对应门架集合
     *
     * @param direction 上下行
     * @return 结果
     */
    List<Map> selectGantryAlgorithmList(Integer direction);

    /**
     * 根据门架编号、开始时间、结束时间、查询门架过车记录
     *
     * @param gantryId 门架编号
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 结果
     */
    List<Map> selectGantryAlgorithmPassRecord(String gantryId,String startTime, String endTime);

    /**
     * 获取收费单元信息（含理论日收益预测因子）
     */
    List<TblGantryChargeInfo> selectChargeInfo(String chargingUnitId);

    /**
     * 获取收费单元流量
     */
    List<ChargeFlowModel> selectChargeFlowList(String startDate, String endDate);

    ChargeFlowModel selectChargeFlow(String chargingUnitId, String statisticsDate);
}
