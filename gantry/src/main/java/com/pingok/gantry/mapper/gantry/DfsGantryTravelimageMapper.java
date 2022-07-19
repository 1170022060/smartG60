package com.pingok.gantry.mapper.gantry;

import com.pingok.gantry.domain.entity.gantry.DfsGantryTravelimage;
import com.ruoyi.common.core.mapper.CommonRepository;

import java.util.Date;
import java.util.List;

/**
 *
 */
public interface DfsGantryTravelimageMapper extends CommonRepository<DfsGantryTravelimage> {
    List<DfsGantryTravelimage> findPicTime(Date startTime, Date endTime);
}
