package com.pingok.devicemonitor.mapper.gantry;

import com.pingok.devicemonitor.domain.gantry.TblGantryTransaction;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TblGantryTransactionMapper extends CommonRepository<TblGantryTransaction> {

    int addtblGantryTransaction(TblGantryTransaction tblGantryTransaction);
}
