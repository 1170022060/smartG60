package com.pingok.vocational.mapper.business;

import com.pingok.vocational.domain.business.TblSectorLog;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_SECTOR_LOG 数据层
 *
 * @author xia
 */
public interface TblSectorLogMapper extends CommonRepository<TblSectorLog> {

    @Select({"<script>" +
            "select a.GID as \"gid\" ," +
            "b.LANE_NAME as \"laneName\" ," +
            "c.DICT_LABEL as \"passType\" ," +
            "to_char(a.TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"transTime\"  from TBL_SECTOR_LOG a " +
            "left join TBL_LANE_INFO b on b.LANE_GB=SUBSTR(a.GID, 1, 21) " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=to_char(a.PASS_TYPE) and c.DICT_TYPE='pass_type_log' " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and a.TRANS_TIME &gt;= #{startTime} " +
            "</when>" +
            "<when test='endTime != null'> " +
            " and a.TRANS_TIME &lt;= #{endTime} " +
            "</when>" +
            "<when test='gid != null'> " +
            "and a.GID= #{gid} " +
            "</when>" +
            "<when test='laneId != null'> " +
            "and SUBSTR(b.LANE_HEX, 5, 6)= #{laneId} " +
            "</when>" +
            "<when test='passType != null'> " +
            "and a.PASS_TYPE= #{passType} " +
            "</when>" +
            "order by a.TRANS_TIME" +
            "</script>"})
    List<Map> selectSectorLog(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("gid") String gid, @Param("laneId") String laneId, @Param("passType") Integer passType);

    @Select("select a.VEH_PLATE as \"vehPlate\" ," +
            "c.DICT_LABEL as \"vehColor\" ," +
            "b.DICT_LABEL as \"vehClass\" ," +
            "d.DICT_LABEL as \"vehUserType\" ," +
            "a.VEH_LWH as \"vehLwh\" ," +
            "a.WHEELS as \"wheels\" ," +
            "a.AXLE as \"axle\" ," +
            "a.WHEELBASE as \"wheelbase\" ," +
            "a.LOAD_OR_SEATS as \"loadOrSeats\" ," +
            "a.VEH_DESCRIBE as \"vehDescribe\" ," +
            "a.VEH_ENGINE_NO as \"vehEngineNo\"  from TBL_OBU_EF01_LOG a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.VEH_CLASS and b.DICT_TYPE='veh_class' " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.VEH_COLOR and c.DICT_TYPE='veh_color' " +
            "left join  SYS_DICT_DATA d on d.DICT_VALUE=a.VEH_USER_TYPE and d.DICT_TYPE='veh_user_type' " +
            "where a.LOG_ID = #{logId} ")
    Map selectObuEf01Log(@Param("logId") Long logId);

    @Select("select ISSUER_IDENTITY as \"issuerIdentity\" ," +
            "d.DICT_LABEL as \"cardType\" ," +
            "CARD_VERSION as \"cardVersion\" ," +
            "CARD_NET as \"cardNet\" ," +
            "CARD_ID as \"cardId\" ," +
            "ENABLE_TIME as \"enableTime\" ," +
            "EXPIRATION_TIME as \"expirationTime\" ," +
            "VEH_PLATE as \"vehPlate\" ," +
            "e.DICT_LABEL as \"userType\" ," +
            "c.DICT_LABEL as \"vehColor\" ," +
            "b.DICT_LABEL as \"vehClass\" from TBL_CPU_0015_LOG a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.VEH_CLASS and b.DICT_TYPE='veh_class' " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.VEH_COLOR and c.DICT_TYPE='veh_color' " +
            "left join  SYS_DICT_DATA d on d.DICT_VALUE=a.CARD_TYPE and d.DICT_TYPE='etc_card_type' " +
            "left join  SYS_DICT_DATA e on e.DICT_VALUE=a.USER_TYPE and e.DICT_TYPE='user_type' " +
            "where a.LOG_ID = #{logId} ")
    Map selectCpu0015Log(@Param("logId") Long logId);

    @Select("select a.CONSUME_SIGN as \"consumeSign\" ," +
            "a.LENGTH as \"length\" ," +
            "d.DICT_LABEL as \"lockSign\" ," +
            "a.NET as \"net\" ," +
            "a.STATION as \"station\" ," +
            "a.LANE as \"lane\" ," +
            "a.TRANS_TIME as \"transTime\" ," +
            "g.STATION_NAME as \"stationName\" , " +
            "b.DICT_LABEL as \"vehClass\" ," +
            "e.DICT_LABEL as \"transType\" ," +
            "GANTRY_HEX as \"gantryHex\" ," +
            "GANTRY_TIME as \"gantryTime\" ," +
            "a.VEH_PLATE as \"vehPlate\" ," +
            "c.DICT_LABEL as \"vehColor\" ," +
            "a.TRUCK_AXLE as \"truckAxle\" ," +
            "a.TRUCK_WEIGHT as \"truckWeight\" ," +
            "f.DICT_LABEL as \"vehSign\" ," +
            "a.FEE as \"fee\" from TBL_CPU_0019_LOG a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.VEH_CLASS and b.DICT_TYPE='veh_class' " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.VEH_COLOR and c.DICT_TYPE='veh_color' " +
            "left join  SYS_DICT_DATA d on d.DICT_VALUE=a.LOCK_SIGN and d.DICT_TYPE='lock_sign' " +
            "left join  SYS_DICT_DATA e on e.DICT_VALUE=a.TRANS_TYPE and e.DICT_TYPE='lane_type' " +
            "left join  SYS_DICT_DATA f on f.DICT_VALUE=a.VEH_SIGN and f.DICT_TYPE='veh_sign' " +
            "left join TBL_BASE_STATION_INFO g on g.STATION_HEX=CONCAT(a.NET,a.STATION) " +
            "where a.LOG_ID = #{logId} ")
    Map selectCpu0019Log(@Param("logId") Long logId);

    @Select("select b.DICT_LABEL as \"provId\" ," +
            "a.PROV_FEE as \"provFee\"  from TBL_OBU_EF04_LOG_PROV a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.PROV_ID and b.DICT_TYPE='province_id' " +
            "where a.LOG_ID = #{logId} ")
    List<Map> selectObuEf04LogProv(@Param("logId") Long logId);

    @Select("select b.DICT_LABEL as \"provinceId\" ," +
            "a.GANTRY_COUNT as \"gantryCount\" ," +
            "a.FEE as \"fee\" ," +
            "a.MILEAGE as \"mileage\" ," +
            "a.EN_ETC_GANTRY_HEX as \"enEtcGantryHex\" ," +
            "a.EN_TIME as \"enTime\" ," +
            "a.LAST_ETC_GANTRY_HEX as \"lastEtcGantryHex\" ," +
            "a.LAST_PASS_TIME as \"lastPassTime\" ," +
            "a.FIT_FLAG as \"fitFlag\"  from TBL_CPC_EF04_LOG a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.PROVINCE_ID and b.DICT_TYPE='province_id' " +
            "where a.LOG_ID = #{logId} ")
    Map selectCpcEf04Log(@Param("logId") Long logId);
}
