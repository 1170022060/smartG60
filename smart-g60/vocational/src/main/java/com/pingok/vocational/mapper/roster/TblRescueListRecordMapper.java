package com.pingok.vocational.mapper.roster;

import com.pingok.vocational.domain.roster.TblRescueListRecord;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_RESCUE_LIST_RECORD 数据层
 *
 * @author xia
 */
public interface TblRescueListRecordMapper extends CommonRepository<TblRescueListRecord> {

    @Select({"<script>" +
            "select a.VEH_PLATE as \"vehPlate\"," +
            "d.DICT_LABEL as \"vehClass\"," +
            "e.DICT_LABEL as \"vehColor\"," +
            "a.DISCOUNT_TYPE as \"discountType\" ," +
            "a.DISCOUNT_EXPLAIN as \"discountExplain\" ," +
            "a.DISCOUNT_MODE as \"discountMode\" ," +
            "a.DISCOUNT_REBATE as \"discountRebate\" ," +
            "a.HANDLE_SIDE as \"handleSide\" ," +
            "to_char(a.RESERVE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"reserveTime\" ," +
            "a.CARD_ID as \"cardId\" ," +
            "a.DISCOUNT_FEE as \"discountFee\" ," +
            "a.DISCOUNT_QUOTA as \"discountQuota\" ," +
            "to_char(a.START_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"startTime\" ," +
            "to_char(a.END_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"endTime\" ," +
            "b.STATION_NAME as \"enStation\" ," +
            "c.STATION_NAME as \"exStation\" from  TBL_RESCUE_LIST_RECORD a " +
            "left join TBL_BASE_STATION_INFO b on UPPER(b.STATION_HEX)=UPPER(a.EN_STATION) " +
            "left join TBL_BASE_STATION_INFO c on UPPER(c.STATION_HEX)=UPPER(a.EX_STATION) " +
            "left join  SYS_DICT_DATA d on d.DICT_VALUE=a.VEH_CLASS and d.DICT_TYPE='veh_class' " +
            "left join  SYS_DICT_DATA e on e.DICT_VALUE=a.VEH_COLOR and e.DICT_TYPE='veh_color' " +
            "where 1=1 " +
            "<when test='vehPlate != null'> " +
            " and a.VEH_PLATE like '%' || #{vehPlate} || '%' " +
            "</when>"+
            "<when test='startTime != null'> " +
            "and a.START_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            "and a.END_TIME &lt;= #{endTime} " +
            "</when>" +
            "order by END_TIME" +
            "</script>"})
    List<Map> selectRescueList(@Param("vehPlate") String vehPlate, @Param("startTime") Date startDate, @Param("endTime")  Date endTime);
}
