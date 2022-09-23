package com.pingok.datacenter.service.simulatedSorting;

import com.pingok.datacenter.domain.simulatedSorting.vo.SimulatedSortingVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 模拟清分 业务层
 *
 * @author ruoyi
 */
public interface ISimulatedSortingService {

    /**
     * 模拟清分日统计表
     * @param startTime
     * @param endTime
     * @return
     */
    List<SimulatedSortingVo> dayStatistics(String startTime,String endTime);
    /**
     * 抓取清分数据
     * @param year
     * @param startTime
     * @param endTime
     * @return
     */
    void simulatedSorting(String year, String startTime, String endTime);
}
