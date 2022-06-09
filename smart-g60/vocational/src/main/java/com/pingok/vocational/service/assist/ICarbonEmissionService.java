package com.pingok.vocational.service.assist;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 碳排放统计表 服务层
 *
 * @author ruoyi
 */
public interface ICarbonEmissionService {

    /**
     * 通过起止日期查询碳排放
     *
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @return 碳排放
     */
    List<Map> selectCarbonEmission(Date startDate, Date endDate);
}
