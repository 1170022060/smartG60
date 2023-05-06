package com.pingok.monitor.mapper.baseStation;

import com.pingok.monitor.domain.baseStation.TblBaseStationInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 数据层
 *
 * @author qiumin
 */
@Mapper
public interface BaseStationInfoMapper extends CommonRepository<TblBaseStationInfo> {

    @Select("SELECT DEPT_ID FROM  SYS_DEPT CONNECT BY PRIOR DEPT_ID = PARENT_ID START WITH PARENT_ID = #{parentId}")
    List<Long> findDeptIdByParentId(@Param("parentId")Long parentId);

    @Select("select ID as \"id\",STATION_ID as \"stationId\",RECORD_NUM as \"recordNum\",STATION_NAME as \"stationName\",PILE_NO as \"pileNo\",GATE_TYPE as \"gateType\",SQUARE_TYPE as \"squareType\",GPS as \"gps\",OVERLOAD_CONTROL as \"overloadControl\",OVERLOAD_CONTROL_SITE as \"overloadControlSite\",ROAD_BELONG as \"roadBelong\",CREATE_TIME as \"createTime\",UPDATE_TIME as \"updateTime\" from TBL_BASE_STATION_INFO")
    List<Map> getMonitorInfo();

    @Select("SELECT a.\"orientation\",a.\"normal\",a.\"abnormal\",a.\"close\",b.\"specialRecord\" FROM ( " +
            "SELECT " +
            "CASE tli.LANE_TYPE  " +
            "WHEN 1 THEN " +
            "1  " +
            "WHEN 3 THEN " +
            "1  " +
            "WHEN 2 THEN " +
            "2  " +
            "WHEN 4 THEN " +
            "2  END AS \"orientation\"," +
            " SUM( CASE NVL( tls.STATUS, - 1 ) WHEN 1 THEN 1 ELSE 0 END ) AS \"normal\"," +
            " SUM( CASE NVL( tls.STATUS, - 1 ) WHEN - 1 THEN 1 ELSE 0 END ) AS \"abnormal\"," +
            " SUM( CASE NVL( tls.STATUS, - 1 ) WHEN 0 THEN 1 ELSE 0 END ) AS \"close\" " +
            "FROM " +
            "TBL_LANE_INFO tli " +
            "LEFT JOIN TBL_LANE_STATUS tls ON tli.LANE_HEX = tls.LANE_HEX " +
            "WHERE tli.LANE_TYPE IN ( 1, 2, 3, 4 )  AND tli.STATION_ID = #{stationId}  " +
            "GROUP BY " +
            "CASE tli.LANE_TYPE  " +
            "WHEN 1 THEN " +
            "1  " +
            "WHEN 3 THEN " +
            "1  " +
            "WHEN 2 THEN " +
            "2  " +
            "WHEN 4 THEN " +
            "2 END ) a  " +
            "LEFT JOIN ( " +
            "SELECT " +
            "CASE tli.LANE_TYPE  " +
            "WHEN 1 THEN " +
            "1  " +
            "WHEN 3 THEN " +
            "1  " +
            "WHEN 2 THEN " +
            "2  " +
            "WHEN 4 THEN " +
            "2  " +
            "END AS \"orientation\", " +
            "COUNT( tsr.ID ) AS \"specialRecord\"  " +
            "FROM TBL_LANE_INFO tli " +
            "LEFT JOIN TBL_SPECIAL_RECORD tsr ON tsr.LANE_ID = tli.LANE_ID AND tsr.STATION_ID = tli.STATION_ID AND tsr.STATUS = 0  " +
            "WHERE tli.LANE_TYPE IN ( 1, 2, 3, 4 ) AND tli.STATION_ID = #{stationId}  " +
            "GROUP BY " +
            "CASE tli.LANE_TYPE  " +
            "WHEN 1 THEN " +
            "1  " +
            "WHEN 3 THEN " +
            "1  " +
            "WHEN 2 THEN " +
            "2  " +
            "WHEN 4 THEN 2 END  " +
            "  ) b on b.\"orientation\" = a.\"orientation\"")
    List<Map> getStatusByStationId(@Param("stationId") String stationId);


    @Select("SELECT " +
            "tsi.ID as \"id\", " +
            "tsi.NAME as \"name\", " +
            "tsi.VERSION as \"version\", " +
            "tsi.TIME as \"time\", " +
            "tsi.STATUS as \"status\", " +
            "tsi.STATUS_CODE as \"statusCode\", "+
            "tsi.UPLOAD_FLAG AS \"uploadFlag\", " +
            "tsi.UPLOAD_STATUS AS \"uploadStatus\", " +
            "tsi.DOWNLOAD_FLAG AS \"downloadFlag\", " +
            "tsi.DOWNLOAD_STATUS AS \"downloadStatus\"  " +
            "FROM " +
            "TBL_SOFTWARE_INFO tsi " +
            "where SUBSTR( tsi.NUM, 1, LENGTH( tsi.NUM ) - 2 ) = #{stationHex} ")
    List<Map> getStatusByStationHex(@Param("stationHex") String stationHex);
}
