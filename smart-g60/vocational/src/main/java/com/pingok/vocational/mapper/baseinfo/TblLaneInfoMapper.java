package com.pingok.vocational.mapper.baseinfo;

import com.pingok.vocational.domain.baseinfo.TblLaneInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_LANE_INFO 数据层
 *
 * @author xia
 */
public interface TblLaneInfoMapper extends CommonRepository<TblLaneInfo> {

    @Select({"<script>" +
            "select a.ID as \"id\" ," +
            "a.LANE_NAME as \"laneName\" ," +
            "a.MARK_NAME as \"markName\" ," +
            "d.DICT_LABEL as \"laneType\" ," +
            "a.LANE_GB as \"laneGb\" ," +
            "a.LANE_HEX as \"laneHex\" ," +
            "a.LANE_IP as \"laneIp\" ," +
            "e.DICT_LABEL as \"roadId\" ," +
            "a.STATUS  as \"status\"," +
            "to_char(a.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "to_char(a.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\"," +
            "case when a.CREATE_USER_ID is null then null else a.CREATE_USER_ID || ':' || b.USER_NAME end as \"createUserName\"," +
            "case when a.UPDATE_USER_ID is null then null else a.UPDATE_USER_ID || ':' || c.USER_NAME end as \"updateUserName\" from TBL_LANE_INFO a " +
            "left join  SYS_USER b on a.CREATE_USER_ID=b.USER_ID " +
            "left join  SYS_USER c on a.UPDATE_USER_ID=c.USER_ID " +
            "left join  SYS_DICT_DATA d on d.DICT_VALUE=a.LANE_TYPE and d.DICT_TYPE='lane_type' " +
            "left join  SYS_DICT_DATA e on e.DICT_VALUE=a.ROAD_ID and e.DICT_TYPE='road_id' " +
            "where 1=1 " +
            "<when test='stationId != null'> " +
            "and a.Station_ID = #{stationId}  " +
            "</when>" +
            "<when test='status != null'> " +
            "and a.STATUS= #{status} " +
            "</when>"+
            " order by LANE_HEX "+
            "</script>"})
    List<Map> selectLane(@Param("stationId") String stationId, @Param("status") Integer status);

    @Select("select * from TBL_LANE_INFO where LANE_NAME= #{laneName} and rownum = 1")
    TblLaneInfo checkLaneNameUnique(@Param("laneName") String laneName);

    @Select("select SUBSTR(LANE_HEX, 5, 6) as \"laneId\",LANE_NAME as \"laneName\" from TBL_LANE_INFO where LANE_HEX like '%310108%' and STATUS=1 order by LANE_HEX")
    List<Map> selectLaneInfo();

    @Select("select LANE_ID as \"laneId\",LANE_NAME as \"laneName\" from TBL_LANE_INFO where LANE_HEX like CONCAT(CONCAT('%',CONCAT('3101',#{stationId})),'%') and STATUS=1 order by LANE_HEX")
    List<Map> selectStationLaneInfo(@Param("stationId") String stationId);
}
