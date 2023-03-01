package com.pingok.vocational.service.report;


import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.domain.report.vo.SectionRecordVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 出入口段面记录 服务层
 *
 * @author ruoyi
 */
public interface ISectionRecordService {
    /**
     * 通过收费站、起止日期、出入口查询出入口段面记录
     *
     * @param stationId 收费站ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param direction 出入口
     * @return 出入口段面记录
     */
    List<Map> selectSectionRecord(String stationId, Date startDate, Date endDate, Integer direction);

    /**
     * 通过收费站、起止日期、出入口查询出入口段面记录
     *
     * @param reportVo 收费站ID
     * @return 出入口段面记录
     */
    List<SectionRecordVo> selectSectionRecordList(ReportVo reportVo);

    /**
     * 出入口断面流量统计
     * @param stationId
     * @param startDate
     * @param endDate
     * @param direction
     * @return
     */
    List<Map> selectEnAnExFlow(String stationId, Date startDate, Date endDate, Integer direction);
}
