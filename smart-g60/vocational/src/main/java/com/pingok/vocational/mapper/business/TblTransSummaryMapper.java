package com.pingok.vocational.mapper.business;

import com.pingok.vocational.domain.business.vo.SummaryVo;
import com.pingok.vocational.domain.business.vo.TransSummaryVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_TRANS_SUMMARY 数据层
 *
 * @author xia
 */
public interface TblTransSummaryMapper {

    @Select({"<script>" +
            "select a.PASS_ID as \"passId\" , " +
            "a.EN_GID as \"enGid\" , " +
            "to_char(a.EN_TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"enTransTime\" , " +
            "to_char(a.EN_WORK_DATE, 'yyyy-mm-dd hh24:mi:ss') as \"enWorkDate\" , " +
            "b.DICT_LABEL as \"enPassType\" , " +
            "d.STATION_NAME as \"enStation\" , " +
            "f.LANE_NAME as \"enLaneHex\" , " +
            "h.DICT_LABEL as \"enShift\" , " +
            "j.DICT_LABEL as \"enVehClass\" , " +
            "k.DICT_LABEL as \"enVehStatus\" , " +
            "a.EN_VEH_PLATE as \"enVehPlate\" , " +
            "l.DICT_LABEL as \"enVehColor\" , " +
            "a.EN_CARD_ID as \"enCardId\" , " +
            "a.EX_GID as \"exGid\" , " +
            "to_char(a.EX_TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"exTransTime\" ," +
            "to_char(a.EX_WORK_DATE, 'yyyy-mm-dd hh24:mi:ss') as \"exWorkDate\" , " +
            "e.STATION_NAME as \"exStation\" , " +
            "g.LANE_NAME as \"exLaneHex\" , " +
            "i.DICT_LABEL as \"exShift\" , " +
            "a.TOLL_FEE as \"tollFee\" , " +
            "a.TOLL_FEE-a.TOLL_FEE95 as \"discountFee\" , " +
            "p.DICT_LABEL as \"payWay\" , " +
            "c.DICT_LABEL as \"exPassType\" , " +
            "m.DICT_LABEL as \"exVehClass\" , " +
            "n.DICT_LABEL as \"exVehStatus\" , " +
            "a.EX_VEH_PLATE as \"exVehPlate\" , " +
            "o.DICT_LABEL as \"exVehColor\" , " +
            "q.DICT_LABEL as \"feeType\" , " +
            "a.EX_CARD_ID as \"exCardId\" , " +
            "a.AMOUNT as \"amount\" from TBL_TRANS_SUMMARY a " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=to_char(a.EN_PASS_TYPE) and b.DICT_TYPE='pass_type' " +
            "left join SYS_DICT_DATA c on c.DICT_VALUE=to_char(a.EX_PASS_TYPE) and c.DICT_TYPE='pass_type' " +
            "left join TBL_BASE_STATION_INFO d on UPPER(d.STATION_HEX)=UPPER(SUBSTR(a.EN_LANE_HEX, 1, 8)) " +
            "left join TBL_BASE_STATION_INFO e on UPPER(e.STATION_HEX)=UPPER(SUBSTR(a.EX_LANE_HEX, 1, 8)) " +
            "left join TBL_LANE_INFO f on UPPER(f.LANE_HEX)=UPPER(a.EN_LANE_HEX) " +
            "left join TBL_LANE_INFO g on UPPER(g.LANE_HEX)=UPPER(a.EX_LANE_HEX) " +
            "left join SYS_DICT_DATA h on h.DICT_VALUE=to_char(a.EN_SHIFT) and h.DICT_TYPE='shift' " +
            "left join SYS_DICT_DATA i on i.DICT_VALUE=to_char(a.EX_SHIFT) and i.DICT_TYPE='shift' " +
            "left join SYS_DICT_DATA j on j.DICT_VALUE=a.EN_VEH_CLASS and j.DICT_TYPE='veh_class' " +
            "left join SYS_DICT_DATA k on k.DICT_VALUE=a.EN_VEH_STATUS and k.DICT_TYPE='veh_status' " +
            "left join SYS_DICT_DATA l on l.DICT_VALUE=a.EN_VEH_COLOR and l.DICT_TYPE='veh_color' " +
            "left join SYS_DICT_DATA m on m.DICT_VALUE=a.EX_VEH_CLASS and m.DICT_TYPE='veh_class' " +
            "left join SYS_DICT_DATA n on n.DICT_VALUE=a.EX_VEH_STATUS and n.DICT_TYPE='veh_status' " +
            "left join SYS_DICT_DATA o on o.DICT_VALUE=a.EX_VEH_COLOR and o.DICT_TYPE='veh_color' " +
            "left join SYS_DICT_DATA p on p.DICT_VALUE=to_char(a.PAY_WAY) and p.DICT_TYPE='pay_way' " +
            "left join SYS_DICT_DATA q on q.DICT_VALUE=a.FEE_TYPE and q.DICT_TYPE='fee_type' " +
            "where 1=1 " +
            "<when test='enStartTime != null'> " +
            "and a.EN_TRANS_TIME &gt;= #{enStartTime} " +
            "</when>"+
            "<when test='enEndTime != null'> " +
            "and a.EN_TRANS_TIME &lt;= #{enEndTime} " +
            "</when>" +
            "<when test='enWorkDate != null'> " +
            "and a.EN_WORK_DATE = #{enWorkDate} " +
            "</when>" +
            "<when test='enStationId != null'> " +
            "and SUBSTR(a.EN_LANE_HEX, 1, 8)= CONCAT('3101',#{enStationId}) " +
            "</when>"+
            "<when test='passId != null'> " +
            "and a.PASS_ID= #{passId} " +
            "</when>"+
            "<when test='enGid != null'> " +
            "and a.EN_GID= #{enGid} " +
            "</when>"+
            "<when test='enPassType != null'> " +
            "and a.EN_PASS_TYPE= #{enPassType} " +
            "</when>"+
            "<when test='enShift != null'> " +
            "and a.EN_SHIFT= #{enShift} " +
            "</when>"+
            "<when test='enVehPlate != null'> " +
            "and a.EN_VEH_PLATE= #{enVehPlate} " +
            "</when>"+
            "<when test='enCardId != null'> " +
            "and a.EN_CARD_ID= #{enCardId} " +
            "</when>"+
            "<when test='exStartTime != null'> " +
            "and a.EX_TRANS_TIME &gt;= #{exStartTime} " +
            "</when>"+
            "<when test='exEndTime != null'> " +
            "and a.EX_TRANS_TIME &lt;= #{exEndTime} " +
            "</when>" +
            "<when test='exWorkDate != null'> " +
            "and a.EX_WORK_DATE = #{exWorkDate} " +
            "</when>" +
            "<when test='exStationId != null'> " +
            "and SUBSTR(a.EX_LANE_HEX, 1, 8)= CONCAT('3101',#{exStationId}) " +
            "</when>"+
            "<when test='exGid != null'> " +
            "and a.EX_GID= #{exGid} " +
            "</when>"+
            "<when test='exPassType != null'> " +
            "and a.EX_PASS_TYPE= #{exPassType} " +
            "</when>"+
            "<when test='exShift != null'> " +
            "and a.EX_SHIFT= #{exShift} " +
            "</when>"+
            "<when test='exVehPlate != null'> " +
            "and a.EX_VEH_PLATE= #{exVehPlate} " +
            "</when>"+
            "<when test='exCardId != null'> " +
            "and a.EX_CARD_ID= #{exCardId} " +
            "</when>"+
            "<when test='payWay != null'> " +
            "and a.PAY_WAY= #{payWay} " +
            "</when>"+
            "</script>"})
    List<Map> selectTransSummary(@Param("enStartTime") Date enStartTime, @Param("enEndTime") Date enEndTime,
                                 @Param("enWorkDate") Date enWorkDate,@Param("enStationId") String enStationId,
                                 @Param("passId") String passId,@Param("enGid") String enGid,
                                 @Param("enPassType") Integer enPassType,@Param("enShift") Integer enShift,
                                 @Param("enVehPlate") String enVehPlate,@Param("enCardId") String enCardId,
                                 @Param("exStartTime") Date exStartTime, @Param("exEndTime") Date exEndTime,
                                 @Param("exWorkDate") Date exWorkDate,@Param("exStationId") String exStationId,
                                 @Param("exGid") String exGid,
                                 @Param("exPassType") Integer exPassType,@Param("exShift") Integer exShift,
                                 @Param("exVehPlate") String exVehPlate,@Param("exCardId") String exCardId,
                                 @Param("payWay") Integer payWay);

    @Select({"<script>" +
            "select a.PASS_ID as \"passId\" , \n" +
            "a.GID as \"enGid\" , \n" +
            "to_char(a.TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"enTransTime\" , \n" +
            "to_char(a.WORK_DATE, 'yyyy-mm-dd hh24:mi:ss') as \"enWorkDate\" , \n" +
            "b.DICT_LABEL as \"enPassType\" , \n" +
            "c.STATION_NAME as \"enStation\" , \n" +
            "d.LANE_NAME as \"enLaneHex\" , \n" +
            "e.DICT_LABEL as \"enShift\" , \n" +
            "f.DICT_LABEL as \"enVehClass\" , \n" +
            "g.DICT_LABEL as \"enVehStatus\" , \n" +
            "trim(a.VEH_PLATE) as \"enVehPlate\" , \n" +
            "h.DICT_LABEL as \"enVehColor\" , \n" +
            "case a.PASS_TYPE when 5 then ETC_CARD_ID when 6 then CPC_CARD_ID end as \"enCardId\" from EN_TRANS_SELECT a \n" +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=to_char(a.PASS_TYPE) and b.DICT_TYPE='pass_type' \n" +
            "left join TBL_BASE_STATION_INFO c on UPPER(c.STATION_HEX)=UPPER(SUBSTR(a.LANE_HEX, 1, 8)) \n" +
            "left join TBL_LANE_INFO d on UPPER(d.LANE_HEX)=UPPER(a.LANE_HEX) \n" +
            "left join SYS_DICT_DATA e on e.DICT_VALUE=to_char(a.SHIFT) and e.DICT_TYPE='shift' \n" +
            "left join SYS_DICT_DATA f on f.DICT_VALUE=a.VEH_CLASS and f.DICT_TYPE='veh_class' \n" +
            "left join SYS_DICT_DATA g on g.DICT_VALUE=a.VEH_STATUS and g.DICT_TYPE='veh_status' \n" +
            "left join SYS_DICT_DATA h on h.DICT_VALUE=a.VEH_COLOR and h.DICT_TYPE='veh_color' " +
            "where 1=1 " +
            "<when test='enStartTime != null'> " +
            "and a.TRANS_TIME &gt;= #{enStartTime} " +
            "</when>"+
            "<when test='enEndTime != null'> " +
            "and a.TRANS_TIME &lt;= #{enEndTime} " +
            "</when>" +
            "<when test='enWorkDate != null'> " +
            "and a.WORK_DATE = #{enWorkDate} " +
            "</when>" +
            "<when test='enStationId != null'> " +
            "and SUBSTR(a.LANE_HEX, 1, 8)= CONCAT('3101',#{enStationId}) " +
            "</when>"+
            "<when test='passId != null'> " +
            "and a.PASS_ID= #{passId} " +
            "</when>"+
            "<when test='enGid != null'> " +
            "and a.GID= #{enGid} " +
            "</when>"+
            "<when test='enPassType != null'> " +
            "and a.PASS_TYPE= #{enPassType} " +
            "</when>"+
            "<when test='enShift != null'> " +
            "and a.SHIFT= #{enShift} " +
            "</when>"+
            "<when test='enVehPlate != null'> " +
            "and trim(a.VEH_PLATE) like CONCAT(CONCAT('%',#{enVehPlate}),'%') " +
            "</when>"+
            "<when test='enCardId != null'> " +
            "and a.ETC_CARD_ID= #{enCardId} or a.CPC_CARD_ID= #{enCardId}" +
            "</when>"+
            "</script>"})
    List<Map> selectEnTransSummary(@Param("enStartTime") Date enStartTime, @Param("enEndTime") Date enEndTime,
                                 @Param("enWorkDate") Date enWorkDate,@Param("enStationId") String enStationId,
                                 @Param("passId") String passId,@Param("enGid") String enGid,
                                 @Param("enPassType") Integer enPassType,@Param("enShift") Integer enShift,
                                 @Param("enVehPlate") String enVehPlate,@Param("enCardId") String enCardId);

    @Select({"<script>" +
            "select a.PASS_ID as \"passId\",\n" +
            "a.GID as \"exGid\" , \n" +
            "to_char(a.TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"exTransTime\" ,\n" +
            "to_char(a.WORK_DATE, 'yyyy-mm-dd hh24:mi:ss') as \"exWorkDate\" , \n" +
            "c.STATION_NAME as \"exStation\" , \n" +
            "d.LANE_NAME as \"exLaneHex\" , \n" +
            "e.DICT_LABEL as \"exShift\" , \n" +
            "a.TOLL_FEE as \"tollFee\" , \n" +
            "a.TOLL_FEE-a.TOLL_FEE95 as \"discountFee\" , \n" +
            "i.DICT_LABEL as \"payWay\" , \n" +
            "b.DICT_LABEL as \"exPassType\" , \n" +
            "f.DICT_LABEL as \"exVehClass\" , \n" +
            "g.DICT_LABEL as \"exVehStatus\" , \n" +
            "trim(a.VEH_PLATE) as \"exVehPlate\" , \n" +
            "h.DICT_LABEL as \"exVehColor\" , \n" +
            "j.DICT_LABEL as \"feeType\" , \n" +
            "case a.PASS_TYPE when 5 then ETC_CARD_ID when 6 then CPC_CARD_ID end as \"exCardId\" , \n" +
            "a.AMOUNT as \"amount\" from EX_TRANS_SELECT a \n" +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=to_char(a.PASS_TYPE) and b.DICT_TYPE='pass_type' \n" +
            "left join TBL_BASE_STATION_INFO c on UPPER(c.STATION_HEX)=UPPER(SUBSTR(a.LANE_HEX, 1, 8)) \n" +
            "left join TBL_LANE_INFO d on UPPER(d.LANE_HEX)=UPPER(a.LANE_HEX) \n" +
            "left join SYS_DICT_DATA e on e.DICT_VALUE=to_char(a.SHIFT) and e.DICT_TYPE='shift' \n" +
            "left join SYS_DICT_DATA f on f.DICT_VALUE=a.VEH_CLASS and f.DICT_TYPE='veh_class' \n" +
            "left join SYS_DICT_DATA g on g.DICT_VALUE=a.VEH_STATUS and g.DICT_TYPE='veh_status' \n" +
            "left join SYS_DICT_DATA h on h.DICT_VALUE=a.VEH_COLOR and h.DICT_TYPE='veh_color' \n" +
            "left join SYS_DICT_DATA i on i.DICT_VALUE=to_char(a.PAY_WAY) and i.DICT_TYPE='pay_way' \n" +
            "left join SYS_DICT_DATA j on j.DICT_VALUE=a.FEE_TYPE and j.DICT_TYPE='fee_type' " +
            "where 1=1 " +
            "<when test='passId != null'> " +
            "and a.PASS_ID= #{passId} " +
            "</when>"+
            "<when test='exStartTime != null'> " +
            "and a.TRANS_TIME &gt;= #{exStartTime} " +
            "</when>"+
            "<when test='exEndTime != null'> " +
            "and a.TRANS_TIME &lt;= #{exEndTime} " +
            "</when>" +
            "<when test='exWorkDate != null'> " +
            "and a.WORK_DATE = #{exWorkDate} " +
            "</when>" +
            "<when test='exStationId != null'> " +
            "and SUBSTR(a.LANE_HEX, 1, 8)= CONCAT('3101',#{exStationId}) " +
            "</when>"+
            "<when test='exGid != null'> " +
            "and a.GID= #{exGid} " +
            "</when>"+
            "<when test='exPassType != null'> " +
            "and a.PASS_TYPE= #{exPassType} " +
            "</when>"+
            "<when test='exShift != null'> " +
            "and a.SHIFT= #{exShift} " +
            "</when>"+
            "<when test='exVehPlate != null'> " +
            "and trim(a.VEH_PLATE) like CONCAT(CONCAT('%',#{exVehPlate}),'%')  " +
            "</when>"+
            "<when test='exCardId != null'> " +
            "and a.ETC_CARD_ID= #{exCardId} or a.CPC_CARD_ID= #{exCardId} " +
            "</when>"+
            "<when test='payWay != null'> " +
            "and a.PAY_WAY= #{payWay} " +
            "</when>"+
            "</script>"})
    List<Map> selectExTransSummary(@Param("passId") String passId,@Param("exStartTime") Date exStartTime,
                                   @Param("exEndTime") Date exEndTime,@Param("exWorkDate") Date exWorkDate,
                                   @Param("exStationId") String exStationId,@Param("exGid") String exGid,
                                   @Param("exPassType") Integer exPassType,@Param("exShift") Integer exShift,
                                 @Param("exVehPlate") String exVehPlate,@Param("exCardId") String exCardId,
                                 @Param("payWay") Integer payWay);

    @Select({"<script>" +
            "select a.PASS_ID as \"passId\" , " +
            "a.EN_GID as \"enGid\" , " +
            "to_char(a.EN_TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"enTransTime\" , " +
            "to_char(a.EN_WORK_DATE, 'yyyy-mm-dd hh24:mi:ss') as \"enWorkDate\" , " +
            "b.DICT_LABEL as \"enPassType\" , " +
            "d.STATION_NAME as \"enStation\" , " +
            "f.LANE_NAME as \"enLaneHex\" , " +
            "h.DICT_LABEL as \"enShift\" , " +
            "j.DICT_LABEL as \"enVehClass\" , " +
            "k.DICT_LABEL as \"enVehStatus\" , " +
            "a.EN_VEH_PLATE as \"enVehPlate\" , " +
            "l.DICT_LABEL as \"enVehColor\" , " +
            "a.EN_CARD_ID as \"enCardId\" , " +
            "a.EX_GID as \"exGid\" , " +
            "to_char(a.EX_TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"exTransTime\" ," +
            "to_char(a.EX_WORK_DATE, 'yyyy-mm-dd hh24:mi:ss') as \"exWorkDate\" , " +
            "e.STATION_NAME as \"exStation\" , " +
            "g.LANE_NAME as \"exLaneHex\" , " +
            "i.DICT_LABEL as \"exShift\" , " +
            "a.TOLL_FEE as \"tollFee\" , " +
            "a.TOLL_FEE-a.TOLL_FEE95 as \"discountFee\" , " +
            "p.DICT_LABEL as \"payWay\" , " +
            "c.DICT_LABEL as \"exPassType\" , " +
            "m.DICT_LABEL as \"exVehClass\" , " +
            "n.DICT_LABEL as \"exVehStatus\" , " +
            "a.EX_VEH_PLATE as \"exVehPlate\" , " +
            "o.DICT_LABEL as \"exVehColor\" , " +
            "q.DICT_LABEL as \"feeType\" , " +
            "a.EX_CARD_ID as \"exCardId\" , " +
            "a.AMOUNT as \"amount\" from TBL_TRANS_SUMMARY a " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=to_char(a.EN_PASS_TYPE) and b.DICT_TYPE='pass_type' " +
            "left join SYS_DICT_DATA c on c.DICT_VALUE=to_char(a.EX_PASS_TYPE) and c.DICT_TYPE='pass_type' " +
            "left join TBL_BASE_STATION_INFO d on UPPER(d.STATION_HEX)=UPPER(SUBSTR(a.EN_LANE_HEX, 1, 8)) " +
            "left join TBL_BASE_STATION_INFO e on UPPER(e.STATION_HEX)=UPPER(SUBSTR(a.EX_LANE_HEX, 1, 8)) " +
            "left join TBL_LANE_INFO f on UPPER(f.LANE_HEX)=UPPER(a.EN_LANE_HEX) " +
            "left join TBL_LANE_INFO g on UPPER(g.LANE_HEX)=UPPER(a.EX_LANE_HEX) " +
            "left join SYS_DICT_DATA h on h.DICT_VALUE=to_char(a.EN_SHIFT) and h.DICT_TYPE='shift' " +
            "left join SYS_DICT_DATA i on i.DICT_VALUE=to_char(a.EX_SHIFT) and i.DICT_TYPE='shift' " +
            "left join SYS_DICT_DATA j on j.DICT_VALUE=a.EN_VEH_CLASS and j.DICT_TYPE='veh_class' " +
            "left join SYS_DICT_DATA k on k.DICT_VALUE=a.EN_VEH_STATUS and k.DICT_TYPE='veh_status' " +
            "left join SYS_DICT_DATA l on l.DICT_VALUE=a.EN_VEH_COLOR and l.DICT_TYPE='veh_color' " +
            "left join SYS_DICT_DATA m on m.DICT_VALUE=a.EX_VEH_CLASS and m.DICT_TYPE='veh_class' " +
            "left join SYS_DICT_DATA n on n.DICT_VALUE=a.EX_VEH_STATUS and n.DICT_TYPE='veh_status' " +
            "left join SYS_DICT_DATA o on o.DICT_VALUE=a.EX_VEH_COLOR and o.DICT_TYPE='veh_color' " +
            "left join SYS_DICT_DATA p on p.DICT_VALUE=to_char(a.PAY_WAY) and p.DICT_TYPE='pay_way' " +
            "left join SYS_DICT_DATA q on q.DICT_VALUE=a.FEE_TYPE and q.DICT_TYPE='fee_type' " +
            "where 1=1 " +
            "<when test='enStartTime != null'> " +
            "and a.EN_TRANS_TIME &gt;= #{enStartTime} " +
            "</when>"+
            "<when test='enEndTime != null'> " +
            "and a.EN_TRANS_TIME &lt;= #{enEndTime} " +
            "</when>" +
            "<when test='enWorkDate != null'> " +
            "and a.EN_WORK_DATE = #{enWorkDate} " +
            "</when>" +
            "<when test='enStationId != null'> " +
            "and SUBSTR(a.EN_LANE_HEX, 1, 8)= CONCAT('3101',#{enStationId}) " +
            "</when>"+
            "<when test='passId != null'> " +
            "and a.PASS_ID= #{passId} " +
            "</when>"+
            "<when test='enGid != null'> " +
            "and a.EN_GID= #{enGid} " +
            "</when>"+
            "<when test='enPassType != null'> " +
            "and a.EN_PASS_TYPE= #{enPassType} " +
            "</when>"+
            "<when test='enShift != null'> " +
            "and a.EN_SHIFT= #{enShift} " +
            "</when>"+
            "<when test='enVehPlate != null'> " +
            "and a.EN_VEH_PLATE= #{enVehPlate} " +
            "</when>"+
            "<when test='enCardId != null'> " +
            "and a.EN_CARD_ID= #{enCardId} " +
            "</when>"+
            "<when test='exStartTime != null'> " +
            "and a.EX_TRANS_TIME &gt;= #{exStartTime} " +
            "</when>"+
            "<when test='exEndTime != null'> " +
            "and a.EX_TRANS_TIME &lt;= #{exEndTime} " +
            "</when>" +
            "<when test='exWorkDate != null'> " +
            "and a.EX_WORK_DATE = #{exWorkDate} " +
            "</when>" +
            "<when test='exStationId != null'> " +
            "and SUBSTR(a.EX_LANE_HEX, 1, 8)= CONCAT('3101',#{exStationId}) " +
            "</when>"+
            "<when test='exGid != null'> " +
            "and a.EX_GID= #{exGid} " +
            "</when>"+
            "<when test='exPassType != null'> " +
            "and a.EX_PASS_TYPE= #{exPassType} " +
            "</when>"+
            "<when test='exShift != null'> " +
            "and a.EX_SHIFT= #{exShift} " +
            "</when>"+
            "<when test='exVehPlate != null'> " +
            "and a.EX_VEH_PLATE= #{exVehPlate} " +
            "</when>"+
            "<when test='exCardId != null'> " +
            "and a.EX_CARD_ID= #{exCardId} " +
            "</when>"+
            "<when test='payWay != null'> " +
            "and a.PAY_WAY= #{payWay} " +
            "</when>"+
            "</script>"})
    List<TransSummaryVo> selectTransSummaryList(SummaryVo summaryVo);
}
