package com.pingok.gantry.mapper.gantry;

import com.pingok.gantry.domain.entity.gantry.DfsGantryMonitorStatus;
import com.ruoyi.common.core.mapper.CommonRepository;

import java.util.List;

/**
 *
 */
public interface DfsGantryMonitorStatusMapper extends CommonRepository<DfsGantryMonitorStatus> {
    List<DfsGantryMonitorStatus> findByStateTime(String time);
}
