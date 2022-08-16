package com.pingok.vocational.mapper.baseinfo;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_RATE TBL_RATE_PROV 数据层
 *
 * @author xia
 */
public interface TblRateMapper {
    @Select({"<script>" +
            "select a.ID as \"id\" ," +
            "a.EN_PROV as \"enProv\" ," +
            "a.EX_PROV as \"exProv\" ," +
            "a.VERSION as \"version\","+
            "a.EN_ID as \"enId\" ," +
            "a.EX_ID as \"exId\" ," +
            "c.STATION_NAME as \"enName\" ," +
            "d.STATION_NAME as \"exName\" ," +
            "b.DICT_LABEL as \"vehClass\"," +
            "a.FEE as \"fee\" ," +
            "a.FEE_95 as \"fee95\" ," +
            "a.M as \"m\"  from TBL_RATE a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=to_char(a.VEH_CLASS) and b.DICT_TYPE='veh_class' " +
            "left join TBL_BASE_STATION_INFO c on c.STATION_GB=a.EN_ID " +
            "left join TBL_BASE_STATION_INFO d on d.STATION_GB=a.EX_ID " +
            "where 1=1 " +
            "<when test='inStationName != null'> " +
            " and c.STATION_NAME like CONCAT(CONCAT('%',#{inStationName}),'%') " +
            "</when>"+
            "<when test='exStationId != null'> " +
            " and d.STATION_HEX like CONCAT('3101',#{exStationId})" +
            "</when>"+
            "<when test='vehClass != null'> " +
            "and a.VEH_CLASS= #{vehClass} " +
            "</when>"+
            "<when test='versionNum != null'> " +
            "and a.VERSION= #{versionNum} " +
            "</when>"+
            " order by a.EN_ID,a.VEH_CLASS "+
            "</script>"})
    List<Map> selectRate(@Param("inStationName") String inStationName, @Param("exStationId") String exStationId,@Param("vehClass") Integer vehClass,@Param("versionNum") String versionNum);

    @Select({"<script>" +
            "select a.P_INDEX as \"pIndex\" ," +
            "a.PROV as \"prov\" ," +
            "b.DICT_LABEL as \"provName\" ," +
            "a.P_FEE as \"pFee\" ," +
            "a.P_FEE_95 as \"pFee95\" ," +
            "a.P_M as \"pM\" from TBL_RATE_PROV a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.PROV and b.DICT_TYPE='province_id' " +
            "where a.RATE_ID = #{rateId} " +
            " order by a.P_INDEX "+
            "</script>"})
    List<Map> selectRateProv(@Param("rateId") Long rateId);

    @Select({"select STATION_HEX as \"id\",STATION_NAME as \"stationName\" from TBL_BASE_STATION_INFO info"+
            " order by info.STATION_HEX "
    })
    List<Map> selectInStation();

    @Select({"select distinct VERSION as \"versionNum\" from TBL_RATE a"+
            " order by a.VERSION "
    })
    List<Map> selectVersionNum();
}
