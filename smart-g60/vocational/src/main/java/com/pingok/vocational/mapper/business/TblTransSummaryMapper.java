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
            "select en.PASS_ID as \"passId\" ,  " +
            "en.GID as \"enGid\" ,  " +
            "to_char(en.TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"enTransTime\" ,  " +
            "to_char(en.WORK_DATE, 'yyyy-mm-dd hh24:mi:ss') as \"enWorkDate\" ,  " +
            "b.DICT_LABEL as \"enPassType\" ,  " +
            "d.STATION_NAME as \"enStation\" ,  " +
            "f.LANE_NAME as \"enLaneHex\" ,  " +
            "h.DICT_LABEL as \"enShift\" ,  " +
            "j.DICT_LABEL as \"enVehClass\" ,  " +
            "k.DICT_LABEL as \"enVehStatus\" ,  " +
            "trim(en.VEH_PLATE) as \"enVehPlate\" ,  " +
            "l.DICT_LABEL as \"enVehColor\" ,  " +
            "case en.PASS_TYPE when 5 then enep.ETC_CARD_ID when 6 then enmp.CPC_CARD_ID end as \"enCardId\" , " +
            "ex.GID as \"exGid\" ,  " +
            "to_char(ex.TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"exTransTime\" , " +
            "to_char(ex.WORK_DATE, 'yyyy-mm-dd hh24:mi:ss') as \"exWorkDate\" ,  " +
            "e.STATION_NAME as \"exStation\" ,  " +
            "g.LANE_NAME as \"exLaneHex\" ,  " +
            "i.DICT_LABEL as \"exShift\" ,  " +
            "ex.TOLL_FEE as \"tollFee\" ,  " +
            "ex.TOLL_FEE-ex.TOLL_FEE95 as \"discountFee\" ,  " +
            "p.DICT_LABEL as \"payWay\" ,  " +
            "c.DICT_LABEL as \"exPassType\" ,  " +
            "m.DICT_LABEL as \"exVehClass\" ,  " +
            "n.DICT_LABEL as \"exVehStatus\" ,  " +
            "trim(ex.VEH_PLATE) as \"exVehPlate\" ,  " +
            "o.DICT_LABEL as \"exVehColor\" ,  " +
            "q.DICT_LABEL as \"feeType\" ,  " +
            "case ex.PASS_TYPE when 5 then exep.ETC_CARD_ID when 6 then exmp.CPC_CARD_ID end as \"exCardId\" ,  " +
            "ex.AMOUNT as \"amount\" from TBL_EN_TRANS_${year} en  " +
            "left join TBL_EX_TRANS_${year} ex on en.PASS_ID=ex.PASS_ID " +
            "left join TBL_EN_MTC_PASS_${year} enmp on en.RECORD_ID=enmp.RECORD_ID " +
            "left join TBL_EN_ETC_PASS_${year} enep on en.RECORD_ID=enep.RECORD_ID " +
            "left join TBL_EX_MTC_PASS_${year} exmp on ex.RECORD_ID=exmp.RECORD_ID " +
            "left join TBL_EX_ETC_PASS_${year} exep on ex.RECORD_ID=exep.RECORD_ID " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=to_char(en.PASS_TYPE) and b.DICT_TYPE='pass_type'  " +
            "left join SYS_DICT_DATA c on c.DICT_VALUE=to_char(ex.PASS_TYPE) and c.DICT_TYPE='pass_type'  " +
            "left join TBL_BASE_STATION_INFO d on UPPER(d.STATION_HEX)=UPPER(SUBSTR(en.LANE_HEX, 1, 8))  " +
            "left join TBL_BASE_STATION_INFO e on UPPER(e.STATION_HEX)=UPPER(SUBSTR(ex.LANE_HEX, 1, 8))  " +
            "left join TBL_LANE_INFO f on UPPER(f.LANE_HEX)=UPPER(en.LANE_HEX)  " +
            "left join TBL_LANE_INFO g on UPPER(g.LANE_HEX)=UPPER(ex.LANE_HEX)  " +
            "left join SYS_DICT_DATA h on h.DICT_VALUE=to_char(en.SHIFT) and h.DICT_TYPE='shift'  " +
            "left join SYS_DICT_DATA i on i.DICT_VALUE=to_char(ex.SHIFT) and i.DICT_TYPE='shift'  " +
            "left join SYS_DICT_DATA j on j.DICT_VALUE=en.VEH_CLASS and j.DICT_TYPE='veh_class'  " +
            "left join SYS_DICT_DATA k on k.DICT_VALUE=en.VEH_STATUS and k.DICT_TYPE='veh_status'  " +
            "left join SYS_DICT_DATA l on l.DICT_VALUE=en.VEH_COLOR and l.DICT_TYPE='veh_color'  " +
            "left join SYS_DICT_DATA m on m.DICT_VALUE=ex.VEH_CLASS and m.DICT_TYPE='veh_class'  " +
            "left join SYS_DICT_DATA n on n.DICT_VALUE=ex.VEH_STATUS and n.DICT_TYPE='veh_status'  " +
            "left join SYS_DICT_DATA o on o.DICT_VALUE=ex.VEH_COLOR and o.DICT_TYPE='veh_color'  " +
            "left join SYS_DICT_DATA p on p.DICT_VALUE=to_char(ex.PAY_WAY) and p.DICT_TYPE='pay_way'  " +
            "left join SYS_DICT_DATA q on q.DICT_VALUE=ex.FEE_TYPE and q.DICT_TYPE='fee_type'  " +
            "where 1=1 " +
            "<when test='enStartTime != null'> " +
            "and en.TRANS_TIME &gt;= #{enStartTime} " +
            "</when>"+
            "<when test='enEndTime != null'> " +
            "and en.TRANS_TIME &lt;= #{enEndTime} " +
            "</when>" +
            "<when test='enWorkDate != null'> " +
            "and en.WORK_DATE = #{enWorkDate} " +
            "</when>" +
            "<when test='enStationId != null'> " +
            "and SUBSTR(en.LANE_HEX, 1, 8)= CONCAT('3101',#{enStationId}) " +
            "</when>"+
            "<when test='passId != null'> " +
            "and en.PASS_ID= #{passId} " +
            "</when>"+
            "<when test='enGid != null'> " +
            "and en.GID= #{enGid} " +
            "</when>"+
            "<when test='enPassType != null'> " +
            "and en.PASS_TYPE= #{enPassType} " +
            "</when>"+
            "<when test='enShift != null'> " +
            "and en.SHIFT= #{enShift} " +
            "</when>"+
            "<when test='enVehPlate != null'> " +
            "and trim(en.VEH_PLATE) like CONCAT(CONCAT('%',#{enVehPlate}),'%') " +
            "</when>"+
            "<when test='enCardId != null'> " +
            "and enep.ETC_CARD_ID= #{enCardId} or enmp.CPC_CARD_ID= #{enCardId}" +
            "</when>"+
            "<when test='exStartTime != null'> " +
            "and ex.TRANS_TIME &gt;= #{exStartTime} " +
            "</when>"+
            "<when test='exEndTime != null'> " +
            "and ex.TRANS_TIME &lt;= #{exEndTime} " +
            "</when>" +
            "<when test='exWorkDate != null'> " +
            "and ex.WORK_DATE = #{exWorkDate} " +
            "</when>" +
            "<when test='exStationId != null'> " +
            "and SUBSTR(ex.LANE_HEX, 1, 8)= CONCAT('3101',#{exStationId}) " +
            "</when>"+
            "<when test='exGid != null'> " +
            "and ex.GID= #{exGid} " +
            "</when>"+
            "<when test='exPassType != null'> " +
            "and ex.PASS_TYPE= #{exPassType} " +
            "</when>"+
            "<when test='exShift != null'> " +
            "and ex.SHIFT= #{exShift} " +
            "</when>"+
            "<when test='exVehPlate != null'> " +
            "and trim(ex.VEH_PLATE) like CONCAT(CONCAT('%',#{exVehPlate}),'%') " +
            "</when>"+
            "<when test='exCardId != null'> " +
            "and exep.ETC_CARD_ID= #{exCardId} or exmp.CPC_CARD_ID= #{exCardId}" +
            "</when>"+
            "<when test='payWay != null'> " +
            "and ex.PAY_WAY= #{payWay} " +
            "</when>"+
            "union " +
            "select ex.PASS_ID as \"passId\" ,  " +
            "en.GID as \"enGid\" ,  " +
            "to_char(en.TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"enTransTime\" ,  " +
            "to_char(en.WORK_DATE, 'yyyy-mm-dd hh24:mi:ss') as \"enWorkDate\" ,  " +
            "b.DICT_LABEL as \"enPassType\" ,  " +
            "d.STATION_NAME as \"enStation\" ,  " +
            "f.LANE_NAME as \"enLaneHex\" ,  " +
            "h.DICT_LABEL as \"enShift\" ,  " +
            "j.DICT_LABEL as \"enVehClass\" ,  " +
            "k.DICT_LABEL as \"enVehStatus\" ,  " +
            "trim(en.VEH_PLATE) as \"enVehPlate\" ,  " +
            "l.DICT_LABEL as \"enVehColor\" ,  " +
            "case en.PASS_TYPE when 5 then enep.ETC_CARD_ID when 6 then enmp.CPC_CARD_ID end as \"enCardId\" , " +
            "ex.GID as \"exGid\" ,  " +
            "to_char(ex.TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"exTransTime\" , " +
            "to_char(ex.WORK_DATE, 'yyyy-mm-dd hh24:mi:ss') as \"exWorkDate\" ,  " +
            "e.STATION_NAME as \"exStation\" ,  " +
            "g.LANE_NAME as \"exLaneHex\" ,  " +
            "i.DICT_LABEL as \"exShift\" ,  " +
            "ex.TOLL_FEE as \"tollFee\" ,  " +
            "ex.TOLL_FEE-ex.TOLL_FEE95 as \"discountFee\" ,  " +
            "p.DICT_LABEL as \"payWay\" ,  " +
            "c.DICT_LABEL as \"exPassType\" ,  " +
            "m.DICT_LABEL as \"exVehClass\" ,  " +
            "n.DICT_LABEL as \"exVehStatus\" ,  " +
            "trim(ex.VEH_PLATE) as \"exVehPlate\" ,  " +
            "o.DICT_LABEL as \"exVehColor\" ,  " +
            "q.DICT_LABEL as \"feeType\" ,  " +
            "case ex.PASS_TYPE when 5 then exep.ETC_CARD_ID when 6 then exmp.CPC_CARD_ID end as \"exCardId\" ,  " +
            "ex.AMOUNT as \"amount\" from TBL_EX_TRANS_${year} ex  " +
            "left join TBL_EN_TRANS_${year} en on en.PASS_ID=ex.PASS_ID " +
            "left join TBL_EN_MTC_PASS_${year} enmp on en.RECORD_ID=enmp.RECORD_ID " +
            "left join TBL_EN_ETC_PASS_${year} enep on en.RECORD_ID=enep.RECORD_ID " +
            "left join TBL_EX_MTC_PASS_${year} exmp on ex.RECORD_ID=exmp.RECORD_ID " +
            "left join TBL_EX_ETC_PASS_${year} exep on ex.RECORD_ID=exep.RECORD_ID " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=to_char(en.PASS_TYPE) and b.DICT_TYPE='pass_type'  " +
            "left join SYS_DICT_DATA c on c.DICT_VALUE=to_char(ex.PASS_TYPE) and c.DICT_TYPE='pass_type'  " +
            "left join TBL_BASE_STATION_INFO d on UPPER(d.STATION_HEX)=UPPER(SUBSTR(en.LANE_HEX, 1, 8))  " +
            "left join TBL_BASE_STATION_INFO e on UPPER(e.STATION_HEX)=UPPER(SUBSTR(ex.LANE_HEX, 1, 8))  " +
            "left join TBL_LANE_INFO f on UPPER(f.LANE_HEX)=UPPER(en.LANE_HEX)  " +
            "left join TBL_LANE_INFO g on UPPER(g.LANE_HEX)=UPPER(ex.LANE_HEX)  " +
            "left join SYS_DICT_DATA h on h.DICT_VALUE=to_char(en.SHIFT) and h.DICT_TYPE='shift'  " +
            "left join SYS_DICT_DATA i on i.DICT_VALUE=to_char(ex.SHIFT) and i.DICT_TYPE='shift'  " +
            "left join SYS_DICT_DATA j on j.DICT_VALUE=en.VEH_CLASS and j.DICT_TYPE='veh_class'  " +
            "left join SYS_DICT_DATA k on k.DICT_VALUE=en.VEH_STATUS and k.DICT_TYPE='veh_status'  " +
            "left join SYS_DICT_DATA l on l.DICT_VALUE=en.VEH_COLOR and l.DICT_TYPE='veh_color'  " +
            "left join SYS_DICT_DATA m on m.DICT_VALUE=ex.VEH_CLASS and m.DICT_TYPE='veh_class'  " +
            "left join SYS_DICT_DATA n on n.DICT_VALUE=ex.VEH_STATUS and n.DICT_TYPE='veh_status'  " +
            "left join SYS_DICT_DATA o on o.DICT_VALUE=ex.VEH_COLOR and o.DICT_TYPE='veh_color'  " +
            "left join SYS_DICT_DATA p on p.DICT_VALUE=to_char(ex.PAY_WAY) and p.DICT_TYPE='pay_way'  " +
            "left join SYS_DICT_DATA q on q.DICT_VALUE=ex.FEE_TYPE and q.DICT_TYPE='fee_type' " +
            "where 1=1 " +
            "<when test='enStartTime != null'> " +
            "and en.TRANS_TIME &gt;= #{enStartTime} " +
            "</when>"+
            "<when test='enEndTime != null'> " +
            "and en.TRANS_TIME &lt;= #{enEndTime} " +
            "</when>" +
            "<when test='enWorkDate != null'> " +
            "and en.WORK_DATE = #{enWorkDate} " +
            "</when>" +
            "<when test='enStationId != null'> " +
            "and SUBSTR(en.LANE_HEX, 1, 8)= CONCAT('3101',#{enStationId}) " +
            "</when>"+
            "<when test='passId != null'> " +
            "and en.PASS_ID= #{passId} " +
            "</when>"+
            "<when test='enGid != null'> " +
            "and en.GID= #{enGid} " +
            "</when>"+
            "<when test='enPassType != null'> " +
            "and en.PASS_TYPE= #{enPassType} " +
            "</when>"+
            "<when test='enShift != null'> " +
            "and en.SHIFT= #{enShift} " +
            "</when>"+
            "<when test='enVehPlate != null'> " +
            "and trim(en.VEH_PLATE) like CONCAT(CONCAT('%',#{enVehPlate}),'%') " +
            "</when>"+
            "<when test='enCardId != null'> " +
            "and enep.ETC_CARD_ID= #{enCardId} or enmp.CPC_CARD_ID= #{enCardId}" +
            "</when>"+
            "<when test='exStartTime != null'> " +
            "and ex.TRANS_TIME &gt;= #{exStartTime} " +
            "</when>"+
            "<when test='exEndTime != null'> " +
            "and ex.TRANS_TIME &lt;= #{exEndTime} " +
            "</when>" +
            "<when test='exWorkDate != null'> " +
            "and ex.WORK_DATE = #{exWorkDate} " +
            "</when>" +
            "<when test='exStationId != null'> " +
            "and SUBSTR(ex.LANE_HEX, 1, 8)= CONCAT('3101',#{exStationId}) " +
            "</when>"+
            "<when test='exGid != null'> " +
            "and ex.GID= #{exGid} " +
            "</when>"+
            "<when test='exPassType != null'> " +
            "and ex.PASS_TYPE= #{exPassType} " +
            "</when>"+
            "<when test='exShift != null'> " +
            "and ex.SHIFT= #{exShift} " +
            "</when>"+
            "<when test='exVehPlate != null'> " +
            "and trim(ex.VEH_PLATE) like CONCAT(CONCAT('%',#{exVehPlate}),'%') " +
            "</when>"+
            "<when test='exCardId != null'> " +
            "and exep.ETC_CARD_ID= #{exCardId} or exmp.CPC_CARD_ID= #{exCardId}" +
            "</when>"+
            "<when test='payWay != null'> " +
            "and ex.PAY_WAY= #{payWay} " +
            "</when>"+
            "</script>"})
    List<Map> selectTransSummary(@Param("year") String year,@Param("enStartTime") Date enStartTime, @Param("enEndTime") Date enEndTime,
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
            "select a.PASS_ID as \"passId\" ,  " +
            "a.GID as \"enGid\" ,  " +
            "to_char(a.TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"enTransTime\" ,  " +
            "to_char(a.WORK_DATE, 'yyyy-mm-dd hh24:mi:ss') as \"enWorkDate\" ,  " +
            "b.DICT_LABEL as \"enPassType\" ,  " +
            "c.STATION_NAME as \"enStation\" ,  " +
            "d.LANE_NAME as \"enLaneHex\" ,  " +
            "e.DICT_LABEL as \"enShift\" ,  " +
            "f.DICT_LABEL as \"enVehClass\" ,  " +
            "g.DICT_LABEL as \"enVehStatus\" ,  " +
            "trim(a.VEH_PLATE) as \"enVehPlate\" ,  " +
            "h.DICT_LABEL as \"enVehColor\" ,  " +
            "case a.PASS_TYPE when 5 then ETC_CARD_ID when 6 then CPC_CARD_ID end as \"enCardId\" from EN_TRANS_SELECT a  " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=to_char(a.PASS_TYPE) and b.DICT_TYPE='pass_type'  " +
            "left join TBL_BASE_STATION_INFO c on UPPER(c.STATION_HEX)=UPPER(SUBSTR(a.LANE_HEX, 1, 8))  " +
            "left join TBL_LANE_INFO d on UPPER(d.LANE_HEX)=UPPER(a.LANE_HEX)  " +
            "left join SYS_DICT_DATA e on e.DICT_VALUE=to_char(a.SHIFT) and e.DICT_TYPE='shift'  " +
            "left join SYS_DICT_DATA f on f.DICT_VALUE=a.VEH_CLASS and f.DICT_TYPE='veh_class'  " +
            "left join SYS_DICT_DATA g on g.DICT_VALUE=a.VEH_STATUS and g.DICT_TYPE='veh_status'  " +
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
            "select a.PASS_ID as \"passId\", " +
            "a.GID as \"exGid\" ,  " +
            "to_char(a.TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"exTransTime\" , " +
            "to_char(a.WORK_DATE, 'yyyy-mm-dd hh24:mi:ss') as \"exWorkDate\" ,  " +
            "c.STATION_NAME as \"exStation\" ,  " +
            "d.LANE_NAME as \"exLaneHex\" ,  " +
            "e.DICT_LABEL as \"exShift\" ,  " +
            "a.TOLL_FEE as \"tollFee\" ,  " +
            "a.TOLL_FEE-a.TOLL_FEE95 as \"discountFee\" ,  " +
            "i.DICT_LABEL as \"payWay\" ,  " +
            "b.DICT_LABEL as \"exPassType\" ,  " +
            "f.DICT_LABEL as \"exVehClass\" ,  " +
            "g.DICT_LABEL as \"exVehStatus\" ,  " +
            "trim(a.VEH_PLATE) as \"exVehPlate\" ,  " +
            "h.DICT_LABEL as \"exVehColor\" ,  " +
            "j.DICT_LABEL as \"feeType\" ,  " +
            "case a.PASS_TYPE when 5 then ETC_CARD_ID when 6 then CPC_CARD_ID end as \"exCardId\" ,  " +
            "a.AMOUNT as \"amount\" from EX_TRANS_SELECT a  " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=to_char(a.PASS_TYPE) and b.DICT_TYPE='pass_type'  " +
            "left join TBL_BASE_STATION_INFO c on UPPER(c.STATION_HEX)=UPPER(SUBSTR(a.LANE_HEX, 1, 8))  " +
            "left join TBL_LANE_INFO d on UPPER(d.LANE_HEX)=UPPER(a.LANE_HEX)  " +
            "left join SYS_DICT_DATA e on e.DICT_VALUE=to_char(a.SHIFT) and e.DICT_TYPE='shift'  " +
            "left join SYS_DICT_DATA f on f.DICT_VALUE=a.VEH_CLASS and f.DICT_TYPE='veh_class'  " +
            "left join SYS_DICT_DATA g on g.DICT_VALUE=a.VEH_STATUS and g.DICT_TYPE='veh_status'  " +
            "left join SYS_DICT_DATA h on h.DICT_VALUE=a.VEH_COLOR and h.DICT_TYPE='veh_color'  " +
            "left join SYS_DICT_DATA i on i.DICT_VALUE=to_char(a.PAY_WAY) and i.DICT_TYPE='pay_way'  " +
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
    
    @Select({"<script>" +
            "SELECT  " +
            "en.PASS_ID as \"passId\", " +
            "en.ID as \"enGid\", " +
            "to_char(en.EN_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"enTransTime\", " +
            "en.MEDIA_TYPE as \"enPassType\", " +
            "station.STATION_NAME as \"enStation\", " +
            "lane.LANE_NAME as \"enLaneHex\", " +
            "vt.DICT_LABEL as \"enVehClass\", " +
            "vc.DICT_LABEL as \"enVehStatus\", " +
            "SUBSTR(en.VEHICLE_ID, 1, INSTR(en.VEHICLE_ID, '_')-1) as \"enVehPlate\", " +
            "color.DICT_LABEL as \"enVehColor\", " +
            "en.CARD_ID as \"enCardId\", " +
            "ex.\"exGid\", " +
            "ex.\"exTransTime\", " +
            "ex.\"exPassType\", " +
            "ex.\"exStation\", " +
            "ex.\"stationId\"," +
            "ex.\"exVehPlate\", " +
            "ex.\"exVehClass\", " +
            "ex.\"exVehStatus\", " +
            "ex.\"exVehColor\", " +
            "ex.\"exCardId\", " +
            "ex.\"payWay\", " +
            "ex.\"tollFee\", " +
            "ex.\"discountFee\", " +
            "ex.\"feeType\"," +
            "ex.\"amount\" " +
            "FROM TBL_SHAR_ENPD_RES_SENDER_${year} en " +
            "LEFT JOIN TBL_LANE_INFO lane on lane.LANE_HEX = en.EN_TOLL_LANE_HEX " +
            "LEFT JOIN TBL_BASE_STATION_INFO station on UPPER(station.STATION_HEX) = UPPER(CONCAT('3101',lane.STATION_ID))  " +
            "LEFT JOIN SYS_DICT_DATA vt on vt.DICT_VALUE=en.VEHICLE_TYPE and vt.DICT_TYPE='veh_class' " +
            "LEFT JOIN SYS_DICT_DATA vc on vc.DICT_VALUE=en.VEHICLE_CLASS and vc.DICT_TYPE='veh_status' " +
            "LEFT JOIN SYS_DICT_DATA pass on pass.DICT_VALUE=en.MEDIA_TYPE and pass.DICT_TYPE='pass_way' " +
            "LEFT JOIN SYS_DICT_DATA color on color.DICT_VALUE=SUBSTR(en.VEHICLE_ID, INSTR(en.VEHICLE_ID, '_')+1,LENGTH(en.VEHICLE_ID)) and color.DICT_TYPE='veh_color' " +
            "LEFT JOIN ( " +
            "SELECT " +
            "exEtc.PASS_ID as \"passIDEx\", " +
            "exEtc.ID as \"exGid\", " +
            "to_char(exEtc.EX_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"exTransTime\", " +
            "a.DICT_LABEL as \"exPassType\", " +
            "exEtc.EX_TOLL_STATION_NAME as \"exStation\", " +
            "f.STATION_ID as \"stationId\"," +
            "SUBSTR(exEtc.VEHICLE_ID, 1, INSTR(exEtc.VEHICLE_ID, '_')-1) as \"exVehPlate\", " +
            "b.DICT_LABEL as \"exVehClass\", " +
            "c.DICT_LABEL as \"exVehStatus\", " +
            "d.DICT_LABEL as \"exVehColor\", " +
            "exEtc.CARD_ID as \"exCardId\", " +
            "e.DICT_LABEL as \"payWay\", " +
            "exEtc.PAY_FEE as \"tollFee\", " +
            "exEtc.DISCOUNT_FEE as \"discountFee\", " +
            "g.DICT_LABEL as \"feeType\", " +
            "exEtc.TRANS_FEE as \"amount\" " +
            "FROM TBL_SHAR_ETCTD_RES_SENDER_${year} exEtc " +
            "LEFT JOIN SYS_DICT_DATA a on a.DICT_VALUE=exEtc.MEDIA_TYPE and a.DICT_TYPE='pass_way' " +
            "LEFT JOIN SYS_DICT_DATA b on b.DICT_VALUE=exEtc.VEHICLE_TYPE and b.DICT_TYPE='veh_class' " +
            "LEFT JOIN SYS_DICT_DATA c on c.DICT_VALUE=exEtc.VEHICLE_CLASS and c.DICT_TYPE='veh_status' " +
            "LEFT JOIN SYS_DICT_DATA d on d.DICT_VALUE=SUBSTR(exEtc.VEHICLE_ID, INSTR(exEtc.VEHICLE_ID, '_')+1,LENGTH(exEtc.VEHICLE_ID)) and d.DICT_TYPE='veh_color' " +
            "left join SYS_DICT_DATA e on e.DICT_VALUE=exEtc.TRANS_PAY_TYPE and e.DICT_TYPE='pay_way' " +
            "LEFT JOIN TBL_BASE_STATION_INFO f on f.STATION_NAME = exEtc.EX_TOLL_STATION_NAME " +
            "LEFT JOIN SYS_DICT_DATA g on g.DICT_VALUE=exEtc.EXIT_FEE_TYPE and g.DICT_TYPE='fee_type' " +
            "UNION ALL " +
            "SELECT " +
            "exCpc.PASS_ID as \"passIDEx\", " +
            "exCpc.ID as \"exGid\", " +
            "to_char(exCpc.EX_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"exTransTime\", " +
            "a.DICT_LABEL as \"exPassType\", " +
            "exCpc.EX_TOLL_STATION_NAME as \"exStation\", " +
            "f.STATION_ID as \"stationId\"," +
            "SUBSTR(exCpc.VEHICLE_ID, 1, INSTR(exCpc.VEHICLE_ID, '_')-1) as \"exVehPlate\", " +
            "b.DICT_LABEL as \"exVehClass\", " +
            "c.DICT_LABEL as \"exVehStatus\", " +
            "d.DICT_LABEL as \"exVehColor\", " +
            "exCpc.CARD_ID as \"exCardId\", " +
            "e.DICT_LABEL as \"payWay\", " +
            "exCpc.PAY_FEE as \"tollFee\", " +
            "exCpc.DISCOUNT_FEE as \"discountFee\", " +
            "g.DICT_LABEL as \"feeType\", " +
            "exCpc.TRANS_FEE as \"amount\" " +
            "FROM TBL_SHAR_OTD_RES_SENDER_${year} exCpc " +
            "LEFT JOIN SYS_DICT_DATA a on a.DICT_VALUE=exCpc.MEDIA_TYPE and a.DICT_TYPE='pass_way' " +
            "LEFT JOIN SYS_DICT_DATA b on b.DICT_VALUE=exCpc.VEHICLE_TYPE and b.DICT_TYPE='veh_class' " +
            "LEFT JOIN SYS_DICT_DATA c on c.DICT_VALUE=exCpc.VEHICLE_CLASS and c.DICT_TYPE='veh_status' " +
            "LEFT JOIN SYS_DICT_DATA d on d.DICT_VALUE=SUBSTR(exCpc.VEHICLE_ID, INSTR(exCpc.VEHICLE_ID, '_')+1,LENGTH(exCpc.VEHICLE_ID)) and d.DICT_TYPE='veh_color' " +
            "left join SYS_DICT_DATA e on e.DICT_VALUE=exCpc.TRANS_PAY_TYPE and e.DICT_TYPE='pay_way' " +
            "LEFT JOIN TBL_BASE_STATION_INFO f on f.STATION_NAME = exCpc.EX_TOLL_STATION_NAME " +
            "LEFT JOIN SYS_DICT_DATA g on g.DICT_VALUE=exCpc.EXIT_FEE_TYPE and g.DICT_TYPE='fee_type' " +
            ") ex on ex.\"passIDEx\" = en.PASS_ID " +
            "where 1=1 " +
            "<when test='enStartTime != null'> " +
            "and en.EN_TIME &gt;= #{enStartTime} " +
            "</when>"+
            "<when test='enEndTime != null'> " +
            "and en.EN_TIME &lt;= #{enEndTime} " +
            "</when>" +
            "<when test='enStationId != null'> " +
            "and SUBSTR(en.EN_TOLL_LANE_HEX, 1, 8)= CONCAT('3101',#{enStationId}) " +
            "</when>"+
            "<when test='passId != null'> " +
            "and en.PASS_ID= #{passId} " +
            "</when>"+
            "<when test='enGid != null'> " +
            "and en.ID= #{enGid} " +
            "</when>"+
            "<when test='enPassType != null'> " +
            "and en.MEDIA_TYPE= #{enPassType} " +
            "</when>"+
            "<when test='enVehPlate != null'> " +
            "and SUBSTR(en.VEHICLE_ID, 1, INSTR(en.VEHICLE_ID, '_')-1) like CONCAT(CONCAT('%',#{enVehPlate}),'%') " +
            "</when>"+
            "<when test='enCardId != null'> " +
            "and en.CARD_ID= #{enCardId}" +
            "</when>"+
            "<when test='exStartTime != null'> " +
            "and ex.\"exTransTime\" &gt;= #{exStartTime} " +
            "</when>"+
            "<when test='exEndTime != null'> " +
            "and ex.\"exTransTime\" &lt;= #{exEndTime} " +
            "</when>" +
            "<when test='exStationId != null'> " +
            "and ex.\"stationId\"= CONCAT('3101',#{exStationId}) " +
            "</when>"+
            "<when test='exGid != null'> " +
            "and ex.\"exGid\"= #{exGid} " +
            "</when>"+
            "<when test='exPassType != null'> " +
            "and ex.\"exPassType\"= #{exPassType} " +
            "</when>"+
            "<when test='exVehPlate != null'> " +
            "and ex.\"exVehPlate\" like CONCAT(CONCAT('%',#{exVehPlate}),'%') " +
            "</when>"+
            "<when test='exCardId != null'> " +
            "and ex.\"exCardId\"= #{exCardId}" +
            "</when>"+
            "<when test='payWay != null'> " +
            "and ex.\"payWay\"= #{payWay} " +
            "</when>"+
            "</script>"})
    List<Map> selectTransactionFlow(@Param("year") String year,@Param("enStartTime") Date enStartTime, @Param("enEndTime") Date enEndTime,
                                    @Param("enStationId") String enStationId, @Param("passId") String passId,@Param("enGid") String enGid,
                                    @Param("enPassType") Integer enPassType, @Param("enVehPlate") String enVehPlate,
                                    @Param("enCardId") String enCardId, @Param("exStartTime") Date exStartTime,
                                    @Param("exEndTime") Date exEndTime,@Param("exStationId") String exStationId,
                                    @Param("exGid") String exGid, @Param("exPassType") Integer exPassType,
                                    @Param("exVehPlate") String exVehPlate,@Param("exCardId") String exCardId,
                                    @Param("payWay") Integer payWay);
}
