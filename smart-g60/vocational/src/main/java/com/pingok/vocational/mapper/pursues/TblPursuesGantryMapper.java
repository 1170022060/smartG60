package com.pingok.vocational.mapper.pursues;

import com.pingok.vocational.domain.pursues.TblPursuesGantry;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TblPursuesGantryMapper extends CommonRepository<TblPursuesGantry> {
    @Select("select " +
            "VEHICLE_PLATE as \"vehiclePlate\" , " +
            "VEHICLE_TYPE as \"vehicleType\" , " +
            "GANTRY_ID as \"gantryId\" , " +
            "MEDIA_TYPE as \"mediaType\" , " +
            "OBUSN as \"obuSn\" , " +
            "TRANS_TIME as \"transTime\" , " +
            "FEE as \"fee\" , " +
            "PAY_FEE as \"payFee\" , " +
            "DISCOUNT_FEE as \"discountFee\" , " +
            "TRANS_FEE as \"transFee\" from TBL_GANTRY_TRANSACTION " +
            "where PASS_ID= #{passId} order by TRANS_TIME desc ")
    List<TblPursuesGantry> selectGantryByPassId(@Param("passId") String passId);

    @Select({"<script>" +
            "select " +
            "IMAGE as \"image\" from TBL_GANTRY_PICTURE " +
            "where VEHICLE_PLATE= #{vehPlate} " +
            "and PIC_TIME &gt;= #{time}-10/24/60/60 " +
            "and PIC_TIME &lt;= #{time}+10/24/60/60 " +
            "and rownum=1 " +
            "</script>"})
    String selectGantryImageByVeh(@Param("vehPlate") String vehPlate,@Param("time") Date time);

    @Select("select " +
            "DEVICE_NAME as \"deviceName\" from TBL_GANTRY_INFO " +
            "where DEVICE_ID= #{gantryId} ")
    String selectGantryName(@Param("gantryId") String gantryId);

    @Select({"<script>" +
            "select " +
            "substr(a.VEHICLE_PLATE,1,instr(a.VEHICLE_PLATE,'_')-1) as \"vehiclePlate\" , " +
            "b.DICT_LABEL as \"vehicleColor\" , " +
            "c.DICT_LABEL as \"vehicleType\" , " +
            "a.GANTRY_ID as \"gantryId\" , " +
            "d.DEVICE_NAME as \"gantryName\" , " +
            "d.DICT_LABEL as \"mediaType\" , " +
            "a.OBU_SN as \"obuSn\" , " +
            "to_char(a.TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"transTime\" ," +
            "a.FEE as \"fee\" , " +
            "a.PAY_FEE as \"payFee\" , " +
            "a.DISCOUNT_FEE as \"discountFee\" , " +
            "a.TRANS_FEE as \"transFee\" from TBL_PURSUES_GANTRY a " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=substr(a.VEHICLE_PLATE,instr(a.VEHICLE_PLATE,'_')+1,length(a.VEHICLE_PLATE)-instr(a.VEHICLE_PLATE,'_')) and b.DICT_TYPE='veh_color' " +
            "left join SYS_DICT_DATA c on c.DICT_VALUE=a.VEHICLE_TYPE and c.DICT_TYPE='veh_class' " +
            "left join SYS_DICT_DATA d on d.DICT_VALUE=a.MEDIA_TYPE and d.DICT_TYPE='media_type' " +
            "left join TBL_GANTRY_INFO d on d.DEVICE_ID=a.GANTRY_ID " +
            "where 1=1 " +
            "<when test='concertId != null'> " +
            "and a.CONCERT_ID = #{concertId} " +
            "</when>" +
            "order by a.TRANS_TIME " +
            "</script>"})
    List<Map> selectPursuesGantry(@Param("concertId") String concertId);
}
