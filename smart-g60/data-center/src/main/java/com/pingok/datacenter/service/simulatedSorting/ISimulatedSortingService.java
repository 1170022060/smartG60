package com.pingok.datacenter.service.simulatedSorting;

import java.util.Date;

/**
 * 模拟清分 业务层
 *
 * @author ruoyi
 */
public interface ISimulatedSortingService {
    /**
     * 抓取清分数据
     * @param year
     * @param startTime
     * @param endTime
     * @return
     */
    void simulatedSorting(String year, String startTime, String endTime);
}
