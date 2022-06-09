package com.pingok.vocational.service.business;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 操作员工班信息 服务层
 *
 * @author ruoyi
 */
public interface IOptWorkInfoService {

    /**
     * 通过起止日期、收费站编码、员工姓名、工班号查询排班当岗信息
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param stationId 收费站编码
     * @param optName 员工姓名
     * @param shift 工班号
     * @return 排班当岗信息
     */
    List<Map> selectOptWorkInfo( Date startDate, Date endDate, String stationId, String optName, Integer shift);

}
