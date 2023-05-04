package com.pingok.vocational.service.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IVdHistoryRecordService {

    /**
     * 车检器流量统计
     * @param deviceName
     * @param statisticsType
     * @param startDate
     * @param endTime
     * @return
     */
    List<Map> selectVdHistory(String deviceName, Integer statisticsType, Date startDate,Date endTime);

    List<Map> selectPileNo();

    int getTotal(String deviceName, Integer statisticsType, Date startDate,Date endTime);
}
