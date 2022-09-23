package com.pingok.gantry.mapper.gantry;

import com.pingok.gantry.domain.entity.gantry.DfsGantryRsuMonitor;
import com.ruoyi.common.core.mapper.CommonRepository;

import java.util.List;

/**
 *
 */
public interface DfsGantryRsuMonitorMapper extends CommonRepository<DfsGantryRsuMonitor> {
    List<DfsGantryRsuMonitor> findByCreateTime(String time);
}
