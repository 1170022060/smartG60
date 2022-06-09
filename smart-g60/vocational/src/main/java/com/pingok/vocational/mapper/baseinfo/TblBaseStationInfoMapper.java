package com.pingok.vocational.mapper.baseinfo;

import com.pingok.vocational.domain.baseinfo.TblBaseStationInfo;
import com.pingok.vocational.domain.baseinfo.vo.StationInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_BASE_STATION_INFO 数据层
 *
 * @author xia
 */
public interface TblBaseStationInfoMapper extends CommonRepository<TblBaseStationInfo> {

    @Select({"<script>" +
            "select a.ID as \"id\" ," +
            "a.VERSION as \"version\" ," +
            "a.STATION_NAME as \"stationName\" ," +
            "a.STATION_GB as \"stationGb\" ," +
            "a.STATION_HEX as \"stationHex\" ," +
            "to_char(a.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "to_char(a.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\"," +
            "case when a.CREATE_USER_ID is null then null else a.CREATE_USER_ID || ':' || b.USER_NAME end as \"createUserName\"," +
            "case when a.UPDATE_USER_ID is null then null else a.UPDATE_USER_ID || ':' || c.USER_NAME end as \"updateUserName\" from TBL_BASE_STATION_INFO a " +
            "left join  SYS_USER b on a.CREATE_USER_ID=b.USER_ID " +
            "left join  SYS_USER c on a.UPDATE_USER_ID=c.USER_ID " +
            "where 1=1" +
            "<when test='stationName != null'> " +
            "and a.STATION_NAME like CONCAT(CONCAT('%',#{stationName}),'%')  " +
            "</when>"+
            "</script>"})
    List<Map> selectBaseStation(@Param("stationName") String stationName);

    @Select("select ID as \"id\",STATION_NAME as \"stationName\",STATION_ID as \"stationId\" from TBL_BASE_STATION_INFO where STATION_HEX like '%310108%'")
    List<Map> selectStationInfo();

    @Select("select 0 as \"id\",'沪杭路段中心' as \"stationName\",'08' as \"stationId\" from dual " +
            "union all " +
            "select ID as \"id\",STATION_NAME as \"stationName\",STATION_ID as \"stationId\" from TBL_BASE_STATION_INFO where STATION_HEX like '%310108%' order by \"stationId\" ")
    List<Map> selectStationCenterInfo();

    @Select("select STATION_NAME as \"stationName\",STATION_ID as \"stationId\" from TBL_BASE_STATION_INFO where STATION_HEX like '%310108%' order by STATION_HEX")
    List<StationInfo> selectStationLaneInfo();
}
