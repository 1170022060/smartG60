package com.pingok.gantry.service.gantry110;

import com.pingok.gantry.domain.entity.gantry.*;

import java.util.Date;
import java.util.List;

public interface IGantry410Service {
    List<DfsGantryBaseinfo> findByStateTime(String startTime, String endTime);

    List<DfsGantryRsuBaseinfo> findByCreateTime(String startTime, String endTime);

    List<DfsGantryVplrBaseinfo> findVplrByCreateTime(String startTime, String endTime);

    List<DfsGantryMonitorStatus> findGantryMonitor(String time);

    List<DfsGantryRsuMonitor> findRsuMonitor(String time);

    List<DfsGantryVplrMonitor> findVplrMonitor(String time);

    List<DfsGantryTransaction> findTransactionByTransTime(Date startTime, Date endTime);

    List<DfsGantryTravelimage> findTravelImageByPicTime(Date startTime, Date endTime);

    DfsGantryTransaction findTransactionById(String tradeId);

    DfsGantryTravelimage findTravelImageById(String picId);
}