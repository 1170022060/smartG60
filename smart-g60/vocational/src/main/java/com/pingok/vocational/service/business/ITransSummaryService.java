package com.pingok.vocational.service.business;

import com.pingok.vocational.domain.business.vo.SummaryVo;
import com.pingok.vocational.domain.business.vo.TransSummaryVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 流水汇总 服务层
 *
 * @author ruoyi
 */
public interface ITransSummaryService {

    /**
     * 通过出入口起止时间、出入口工班日、出入口收费站编码、passId、出入口Gid、出入口通行方式
     * 出入口工班号、出入口车牌、出入口CPC/ETC卡ID、支付方式查询流水汇总信息
     *
     * @param enStartTime 入口开始时间
     * @param enEndTime 入口结束时间
     * @param enWorkDate 入口工班日
     * @param enStationId 入口收费站编码
     * @param passId passId
     * @param enGid 入口Gid
     * @param enPassType 入口通行方式
     * @param enShift 入口工班号
     * @param enVehPlate 入口车牌
     * @param enCardId 入口CPC/ETC卡ID
     * @param exStartTime 出口开始时间
     * @param exEndTime 出口结束时间
     * @param exWorkDate 出口工班日
     * @param exStationId 出口收费站编码
     * @param exGid 出口Gid
     * @param exPassType 出口通行方式
     * @param exShift 出口工班号
     * @param exVehPlate 出口车牌
     * @param exCardId 出口CPC/ETC卡ID
     * @param payWay 支付方式
     * @return 流水汇总信息
     */
    List<Map> selectTransSummary(Date enStartTime, Date enEndTime,
                                Date enWorkDate, String enStationId,
                                String passId, String enGid,
                                Integer enPassType, Integer enShift,
                                String enVehPlate, String enCardId,
                                Date exStartTime, Date exEndTime,
                                Date exWorkDate, String exStationId, String exGid,
                                Integer exPassType, Integer exShift,
                                String exVehPlate, String exCardId,
                                Integer payWay);

    /**
     * 通过入口起止时间、入口工班日、入口收费站编码、passId、入口Gid、入口通行方式
     * 入口工班号、入口车牌、入口CPC/ETC卡ID查询流水汇总信息
     *
     * @param enStartTime 入口开始时间
     * @param enEndTime 入口结束时间
     * @param enWorkDate 入口工班日
     * @param enStationId 入口收费站编码
     * @param passId passId
     * @param enGid 入口Gid
     * @param enPassType 入口通行方式
     * @param enShift 入口工班号
     * @param enVehPlate 入口车牌
     * @param enCardId 入口CPC/ETC卡ID
     * @return 流水汇总信息
     */
    List<Map> selectEnTransSummary(Date enStartTime, Date enEndTime,
                                 Date enWorkDate, String enStationId,
                                 String passId, String enGid,
                                 Integer enPassType, Integer enShift,
                                 String enVehPlate, String enCardId);

    /**
     * 通过出口起止时间、出口工班日、出口收费站编码、passId、出口Gid、出口通行方式
     * 出口工班号、出口车牌、出口CPC/ETC卡ID、支付方式查询流水汇总信息
     *
     * @param passId passId
     * @param exStartTime 出口开始时间
     * @param exEndTime 出口结束时间
     * @param exWorkDate 出口工班日
     * @param exStationId 出口收费站编码
     * @param exGid 出口Gid
     * @param exPassType 出口通行方式
     * @param exShift 出口工班号
     * @param exVehPlate 出口车牌
     * @param exCardId 出口CPC/ETC卡ID
     * @param payWay 支付方式
     * @return 流水汇总信息
     */
    List<Map> selectExTransSummary(String passId,
                                 Date exStartTime, Date exEndTime,
                                 Date exWorkDate, String exStationId, String exGid,
                                 Integer exPassType, Integer exShift,
                                 String exVehPlate, String exCardId,
                                 Integer payWay);
    /**
     * 通过出入口起止时间、出入口工班日、出入口收费站编码、passId、出入口Gid、出入口通行方式
     * 出入口工班号、出入口车牌、出入口CPC/ETC卡ID、支付方式查询流水汇总信息
     *
     * @param summaryVo
     * @return 流水汇总信息
     */
    List<TransSummaryVo> selectTransSummaryList(SummaryVo summaryVo);
}
