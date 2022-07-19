package com.pingok.gantry.mapper.charge;

import com.pingok.gantry.domain.entity.charge.TblGantryBaseinfo;
import com.ruoyi.common.core.mapper.CommonRepository;

/**
 *
 */
public interface TblGantryBaseinfoMapper extends CommonRepository<TblGantryBaseinfo> {
    TblGantryBaseinfo findByGantryIdAndComputerOrder(String gantryId,Integer computerOrder);
}
