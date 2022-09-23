package com.pingok.gantry.mapper.charge;

import com.pingok.gantry.domain.entity.charge.TblGantryTransaction;
import com.ruoyi.common.core.mapper.CommonRepository;

import java.util.List;

/**
 *
 */
public interface TblGantryTransactionMapper extends CommonRepository<TblGantryTransaction> {
    List<TblGantryTransaction> findTransactionUploadError(List<String> gantryId);
}
