package com.pingok.vocational.service.roster;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 抢险救灾名单 业务层
 *
 * @author ruoyi
 */
public interface IRescueListRecordService {
    /**
     * 通过车牌、行程时间查询抢险救灾名单
     *
     * @param vehPlate 车牌
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 抢险救灾名单
     */
    List<Map> selectRescueList(String vehPlate, Date startTime, Date endTime);
}
