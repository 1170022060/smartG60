package com.pingok.monitor.mapper.device;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_BASE_STATION_INFO 数据层
 *
 * @author xia
 */
public interface TblBaseStationInfoMapper{

    @Select("select ID as \"id\" ," +
            "STATION_NAME as \"stationName\" ," +
            "STATION_GB as \"stationGb\" ," +
            "STATION_HEX as \"stationHex\"  from TBL_BASE_STATION_INFO " +
            "where STATION_HEX like CONCAT(CONCAT('%','310108'),'%') and STATION_HEX !='31010804' ")
    List<Map> selectBaseStation();

}
