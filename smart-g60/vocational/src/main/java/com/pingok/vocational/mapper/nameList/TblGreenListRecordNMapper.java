package com.pingok.vocational.mapper.nameList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
public interface TblGreenListRecordNMapper {
    @Select("SELECT GREEN_ID as \"greenId\", " +
            "CASE WHEN VEHICLE_SIGN='0x02' THEN " +
            "'绿通车' " +
            "WHEN VEHICLE_SIGN='0x03' THEN " +
            "'联合收割机' " +
            "END AS \"vehicleSign\",VEHICLE_ID as \"vehicleId\", " +
            "to_char(END_TRANSPORT_TIME,'yyyy-mm-dd hh24:mi:ss') as \"endTransTime\", " +
            "to_char(APPOINTMENT_TIME,'yyyy-mm-dd hh24:mi:ss') as \"appointSubmitTime\",a.DICT_LABEL as \"enProv\",b.DICT_LABEL as \"exProv\", " +
            "PHONE as \"phone\",APPOINT_TIME as \"appointTime\" " +
            "FROM TBL_GREEN_LIST_RECORD  " +
            "LEFT JOIN SYS_DICT_DATA a on a.DICT_VALUE=START_DISTRICT_ID AND a.DICT_TYPE='province_id' " +
            "LEFT JOIN SYS_DICT_DATA b on b.DICT_VALUE=END_DISTRICT_ID AND b.DICT_TYPE='province_id' " +
            "where VERSION_ID = #{id} ")
    List<Map> findById(@Param("id") Long id);
}
