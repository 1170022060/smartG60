package com.pingok.vocational.mapper.nameList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
public interface TblGreenStationUsedMapper {
    @Select({"<script>"+
            "select * from ( "+
            "SELECT " +
            "NVL(gv.ID,0) as \"id\"," +
            "gsu.STATION_HEX as \"stationHex\"," +
            "bsi.STATION_NAME as \"stationName\"," +
            "gsu.VERSION as \"version\"," +
            "to_char(gsu.APPLY_TIME,'yyyy-mm-dd hh24:mi:ss') as \"applyTime\"," +
            "row_number() over(partition by gsu.STATION_HEX  order by gsu.VERSION desc) as \"rn\"  "+
            "from TBL_GREEN_STATION_USED gsu  " +
            "LEFT JOIN TBL_BASE_STATION_INFO bsi on bsi.STATION_HEX = gsu.STATION_HEX  " +
            "LEFT JOIN TBL_GREEN_VERSION gv on gv.VERSION = gsu.VERSION  " +
            ") a where a.\"rn\"=1 " +
            "<when test='stationName != null'> " +
            "and a.\"stationName\" like '%' || #{stationName} || '%' " +
            "</when> "+
            "<when test='version != null'> " +
            "and a.\"version\" like '%' || #{version} || '%' " +
            "</when> "+
            "</script>"})
    List<Map> getGreenList(@Param("stationName") String stationName, @Param("version") String version);
}
