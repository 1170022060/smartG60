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
     * 门架ETC本地流量统计（省界入口、大港、新桥）
     * @return
     */
    @Select({"<script>" +
            "SELECT COUNT(*) FROM " +
            "TBL_SHAR_GTD_RES_SENDER_${year} a " +
            "WHERE a.TRANS_TIME <![CDATA[ >= ]]> startTime AND a.TRANS_TIME <![CDATA[ <= ]]> endTime  " +
            "AND GANTRY_ID in #{gantryIds} AND TRADE_RESULT = 0 AND CPU_NET_ID like '31%' and MEDIA_TYPE = 1 " +
            "</script>"})
    public int selectGantryFlowLocal(@Param("year")String year, @Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("gantryIds")String gantryIds);

    /**
     * 门架ETC异地流量统计（省界入口、大港、新桥）
     * @return
     */
    @Select({"<script>" +
            "SELECT COUNT(*) FROM " +
            "TBL_SHAR_GTD_RES_SENDER_${year} a " +
            "WHERE a.TRANS_TIME <![CDATA[ >= ]]> startTime AND a.TRANS_TIME <![CDATA[ <= ]]> endTime  " +
            "AND GANTRY_ID in #{gantryIds} AND TRADE_RESULT = 0 AND CPU_NET_ID not like '31%' and MEDIA_TYPE = 1 " +
            "</script>"})
    public int selectGantryFlowLOther(@Param("year")String year, @Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("gantryIds")String gantryIds);
    /**
     * 门架MTC单省流量统计（省界入口、大港、新桥）
     * @return
     */
    @Select({"<script>" +
            "SELECT COUNT(*) FROM " +
            "TBL_SHAR_GTD_RES_SENDER_${year} a " +
            "WHERE a.TRANS_TIME <![CDATA[ >= ]]> startTime AND a.TRANS_TIME <![CDATA[ <= ]]> endTime  " +
            "AND GANTRY_ID in #{gantryIds} AND TRADE_RESULT = 0 AND PROVINCE_NUM_AFTER =1 and MEDIA_TYPE = 2 " +
            "</script>"})
    public int selectGantryFlowSingle(@Param("year")String year, @Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("gantryIds")String gantryIds);

    /**
     * 门架MTC跨省流量统计（省界入口、大港、新桥）
     * @return
     */
    @Select({"<script>" +
            "SELECT COUNT(*) FROM " +
            "TBL_SHAR_GTD_RES_SENDER_${year} a " +
            "WHERE a.TRANS_TIME <![CDATA[ >= ]]> startTime AND a.TRANS_TIME <![CDATA[ <= ]]> endTime  " +
            "AND GANTRY_ID in #{gantryIds} AND TRADE_RESULT = 0 AND PROVINCE_NUM_AFTER >1 and MEDIA_TYPE = 2 " +
            "</script>"})
    public int selectGantryFlow(@Param("year")String year, @Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("gantryIds")String gantryIds);
}
