package com.pingok.vocational.service.report;


import com.pingok.vocational.domain.report.vo.OdRecordStaVo;
import com.pingok.vocational.domain.report.vo.OdRecordVelVo;
import com.pingok.vocational.domain.report.vo.ReportVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 车辆OD统计记录 服务层
 *
 * @author ruoyi
 */
public interface IOdRecordService {
    /**
     * 通过收费站、车型、起止日期、小时查询车辆OD统计
     *
     * @param stationId 收费站ID
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param hour 小时
     * @return 车辆OD统计
     */
    List<Map> selectOdRecordBySta(String stationId, Date startDate, Date endDate, Integer hour);

    /**
     * 通过收费站、车型、起止日期、小时查询车辆OD统计
     *
     * @param reportVo
     * @return 车辆OD统计
     */
    List<OdRecordStaVo> selectOdRecordByStaList(ReportVo reportVo);

    /**
     * 通过车型、起止日期、小时查询车辆OD统计
     *
     * @param vehClass 车型
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param hour 小时
     * @return 车辆OD统计
     */
    List<Map> selectOdRecordByClass( Integer vehClass, Date startDate, Date endDate, Integer hour);

    /**
     * 通过车型、起止日期、小时查询车辆OD统计
     *
     * @param reportVo
     * @return 车辆OD统计
     */
    List<OdRecordVelVo> selectOdRecordByClassList(ReportVo reportVo);
}
