package com.pingok.gantry.mapper.gantry;

import com.pingok.gantry.domain.entity.gantry.DfsGantryRsuBaseinfo;
import com.ruoyi.common.core.mapper.CommonRepository;

import java.util.List;

/**
 *
 */
public interface DfsGantryRsuBaseinfoMapper extends CommonRepository<DfsGantryRsuBaseinfo> {
    List<DfsGantryRsuBaseinfo> findByCreateTime(String startTime,String endTime);
}
