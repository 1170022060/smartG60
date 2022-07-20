package com.pingok.vocational.mapper.pursues;

import com.pingok.vocational.domain.pursues.TblPursuesTrans;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TblPursuesTransMapper extends CommonRepository<TblPursuesTrans> {

    @Select("select " +
            "EN_VEH_PLATE as \"enVehPlate\" , " +
            "EN_VEH_COLOR as \"enVehColor\" , " +
            "EN_VEH_CLASS as \"enVehClass\" , " +
            "EN_LANE_HEX as \"enLaneHex\" , " +
            "EN_TRANS_TIME as \"enTransTime\" , " +
            "EN_PASS_TYPE as \"enPassType\" , " +
            "EN_CARD_ID as \"enCardId\" , " +
            "EN_GID as \"enGId\" , " +
            "EN_VEH_STATUS as \"enVehStatus\" , " +
            "EX_LANE_HEX as \"exLaneHex\" , " +
            "EX_VEH_PLATE as \"exVehPlate\" , " +
            "EX_TRANS_TIME as \"exTransTime\" ," +
            "EX_PASS_TYPE as \"exPassType\" , " +
            "EX_CARD_ID as \"exCardId\" , " +
            "EX_VEH_STATUS as \"exVehStatus\" , " +
            "EX_GID as \"exGId\" , " +
            "PASS_ID as \"passId\" , " +
            "FEE_TYPE as \"feeType\" , " +
            "TOLL_FEE as \"tollFee\" , " +
            "LOCAL_FEE as \"localFee\" , " +
            "AMOUNT as \"amount\" from TBL_TRANS_SUMMARY " +
            "where PASS_ID= #{passId}")
    TblPursuesTrans selectTransByPassId(@Param("passId") String passId);

    @Select("select " +
            "IMAGE as \"image\" from TBL_TRANS_IMAGE " +
            "where GID= #{gid}")
    List<String> selectTransImageByGid(@Param("gid") String gid);

    @Select({"<script>" +
            "select " +
            "EN_VEH_PLATE as \"enVehPlate\" , " +
            "EN_VEH_COLOR as \"enVehColor\" , " +
            "EN_VEH_CLASS as \"enVehClass\" , " +
            "EN_LANE_HEX as \"enLaneHex\" , " +
            "EN_TRANS_TIME as \"enTransTime\" , " +
            "EN_PASS_TYPE as \"enPassType\" , " +
            "EN_CARD_ID as \"enCardId\" , " +
            "EN_GID as \"enGId\" , " +
            "EN_VEH_STATUS as \"enVehStatus\" , " +
            "EX_LANE_HEX as \"exLaneHex\" , " +
            "EX_VEH_PLATE as \"exVehPlate\" , " +
            "EX_TRANS_TIME as \"exTransTime\" ," +
            "EX_PASS_TYPE as \"exPassType\" , " +
            "EX_CARD_ID as \"exCardId\" , " +
            "EX_VEH_STATUS as \"exVehStatus\" , " +
            "EX_GID as \"exGId\" , " +
            "PASS_ID as \"passId\" , " +
            "FEE_TYPE as \"feeType\" , " +
            "TOLL_FEE as \"tollFee\" , " +
            "LOCAL_FEE as \"localFee\" , " +
            "AMOUNT as \"amount\" from TBL_TRANS_SUMMARY " +
            "where EX_VEH_PLATE= #{vehPlate} " +
            "and EX_TRANS_TIME &gt;= #{time} and " +
            "EX_TRANS_TIME &lt;= #{time}+2/24 and rownum=1 " +
            "order by EX_TRANS_TIME " +
            "</script>"})
    TblPursuesTrans selectTransByPlate(@Param("vehPlate") String vehPlate,@Param("time") Date time);

    @Select({"<script>" +
            "select " +
            "EN_VEH_PLATE as \"enVehPlate\" , " +
            "EN_VEH_COLOR as \"enVehColor\" , " +
            "EN_VEH_CLASS as \"enVehClass\" , " +
            "EN_LANE_HEX as \"enLaneHex\" , " +
            "EN_TRANS_TIME as \"enTransTime\" , " +
            "EN_PASS_TYPE as \"enPassType\" , " +
            "EN_CARD_ID as \"enCardId\" , " +
            "EN_GID as \"enGId\" , " +
            "EN_VEH_STATUS as \"enVehStatus\" , " +
            "EX_LANE_HEX as \"exLaneHex\" , " +
            "EX_VEH_PLATE as \"exVehPlate\" , " +
            "EX_TRANS_TIME as \"exTransTime\" ," +
            "EX_PASS_TYPE as \"exPassType\" , " +
            "EX_CARD_ID as \"exCardId\" , " +
            "EX_VEH_STATUS as \"exVehStatus\" , " +
            "EX_GID as \"exGId\" , " +
            "PASS_ID as \"passId\" , " +
            "FEE_TYPE as \"feeType\" , " +
            "TOLL_FEE as \"tollFee\" , " +
            "LOCAL_FEE as \"localFee\" , " +
            "AMOUNT as \"amount\" from TBL_TRANS_SUMMARY a " +
            "left join TBL_LANE_INFO b on b.LANE_HEX=a.EX_LANE_HEX " +
            "where b.LANE_GB= #{laneGb} " +
            "and EX_TRANS_TIME &gt;= #{time}-2/24/60 and " +
            "EX_TRANS_TIME &lt;= #{time}+2/24/60 " +
            "order by EX_TRANS_TIME " +
            "</script>"})
    List<TblPursuesTrans> selectTransByLaneGb(@Param("laneGb") String laneGb, @Param("time") Date time);

    @Select({"<script>" +
            "select a.EN_VEH_PLATE as \"enVehPlate\" , " +
            "l.DICT_LABEL as \"enVehColor\" , " +
            "j.DICT_LABEL as \"enVehClass\" , " +
            "f.LANE_NAME as \"enLaneHex\" , " +
            "d.STATION_NAME as \"enStation\" , " +
            "to_char(a.EN_TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"enTransTime\" , " +
            "b.DICT_LABEL as \"enPassType\" , " +
            "a.EN_CARD_ID as \"enCardId\" , " +
            "k.DICT_LABEL as \"enVehStatus\" , " +
            "a.EN_IMAGE as \"enImage\" , " +
            "g.LANE_NAME as \"exLaneHex\" , " +
            "e.STATION_NAME as \"exStation\" , " +
            "a.EX_VEH_PLATE as \"exVehPlate\" , " +
            "to_char(a.EX_TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"exTransTime\" ," +
            "c.DICT_LABEL as \"exPassType\" , " +
            "a.EX_CARD_ID as \"exCardId\" , " +
            "n.DICT_LABEL as \"exVehStatus\" , " +
            "a.EX_IMAGE as \"exImage\" , " +
            "q.DICT_LABEL as \"feeType\" , " +
            "a.EN_GID as \"enGid\" , " +
            "a.EX_GID as \"exGid\" , " +
            "a.PASS_ID as \"passId\" , " +
            "a.TOLL_FEE as \"tollFee\" , " +
            "a.LOCAL_FEE as \"localFee\" , " +
            "a.AMOUNT as \"amount\" from TBL_PURSUES_TRANS a " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=to_char(a.EN_PASS_TYPE) and b.DICT_TYPE='pass_type' " +
            "left join SYS_DICT_DATA c on c.DICT_VALUE=to_char(a.EX_PASS_TYPE) and c.DICT_TYPE='pass_type' " +
            "left join TBL_BASE_STATION_INFO d on UPPER(d.STATION_HEX)=UPPER(SUBSTR(a.EN_LANE_HEX, 1, 8)) " +
            "left join TBL_BASE_STATION_INFO e on UPPER(e.STATION_HEX)=UPPER(SUBSTR(a.EX_LANE_HEX, 1, 8)) " +
            "left join TBL_LANE_INFO f on UPPER(f.LANE_HEX)=UPPER(a.EN_LANE_HEX) " +
            "left join TBL_LANE_INFO g on UPPER(g.LANE_HEX)=UPPER(a.EX_LANE_HEX) " +
            "left join SYS_DICT_DATA j on j.DICT_VALUE=a.EN_VEH_CLASS and j.DICT_TYPE='veh_class' " +
            "left join SYS_DICT_DATA k on k.DICT_VALUE=a.EN_VEH_STATUS and k.DICT_TYPE='veh_status' " +
            "left join SYS_DICT_DATA l on l.DICT_VALUE=a.EN_VEH_COLOR and l.DICT_TYPE='veh_color' " +
            "left join SYS_DICT_DATA n on n.DICT_VALUE=a.EX_VEH_STATUS and n.DICT_TYPE='veh_status' " +
            "left join SYS_DICT_DATA q on q.DICT_VALUE=a.FEE_TYPE and q.DICT_TYPE='fee_type' " +
            "where 1=1 " +
            "<when test='concertId != null'> " +
            "and a.CONCERT_ID = #{concertId} " +
            "</when>" +
            "order by a.EX_TRANS_TIME "+
            "</script>"})
    List<Map> selectPursuesTrans(@Param("concertId") String concertId);

}
