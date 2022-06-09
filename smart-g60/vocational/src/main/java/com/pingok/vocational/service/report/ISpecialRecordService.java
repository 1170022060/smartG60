package com.pingok.vocational.service.report;

import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.domain.report.vo.SpecialRecordVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 特情记录 服务层
 *
 * @author ruoyi
 */
public interface ISpecialRecordService {
    /**
     * 通过起止告警时间、站ID或车道ID查询特情记录
     *
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @param stationId 站ID
     * @param laneId 车道ID
     * @return 特情记录
     */
    List<Map> selectSpecialRecord(Date startTime, Date endTime,String stationId,String laneId);

    /**
     * 通过起止日期查询特情统计
     *
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 特情统计
     */
    List<Map> selectSpecialStatistic(Date startDate, Date endDate);

    /**
     * 通过起止日期查询特情统计
     *
     * @param reportVo
     * @return 特情统计
     */
    List<SpecialRecordVo> selectSpecialStatisticList(ReportVo reportVo);
}
