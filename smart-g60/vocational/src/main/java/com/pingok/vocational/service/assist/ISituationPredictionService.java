package com.pingok.vocational.service.assist;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 路段运行态势预估表 服务层
 *
 * @author ruoyi
 */
public interface ISituationPredictionService {
    /**
     * 通过起止日期查询交通流量预估
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 交通流量预估
     */
    List<Map> selectSituationFlow(Date startDate, Date endDate);

    /**
     * 通过起止日期查询交通状态预估
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 交通状态预估
     */
    List<Map> selectSituationStatus(Date startDate, Date endDate);
}
