package com.pingok.gantry.mapper.charge;

import com.pingok.gantry.domain.entity.charge.TblGantryVplrBaseinfo;
import com.ruoyi.common.core.mapper.CommonRepository;

/**
 * 用户仓库
 */
public interface TblGantryVplrBaseinfoMapper extends CommonRepository<TblGantryVplrBaseinfo> {
    TblGantryVplrBaseinfo findByGantryIdAndPicBaseId(String gantryId,String picBaseId);
}
