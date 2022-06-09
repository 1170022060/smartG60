package com.pingok.vocational.service.report;

import com.pingok.vocational.domain.report.TblRoadTripTime;
import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.domain.report.vo.RoadTripTimeVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 路段行程时间 服务层
 *
 * @author ruoyi
 */
public interface IRoadTripTimeService {
    /**
     * 通过起止日期、小时查询路段行程时间
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param hour 小时
     * @return 路段行程时间
     */
    List<Map> selectRoadTripTime(Integer hour, Date startDate, Date endDate);

    /**
     * 通过起止日期、小时查询路段行程时间
     *
     * @param reportVo
     * @return 路段行程时间
     */
    List<RoadTripTimeVo> selectRoadTripTimeList(ReportVo reportVo);

    /**
     * 上一个小时的平均路段行程时间统计
     *
     * @return 上一个小时的平均路段行程时间统计
     */
    List<TblRoadTripTime> selectTripTimeHour();

    /**
     * 新增平均路段行程时间统计
     *
     * @return 结果
     */
    public int insertTripTimeHour();

}
