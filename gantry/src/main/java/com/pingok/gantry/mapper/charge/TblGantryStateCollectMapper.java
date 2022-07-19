package com.pingok.gantry.mapper.charge;

import com.pingok.gantry.domain.entity.charge.TblGantryStateCollect;
import com.ruoyi.common.core.mapper.CommonRepository;

import java.util.List;

/**
 *
 */
public interface TblGantryStateCollectMapper extends CommonRepository<TblGantryStateCollect> {

    List<TblGantryStateCollect> findByGantryId(String gantryId);

    TblGantryStateCollect findStateByGantryId(String gantryId);
}
