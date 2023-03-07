package com.pingok.datacenter.mapper.gantry;

import com.pingok.datacenter.domain.gantry.TblGantrySumTransaction;
import com.pingok.datacenter.domain.gantry.model.GantryFlowModel;
import com.pingok.datacenter.domain.trans.vo.ExTranFlow;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TblGantrySumTransactionMapper extends CommonRepository<TblGantrySumTransaction> {
    /**
     * 查询指定的门架流量之和
     *
     * @return 结果
     */
    public int selectGantryFlow(GantryFlowModel gantryFlowModel);
}
