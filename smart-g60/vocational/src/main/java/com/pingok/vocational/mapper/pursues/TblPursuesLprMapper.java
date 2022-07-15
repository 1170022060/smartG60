package com.pingok.vocational.mapper.pursues;

import com.pingok.vocational.domain.pursues.TblLprSummary;
import com.pingok.vocational.domain.pursues.TblPursuesLpr;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TblPursuesLprMapper extends CommonRepository<TblPursuesLpr> {

    @Select({"<script>" +
            "select " +
            "EX_LANE_GB as \"exLaneGb\" , " +
            "EX_VEH_PLATE as \"exVehPlate\" , " +
            "EX_TRANS_TIME as \"exTransTime\" from TBL_LPR_SUMMARY " +
            "where EX_VEH_PLATE= #{vehPlate} " +
            "and EX_TRANS_TIME &gt;= #{time} and " +
            "EX_TRANS_TIME &lt;= #{time}+2/24 and rownum=1 " +
            "order by EX_TRANS_TIME " +
            "</script>"})
    TblLprSummary selectLprByPlate(@Param("vehPlate") String vehPlate, @Param("time") Date time);

    @Select({"<script>" +
            "select " +
            "EX_VEH_PLATE as \"exVehPlate\" , " +
            "EX_TRANS_TIME as \"exTransTime\" from TBL_LPR_SUMMARY " +
            "where EX_LANE_GB= #{exLaneGb} " +
            "and EX_TRANS_TIME &gt;= #{time} and " +
            "EX_TRANS_TIME &lt;= #{time}+2/24 and rownum=1 " +
            "order by EX_TRANS_TIME " +
            "</script>"})
    List<TblLprSummary> selectLprByLaneGb(@Param("exLaneGb") String exLaneGb, @Param("time") Date time);

    @Select({"<script>" +
            "select " +
            "VEH_PLATE as \"vehPlate\" , " +
            "to_char(TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"transTime\" from TBL_PURSUES_LPR " +
            "where 1=1 " +
            "<when test='concertId != null'> " +
            "and CONCERT_ID = #{concertId} " +
            "</when>" +
            "order by TRANS_TIME "+
            "</script>"})
    List<Map> selectPursuesLpr(@Param("concertId") String concertId);

}
