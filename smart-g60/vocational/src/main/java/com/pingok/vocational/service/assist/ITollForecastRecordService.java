package com.pingok.vocational.service.assist;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 通行费预测记录表 服务层
 *
 * @author ruoyi
 */
public interface ITollForecastRecordService {

    /**
     * 通过预测方式、起止日期查询通行费预测记录
     *
     * @param forecastType 预测方式
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 通行费预测记录
     */
    List<Map> selectTollForecast(Integer forecastType,Date startDate, Date endDate);
}
