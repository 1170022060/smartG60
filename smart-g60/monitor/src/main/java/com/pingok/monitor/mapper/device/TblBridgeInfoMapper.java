package com.pingok.monitor.mapper.device;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_BRIDGE_INFO 数据层
 *
 * @author xia
 */
public interface TblBridgeInfoMapper {

    @Select("SELECT " +
            "tbi.ID AS \"id\", " +
            "tbi.NAME AS \"name\", " +
            "tbi.SERIAL_NO AS \"serialNo\", " +
            "'[' || tbi.LONGITUDE || ',' || tbi.LATITUDE || ']' AS \"gps\", " +
            "tbi.ENTIRETY_RATING AS \"entiretyRating\", " +
            "tbi.COMPONENT_RATING AS \"componentRating\", " +
            "tbi.DEVICE_STATUS AS \"deviceStatus\", " +
            "tbt.TEMPERATURE AS \"temperature\", " +
            "tbw.ALARM_LEVEL AS \"alarmlevel\", " +
            "tbw.INCIDENT_DESCRIPTION AS \"incidentdescription\", " +
            "tbw.ALARM_REASON AS \"alarmReason\", " +
            "tbw.ALARM_TIME AS \"alarmTime\"  " +
            "FROM " +
            "TBL_BRIDGE_INFO tbi " +
            "LEFT JOIN TBL_BRIDGE_TEMPERATURE tbt ON tbt.DEVICE_ID = tbi.SERIAL_NO " +
            "LEFT JOIN ( " +
            "SELECT * FROM (SELECT " +
            "tbw.*, " +
            "ROW_NUMBER ( ) OVER ( PARTITION BY tbw.STRUCTURE_ID ORDER BY tbw.ALARM_TIME DESC ) RW  " +
            "FROM " +
            "TBL_BRIDGE_WARNING tbw  " +
            "WHERE " +
            "tbw.TREATMENT_STATUS = 0) tbw  " +
            "where tbw.RW = 1 " +
            ") tbw ON tbw.STRUCTURE_ID = tbi.ID")
    List<Map> selectBridgeInfo();
}
