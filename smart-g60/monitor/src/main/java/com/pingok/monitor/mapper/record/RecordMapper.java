package com.pingok.monitor.mapper.record;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 *  数据层
 *
 * @author qiumin
 */
@Mapper
public interface RecordMapper {
    @Select("select * from (SELECT GANTRY_ID as \"deviceId\",LICENSE as \"license\",DIRECTION as \"direction\" FROM TBL_GANTRY_RECORD where to_char(WORK_DATE,'yyyy-MM-dd') >= #{startTime} AND to_char(WORK_DATE,'yyyy-MM-dd')<=#{endTime} ) pivot (sum(\"license\") for \"direction\" in ('1' as \"up\",'2'as \"down\" ))\n")
    List<Map> getGantryRecord(@Param("startTime") String startTime, @Param("endTime") String endTime);

    @Select("select * from (SELECT STATION_ID as \"stationId\",LICENSE as \"license\",DIRECTION as \"direction\" FROM TBL_SECTION_RECORD where to_char(WORK_DATE,'yyyy-MM-dd') >= #{startTime} AND to_char(WORK_DATE,'yyyy-MM-dd')<=#{endTime}) pivot (sum(\"license\") for \"direction\" in ('1' as \"entrance\",'2'as \"export\" ))\n")
    List<Map> getSectionRecord(@Param("startTime") String startTime, @Param("endTime") String endTime);
}
