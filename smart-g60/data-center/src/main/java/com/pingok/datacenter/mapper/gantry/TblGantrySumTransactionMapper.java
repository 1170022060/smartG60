package com.pingok.datacenter.mapper.gantry;

import com.pingok.datacenter.domain.gantry.TblGantrySumTransaction;
import com.pingok.datacenter.domain.gantry.model.GantryFlowModel;
import com.pingok.datacenter.domain.trans.vo.ExTranFlow;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

@Mapper
public interface TblGantrySumTransactionMapper extends CommonRepository<TblGantrySumTransaction> {
    /**
     * 查询指定的门架流量之和
     *
     * @return 结果
     */
    public int selectGantryFlow(GantryFlowModel gantryFlowModel);

    /**
     * 门架本地ETC流量统计（省界入口、大港、新桥）
     * @return
     */
    @Select({"<script>" +
            "SELECT COUNT(*) FROM " +
            "TBL_SHAR_GTD_RES_SENDER_${year} a " +
            "WHERE a.TRANS_TIME <![CDATA[ >= ]]> startTime AND a.TRANS_TIME <![CDATA[ <= ]]> endTime  " +
            "AND GANTRY_ID in #{gantryIds} AND TRADE_RESULT = 0 AND PROVINCE_NUM_AFTER =1 " +
            "</script>"})
    public int selectGantryFlow(@Param("year")String year, @Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("gantryIds")String gantryIds);
}
