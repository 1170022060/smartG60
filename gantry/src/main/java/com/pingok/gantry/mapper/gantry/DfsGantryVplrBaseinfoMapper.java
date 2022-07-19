package com.pingok.gantry.mapper.gantry;

import com.pingok.gantry.domain.entity.gantry.DfsGantryVplrBaseinfo;
import com.ruoyi.common.core.mapper.CommonRepository;

import java.util.List;

/**
 *
 */
public interface DfsGantryVplrBaseinfoMapper extends CommonRepository<DfsGantryVplrBaseinfo> {
    List<DfsGantryVplrBaseinfo> findByCreateTime(String startTime,String endTime);
}
