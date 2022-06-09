package com.pingok.vocational.mapper.roster;

import com.pingok.vocational.domain.roster.TblVehicleRecord;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_VEHICLE_RECORD 数据层
 *
 * @author xia
 */
public interface TblVehicleRecordMapper extends CommonRepository<TblVehicleRecord> {
    @Select({"<script>" +
            "select a.PASS_ID as \"passId\" ," +
            "a.GID as \"gid\" ," +
            "a.TRANSACTION_ID as \"transactionId\" ," +
            "a.EN_STATION_ID as \"enStationId\" ," +
            "a.EN_STATION_NAME as \"enStationName\" ," +
            "a.EX_STATION_ID as \"exStationId\" ," +
            "a.EX_STATION_NAME as \"exStationName\" ," +
            "a.GANTRY_ID as \"gantryId\" ," +
            "a.VEH_PHOTO as \"vehPhoto\" ," +
            "a.VEH_PLATE as \"vehPlate\" ," +
            "b.DICT_LABEL as \"vehClass\"," +
            "c.DICT_LABEL as \"vehColor\"," +
            "d.DICT_LABEL as \"vehStatus\"," +
            "to_char(a.EN_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"enTime\" ," +
            "to_char(a.EX_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"exTime\" ," +
            "e.DICT_LABEL as \"mediaType\"," +
            "a.MEDIA_NO as \"mediaNo\" ," +
            "a.TOTAL_PAY as \"totalPay\" ," +
            "a.TOTAL_DISCOUNT as \"totalDiscount\" ," +
            "a.TOTAL_FEE as \"totalFee\" from TBL_VEHICLE_RECORD a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.VEH_CLASS and b.DICT_TYPE='veh_class' " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.VEH_COLOR and c.DICT_TYPE='veh_color' " +
            "left join  SYS_DICT_DATA d on d.DICT_VALUE=a.VEH_STATUS and d.DICT_TYPE='veh_status' " +
            "left join  SYS_DICT_DATA e on e.DICT_VALUE=a.MEDIA_TYPE and e.DICT_TYPE='media_type' " +
            "where a.VEH_PLATE = #{vehPlate} " +
            "where 1=1 " +
            "<when test='vehPlate != null'> " +
            " and a.VEH_PLATE = #{vehPlate} " +
            "</when>"+
            " order by a.EX_TIME desc" +
            "</script>"})
    List<Map> selectVehicleRecord(@Param("vehPlate") String vehPlate);
}
