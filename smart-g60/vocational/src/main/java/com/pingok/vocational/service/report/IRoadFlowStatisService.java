package com.pingok.vocational.service.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IRoadFlowStatisService {

    /**
     * 路段流量流速计算
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map> roadFlowStatis(Date startDate,Date endDate);
}
