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
            "tfi.FIELD_NAME as \"fieldName\"," +
            "tps.VEH_TYPE as \"vehType\"," +
            "sum(tps.ENTER) as \"dailyCumulative\" from TBL_PARKING_STATISTICS tps " +
            "left join TBL_FIELD_INFO tfi on tfi.ID=tps.FIELD_ID " +
            "where tps.DAY = #{date} " +
            "group by tfi.ID,tfi.FIELD_NAME,tps.VEH_TYPE" )
    List<Map> trafficSum(@Param("date") Date date);

    @Select("select HOUR as \"hour\"," +
            "CURRENT_NUM as \"current\"  from TBL_PARKING_STATISTICS " +
            "where DAY = #{date} " +
            "and FIELD_ID = #{fieldId} " +
            "and VEH_TYPE = #{vehType} " +
            "and rownum<=8" +
            "order by HOUR desc ")
    List<Map> trafficStatistics(@Param("date") Date date, @Param("fieldId") Long fieldId, @Param("vehType") Integer vehType);

}
