package com.pingok.vocational.service.business;

import com.pingok.vocational.domain.business.vo.LprSummaryVo;
import com.pingok.vocational.domain.business.vo.SummaryVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 车道牌识 服务层
 *
 * @author ruoyi
 */
public interface ILprSummaryService {
    /**
     * 通过出入口起止时间、出入口收费站编码、出入口车道类型、出入口车牌号查询车道牌识
     *
     * @param enStartTime 入口开始时间
     * @param enEndTime 入口结束时间
     * @param enStationId 入口收费站编码
     * @param enLaneType 入口车道类型
     * @param enVehPlate 入口车牌
     * @param exStartTime 出口开始时间
     * @param exEndTime 出口结束时间
     * @param exStationId 出口收费站编码
     * @param exLaneType 出口车道类型
     * @param exVehPlate 出口车牌号
     * @return 车道牌识
     */
    List<Map> selectLprTrans(Date enStartTime,Date enEndTime, String enStationId, Integer enLaneType, String enVehPlate, Date exStartTime, Date exEndTime, String exStationId, Integer exLaneType, String exVehPlate);

    /**
     * 通过入口起止时间、入口收费站编码、入口车道类型、入口车牌号查询车道牌识
     *
     * @param enStartTime 入口开始时间
     * @param enEndTime 入口结束时间
     * @param enStationId 入口收费站编码
     * @param enLaneType 入口车道类型
     * @param enVehPlate 入口车牌
     * @return 车道牌识
     */
    List<Map> selectEnLprTrans(Date enStartTime,Date enEndTime, String enStationId, Integer enLaneType, String enVehPlate);

    /**
     * 通过出口起止时间、出口收费站编码、出口车道类型、出口车牌号查询车道牌识
     *
     * @param exStartTime 出口开始时间
     * @param exEndTime 出口结束时间
     * @param exStationId 出口收费站编码
     * @param exLaneType 出口车道类型
     * @param exVehPlate 出口车牌号
     * @return 车道牌识
     */
    List<Map> selectExLprTrans(Date exStartTime, Date exEndTime, String exStationId, Integer exLaneType, String exVehPlate);

    /**
     * 通过出入口起止时间、出入口收费站编码、出入口车道类型、出入口车牌号查询车道牌识
     *
     * @param summaryVo
     * @return 车道牌识
     */
    List<LprSummaryVo> selectLprTransList(SummaryVo summaryVo);
}
