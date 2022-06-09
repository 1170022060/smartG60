package com.pingok.vocational.service.report;

import com.pingok.vocational.domain.report.vo.EventRecordClassVo;
import com.pingok.vocational.domain.report.vo.EventRecordSiteVo;
import com.pingok.vocational.domain.report.vo.EventRecordTypeVo;
import com.pingok.vocational.domain.report.vo.ReportVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 事件记录表 服务层
 *
 * @author ruoyi
 */
public interface IEventRecordService {
    /**
     * 通过事件类型、起止时间统计事件记录
     *
     * @param eventType 事件类型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 事件记录统计
     */
    List<Map> selectEventRecordByType(String eventType, Date startTime, Date endTime);

    /**
     * 通过事件类型、起止时间统计事件记录
     *
     * @param reportVo
     * @return 事件记录统计
     */
    List<EventRecordTypeVo> selectEventRecordByTypeList(ReportVo reportVo);

    /**
     * 通过位置区间、起止时间统计事件记录
     *
     * @param locationInterval 位置区间
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 事件记录统计
     */
    List<Map> selectEventRecordBySite(String locationInterval, Date startTime, Date endTime);

    /**
     * 通过位置区间、起止时间统计事件记录
     *
     * @param reportVo
     * @return 事件记录统计
     */
    List<EventRecordSiteVo> selectEventRecordBySiteList(ReportVo reportVo);

    /**
     * 通过车型、起止时间统计事件记录
     *
     * @param vehClass 车型
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 事件记录统计
     */
    List<Map> selectEventRecordByClass(Integer vehClass, Date startTime, Date endTime);

    /**
     * 通过车型、起止时间统计事件记录
     *
     * @param reportVo
     * @return 事件记录统计
     */
    List<EventRecordClassVo> selectEventRecordByClassList(ReportVo reportVo);
}
