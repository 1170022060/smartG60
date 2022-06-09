package com.pingok.monitor.service.record;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 门架断面流量 业务层
 *
 * @author qiumin
 */
public interface IRecordService {

    List<Map> getGantryRecord(String startTime, String endTime);

    List<Map> getSectionRecord(String startTime, String endTime);
}
