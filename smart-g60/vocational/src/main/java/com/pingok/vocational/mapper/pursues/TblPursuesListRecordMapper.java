package com.pingok.vocational.mapper.pursues;

import com.pingok.vocational.domain.roster.TblPursuesListRecord;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_PURSUES_LIST_RECORD 数据层
 *
 * @author xia
 */
public interface TblPursuesListRecordMapper extends CommonRepository<TblPursuesListRecord> {

    @Select({"<script>" +
            "select CONCERT_ID as \"concertId\" , " +
            "PASS_ID as \"passId\" , " +
            "INITIATOR_PROV as \"initiatorProv\" , " +
            "ROAD as \"road\" , " +
            "VEH_PLATE as \"vehPlate\" , " +
            "VEH_COLOR as \"vehColor\"," +
            "ETC_CARD_ID as \"etcCardId\" , " +
            "MEDIA_TYPE as \"mediaType\" , " +
            "MEDIA_NO as \"mediaNo\" , " +
            "FEE as \"fee\" , " +
            "to_char(END_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"endTime\" , " +
            "to_char(SUBMIT_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"submitTime\" , " +
            "to_char(STATUS_UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"statusUpdateTime\" , " +
            "to_char(ROAD_UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"roadUpdateTime\" , " +
            "VEH_CLASS as \"vehClass\"," +
            "LAST_GANTRY_DE as \"lastGantryDe\" , " +
            "LAST_GANTRY_PROV as \"lastGantryProv\" , " +
            "to_char(LAST_GANTRY_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"lastGantryTime\" , " +
            "CIRCULATION_STATUS as \"circulationStatus\" , " +
            "UPLOAD_STATUS as \"uploadStatus\" , " +
            "UPLOAD_DESCRIBE as \"uploadDescribe\" , " +
            "HANDLE_RESULT as \"handleResult\" , " +
            "HANDLE_STATUS as \"handleStatus\" , " +
            "PROOF_PROV as \"proofProv\" , " +
            "CONCERT_END_TIME as \"concertEndTime\" , " +
            "REMAIN_TIME as \"remainTime\" , " +
            "HANDLE_REMAIN_TIME as \"handleRemainTime\" , " +
            "OPERATE_TYPE as \"operateType\" , " +
            "OPERATE_EXPLAIN as \"operateExplain\" , " +
            "REAL_PASS_ID as \"realPassId\" , " +
            "DISCOUNT_TYPE as \"discountType\" , " +
            "BACK_TYPE as \"backType\" , " +
            "OPERATOR as \"operator\" ," +
            "REMARK as \"remark\" from TBL_PURSUES_LIST_RECORD  " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and END_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and END_TIME &lt;= #{endTime} " +
            "</when>"+
            "<when test='vehPlate != null'>" +
            "and VEH_PLATE like '%' || #{vehPlate} || '%' " +
            "</when> " +
            "order by END_TIME" +
            "</script>"})
    List<Map> selectPursuesList(@Param("startTime") Date startTime, @Param("endTime") Date endTime,@Param("vehPlate") String vehPlate);

}
