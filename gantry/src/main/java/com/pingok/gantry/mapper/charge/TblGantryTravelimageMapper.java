package com.pingok.gantry.mapper.charge;

import com.pingok.gantry.domain.entity.charge.TblGantryTravelimage;
import com.ruoyi.common.core.mapper.CommonRepository;

import java.util.List;

/**
 *
 */
public interface TblGantryTravelimageMapper extends CommonRepository<TblGantryTravelimage> {
    List<TblGantryTravelimage> findTravelimageUploadError(List<String> gantryId);
}
