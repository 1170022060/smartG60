package com.pingok.gantry.mapper.gantry;

import com.pingok.gantry.domain.entity.gantry.DfsGantryVplrMonitor;
import com.ruoyi.common.core.mapper.CommonRepository;

import java.util.List;

/**
 *
 */
public interface DfsGantryVplrMonitorMapper extends CommonRepository<DfsGantryVplrMonitor> {
    List<DfsGantryVplrMonitor> findByStateTime(String time);
}
