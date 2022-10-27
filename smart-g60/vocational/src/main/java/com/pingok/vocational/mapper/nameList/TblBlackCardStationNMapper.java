package com.pingok.vocational.mapper.nameList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Mapper
public interface TblBlackCardStationNMapper {
    @Select({"<script>"+
            "select * from ( "+
            "SELECT " +
            "bcv.ID as \"id\"," +
            "bsu.STATION_HEX as \"stationHex\"," +
            "bsi.STATION_NAME as \"stationName\"," +
            "bsu.VERSION as \"version\"," +
            "to_char(bsu.APPLY_TIME,'yyyy-mm-dd hh24:mi:ss') as \"applyTime\"," +
            "row_number() over(partition by bsu.STATION_HEX  order by bsu.VERSION desc) as \"rn\"  "+
            "from TBL_BLACK_CARD_STATION_USED bsu  " +
            "LEFT JOIN TBL_BASE_STATION_INFO bsi on bsi.STATION_HEX = bsu.STATION_HEX  " +
            "LEFT JOIN TBL_BLACK_CARD_VERSION bcv on bcv.VERSION = bsu.VERSION  " +
            ") a where a.\"rn\"=1 " +
            "<when test='stationName != null'> " +
            "and a.\"stationName\" like '%' || #{stationName} || '%' " +
            "</when> "+
            "<when test='version != null'> " +
            "and a.\"version\" like '%' || #{version} || '%' " +
            "</when> "+
            "</script>"})
    List<Map> getBlackCardList(@Param("stationName") String stationName, @Param("version") String version);

    @Select("select * from ( "+
            "SELECT " +
            "bsu.VERSION as \"version\"," +
            "row_number() over(partition by bsu.STATION_HEX  order by bsu.VERSION desc) as \"rn\"  "+
            "from TBL_BLACK_CARD_STATION_USED bsu ) a")
    List<Map> getLatestBCStation();

    @Select("SELECT MAX(VERSION) as \"version\" FROM TBL_BLACK_CARD_STATION_USED bcsu")
    Object getLatestBCVersion();
}
