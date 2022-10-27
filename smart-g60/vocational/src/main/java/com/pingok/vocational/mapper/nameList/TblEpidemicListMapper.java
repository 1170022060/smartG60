package com.pingok.vocational.mapper.nameList;

import com.pingok.vocational.domain.nameList.TblEpidemicStationUsed;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Mapper
public interface TblEpidemicListMapper extends CommonRepository<TblEpidemicStationUsed> {
    @Select({"<script>"+
            "select * from ( "+
            "SELECT " +
            "ev.ID as \"id\"," +
            "esu.STATION_HEX as \"stationHex\"," +
            "bsi.STATION_NAME as \"stationName\"," +
            "esu.VERSION as \"version\"," +
            "elr.REGION_NAME as \"regionName\", " +
            "to_char(esu.APPLY_TIME,'yyyy-mm-dd hh24:mi:ss') as \"applyTime\"," +
            "row_number() over(partition by esu.STATION_HEX  order by esu.VERSION desc) as \"rn\"  "+
            "from TBL_EPIDEMIC_STATION_USED esu  " +
            "LEFT JOIN TBL_BASE_STATION_INFO bsi on bsi.STATION_HEX = esu.STATION_HEX  " +
            "LEFT JOIN TBL_EPIDEMIC_VERSION ev on ev.VERSION = esu.VERSION  " +
            "LEFT JOIN TBL_EPIDEMIC_LIST_RECORD elr on elr.VERSION_ID = ev.ID  " +
            ") a where a.\"rn\"=1 " +
            "<when test='stationName != null'> " +
            "and a.\"stationName\" like '%' || #{stationName} || '%' " +
            "</when> "+
            "<when test='version != null'> " +
            "and a.\"version\" like '%' || #{version} || '%' " +
            "</when> "+
            "</script>"})
    List<Map> getEpidemicList(@Param("stationName") String stationName,@Param("version") String version);

    @Select("select * from ( "+
            "SELECT " +
            "VERSION as \"version\"," +
            "row_number() over(partition by STATION_HEX  order by VERSION desc) as \"rn\"  "+
            "from TBL_EPIDEMIC_STATION_USED ) a")
    List<Map> getLatestEStation();

    @Select("SELECT MAX(VERSION) as \"version\" FROM TBL_EPIDEMIC_STATION_USED ")
    Object getLatestESVersion();
}
