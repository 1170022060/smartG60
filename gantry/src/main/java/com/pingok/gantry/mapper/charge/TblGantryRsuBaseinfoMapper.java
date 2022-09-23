package com.pingok.gantry.mapper.charge;

import com.pingok.gantry.domain.entity.charge.TblGantryRsuBaseinfo;
import com.ruoyi.common.core.mapper.CommonRepository;

/**
 * 用户仓库
 */
public interface TblGantryRsuBaseinfoMapper extends CommonRepository<TblGantryRsuBaseinfo> {
    TblGantryRsuBaseinfo findByGantryIdAndControlIdAndStateVersion(String gantryId,String controlId,String stateVersion);
}
