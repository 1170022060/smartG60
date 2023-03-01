package com.pingok.vocational.service.report;

import com.pingok.vocational.domain.report.vo.GantryRecordVo;
import com.pingok.vocational.domain.report.vo.ReportVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 门架段面记录表 服务层
 *
 * @author ruoyi
 */
public interface IGantryRecordService {
    /**
     * 通过门架、起止日期、出入口查询门架段面记录
     *
     * @param gantryId 门架ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 门架段面记录
     */
    List<Map> selectGantryRecord(String gantryId, Date startDate, Date endDate);
    /**
     * 通过门架、起止日期、出入口查询门架段面记录
     *
     * @param reportVo
     * @return 门架段面记录
     */
    List<GantryRecordVo> selectGantryRecordList(ReportVo reportVo);

    /**
     * 获取门架断面流量统计数据
     * @param gantryId
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map> selectGantryFlow(String gantryId,Date startDate,Date endDate);
}
