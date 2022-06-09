package com.pingok.vocational.mapper.roster;

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
public interface TblPursuesListRecordMapper {

    @Select({"<script>" +
            "select a.CONCERT_ID as \"concertId\" , " +
            "a.PASS_ID as \"passId\" , " +
            "a.INITIATOR_PROV as \"initiatorProv\" , " +
            "a.ROAD as \"road\" , " +
            "a.VEH_PLATE as \"vehPlate\" , " +
            "c.DICT_LABEL as \"vehColor\"," +
            "a.ETC_CARD_ID as \"etcCardId\" , " +
            "d.DICT_LABEL as \"mediaType\" , " +
            "a.MEDIA_NO as \"mediaNo\" , " +
            "a.FEE as \"fee\" , " +
            "to_char(a.END_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"endTime\" , " +
            "to_char(a.SUBMIT_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"submitTime\" , " +
            "to_char(a.STATUS_UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"statusUpdateTime\" , " +
            "to_char(a.ROAD_UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"roadUpdateTime\" , " +
            "b.DICT_LABEL as \"vehClass\"," +
            "a.LAST_GANTRY_DE as \"lastGantryDe\" , " +
            "a.LAST_GANTRY_PROV as \"lastGantryProv\" , " +
            "to_char(a.LAST_GANTRY_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"lastGantryTime\" , " +
            "e.DICT_LABEL as \"circulationStatus\" , " +
            "f.DICT_LABEL as \"uploadStatus\" , " +
            "a.UPLOAD_DESCRIBE as \"uploadDescribe\" , " +
            "a.HANDLE_RESULT as \"handleResult\" , " +
            "g.DICT_LABEL as \"handleStatus\" , " +
            "a.PROOF_PROV as \"proofProv\" , " +
            "to_char(a.CONCERT_END_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"concertEndTime\" , " +
            "a.REMAIN_TIME as \"remainTime\" , " +
            "a.HANDLE_REMAIN_TIME as \"handleRemainTime\" , " +
            "h.DICT_LABEL as \"operateType\" , " +
            "a.OPERATE_EXPLAIN as \"operateExplain\" , " +
            "a.REAL_PASS_ID as \"realPassId\" , " +
            "i.DICT_LABEL as \"discountType\" , " +
            "j.DICT_LABEL as \"backType\" , " +
            "a.OPERATOR as \"operator\"  from TBL_PURSUES_LIST_RECORD a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.VEH_CLASS and b.DICT_TYPE='veh_class' " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.VEH_COLOR and c.DICT_TYPE='veh_color' " +
            "left join  SYS_DICT_DATA d on d.DICT_VALUE=a.MEDIA_TYPE and d.DICT_TYPE='media_type' " +
            "left join  SYS_DICT_DATA e on e.DICT_VALUE=a.CIRCULATION_STATUS and e.DICT_TYPE='pursues_circulation_status' " +
            "left join  SYS_DICT_DATA f on f.DICT_VALUE=a.UPLOAD_STATUS and f.DICT_TYPE='pursues_upload_status' " +
            "left join  SYS_DICT_DATA g on g.DICT_VALUE=a.HANDLE_STATUS and g.DICT_TYPE='pursues_handle_status' " +
            "left join  SYS_DICT_DATA h on h.DICT_VALUE=a.OPERATE_TYPE and h.DICT_TYPE='pursues_operate_type' " +
            "left join  SYS_DICT_DATA i on i.DICT_VALUE=a.DISCOUNT_TYPE and i.DICT_TYPE='pursues_discount_type' " +
            "left join  SYS_DICT_DATA j on j.DICT_VALUE=a.BACK_TYPE and j.DICT_TYPE='pursues_back_type' " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and a.END_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and a.END_TIME &lt;= #{endTime} " +
            "</when>"+
            "order by a.END_TIME" +
            "</script>"})
    List<Map> selectPursuesList(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
