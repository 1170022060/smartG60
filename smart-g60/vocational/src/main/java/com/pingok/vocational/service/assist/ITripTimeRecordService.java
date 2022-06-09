package com.pingok.vocational.service.assist;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 行程时间预测记录 服务层
 *
 * @author ruoyi
 */
public interface ITripTimeRecordService {
    /**
     * 通过起止日期查询行程时间预测记录
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 行程时间预测记录
     */
    List<Map> selectTripTime(Date startDate, Date endDate);
}
