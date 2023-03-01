package com.pingok.monitor.mapper.faultAlarm;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Mapper
public interface FaultAlarmMapper {
    @Select("SELECT * from ( " +
            "SELECT tbf.ID as \"id\",TO_CHAR(tbf.FAULT_TIME,'yyyy-mm-dd hh24:mi:ss') as \"faultTime\",tfi.FIELD_NAME as \"faultPlace\"," +
            "CONCAT(tbi.DEVICE_NAME,tbf.FAULT_DESCRIPTION) as \"faultContent\",1 as \"type\"  " +
            "from TBL_DEVICE_FAULT tbf  " +
            "LEFT JOIN TBL_DEVICE_INFO tbi on tbi.DEVICE_ID=tbf.DEVICE_ID  " +
            "LEFT JOIN TBL_FIELD_INFO tfi on tfi.ID=tbi.FIELD_BELONG  " +
            "WHERE tbf.STATUS = 0 and tbi.FIELD_BELONG in (3940,3941)) UNION ALL " +
            "(SELECT pvi.ID as \"id\", TO_CHAR(pvi.EN_TIME,'yyyy-mm-dd hh24:mi:ss') as \"faultTime\",tfi.FIELD_NAME as \"faultPlace\"," +
            "CONCAT(pvi.VEH_PLATE,'停车超时') as \"faultContent\",2 as \"type\"  from TBL_PARKING_VEHICLE_INFO pvi  " +
            "LEFT JOIN TBL_FIELD_INFO tfi on tfi.ID=pvi.PARKING_ID " +
            "WHERE pvi.EX_TIME IS NULL  " +
            "AND CEIL( ( SYSDATE - EN_TIME ) * 24 ) > (SELECT CONFIG_VALUE FROM  SYS_CONFIG sc WHERE sc.CONFIG_KEY='parking.timeout') AND pvi.PARKING_ID in (3940,3941) AND pvi.ALARM_STATUS =0) UNION ALL " +
            "(SELECT stc.ID as \"id\"," +
            "TO_CHAR(stc.UPDATE_TIME,'yyyy-mm-dd hh24:mi:ss') as \"faultTime\", " +
            "sti.SER_NAME as \"faultPlace\", " +
            "(CASE stc.STATUS " +
            " WHEN 2 THEN " +
            "  CONCAT(stc.POSITION, '号坑位占用超时') " +
            " ELSE " +
            "  CONCAT(stc.POSITION, '号坑位故障') " +
            "END)as \"faultContent\",3 as \"type\"  " +
            "from TBL_SMART_TOILET_CUBICLE stc LEFT JOIN TBL_SMART_TOILET_INFO sti on sti.ID=stc.SER_ID WHERE stc.STATUS in (2,3) and stc.ALARM_STATUS = 0) ORDER BY \"faultTime\" ")
    List<Map> getFaultAlarm();
}
