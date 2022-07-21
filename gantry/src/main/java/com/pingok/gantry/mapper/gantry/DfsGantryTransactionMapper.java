package com.pingok.gantry.mapper.gantry;

import com.pingok.gantry.domain.entity.gantry.DfsGantryTransaction;
import com.ruoyi.common.core.mapper.CommonRepository;

import java.util.Date;
import java.util.List;

/**
 *
 */
public interface DfsGantryTransactionMapper extends CommonRepository<DfsGantryTransaction> {
    List<DfsGantryTransaction> findTransTime(Date startTime, Date endTime);
}
