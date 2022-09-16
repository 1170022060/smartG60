package com.pingok.vocational.mapper.parkingLot;

import com.pingok.vocational.domain.parkingLot.TblParkingStatistics;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_PARKING_STATISTICS 数据层
 *
 * @author xia
 */
public interface TblParkingStatisticsMapper extends CommonRepository<TblParkingStatistics> {

    @Select("select tfi.ID as \"fieldId\"," +
            "tfi.FIELD_NAME as \"fieldName\" from TBL_PARKING_STATISTICS tps " +
            "left join TBL_FIELD_INFO tfi on tfi.ID=tps.FIELD_ID " +
            "where tps.DAY = #{date} " +
            "group by tfi.ID,tfi.FIELD_NAME" )
    List<Map> trafficSum(@Param("date") Date date);

    @Select("select sum(ENTER) as \"dailyCumulative\" from TBL_PARKING_STATISTICS " +
            "where FIELD_ID = #{fieldId} and DAY = #{date} and VEH_TYPE = #{vehType}")
    Integer trafficDaily(@Param("date") Date date, @Param("fieldId") Long fieldId,@Param("vehType") Integer vehType);

    @Select("select \"hour\",\"current\" from " +
            "(select HOUR as \"hour\"," +
            "CURRENT_NUM as \"current\"  from TBL_PARKING_STATISTICS " +
            "where DAY = #{date} " +
            "and FIELD_ID = #{fieldId} " +
            "and VEH_TYPE = #{vehType} " +
            "and rownum<=8" +
            "order by HOUR desc) " +
            "order by \"hour\" ")
    List<Map> trafficStatistics(@Param("date") Date date, @Param("fieldId") Long fieldId, @Param("vehType") Integer vehType);

}
