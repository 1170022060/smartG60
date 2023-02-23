package com.pingok.vocational.mapper.business;

import com.pingok.vocational.domain.business.vo.LprSummaryVo;
import com.pingok.vocational.domain.business.vo.SummaryVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_LPR_SUMMARY 数据层
 *
 * @author xia
 */
public interface TblLprSummaryMapper {

    @Select({"<script>" +
            "select to_char(a.EN_TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"enTransTime\" ," +
            "a.EN_TRANS_NUMBER as \"enTransNumber\" ," +
            "d.LANE_NAME as \"enLaneGb\" ," +
            "a.EN_VEH_PLATE as \"enVehPlate\" ," +
            "f.DICT_LABEL as \"enVehColor\" ," +
            "to_char(a.EX_TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"exTransTime\" ," +
            "a.EX_TRANS_NUMBER as \"exTransNumber\" ," +
            "e.LANE_NAME as \"exLaneGb\" ," +
            "a.EX_VEH_PLATE as \"exVehPlate\" ," +
            "g.DICT_LABEL as \"exVehColor\" from TBL_LPR_SUMMARY a " +
            "left join TBL_BASE_STATION_INFO b on b.STATION_GB=SUBSTR(a.EN_LANE_GB, 1, 14) " +
            "left join TBL_BASE_STATION_INFO c on c.STATION_GB=SUBSTR(a.EX_LANE_GB, 1, 14) " +
            "left join TBL_LANE_INFO d on d.LANE_GB=a.EN_LANE_GB " +
            "left join TBL_LANE_INFO e on e.LANE_GB=a.EX_LANE_GB " +
            "left join  SYS_DICT_DATA f on f.DICT_VALUE=a.EN_VEH_COLOR and f.DICT_TYPE='veh_color' " +
            "left join  SYS_DICT_DATA g on g.DICT_VALUE=a.EX_VEH_COLOR and g.DICT_TYPE='veh_color' " +
            "where 1=1 " +
            "<when test='enStartTime != null'> " +
            "and a.EN_TRANS_TIME &gt;= #{enStartTime} " +
            "</when>"+
            "<when test='enEndTime != null'> " +
            "and a.EN_TRANS_TIME &lt;= #{enEndTime} " +
            "</when>" +
            "<when test='enStationId != null'> " +
            "and b.STATION_ID= #{enStationId} " +
            "</when>"+
            "<when test='enLaneType != null'> " +
            "and d.LANE_TYPE= #{enLaneType} " +
            "</when>"+
            "<when test='enVehPlate != null'> " +
            "and a.EN_VEH_PLATE= #{enVehPlate} " +
            "</when>"+
            "<when test='exStartTime != null'> " +
            "and a.EX_TRANS_TIME &gt;= #{exStartTime} " +
            "</when>"+
            "<when test='exEndTime != null'> " +
            "and a.EX_TRANS_TIME &lt;= #{exEndTime} " +
            "</when>" +
            "<when test='exStationId != null'> " +
            "and c.STATION_ID= #{exStationId} " +
            "</when>"+
            "<when test='exLaneType != null'> " +
            "and e.LANE_TYPE= #{exLaneType} " +
            "</when>"+
            "<when test='exVehPlate != null'> " +
            "and a.EX_VEH_PLATE= #{exVehPlate} " +
            "</when>"+
            "</script>"})
    List<Map> selectLprTrans(@Param("enStartTime") Date enStartTime, @Param("enEndTime") Date enEndTime,
                             @Param("enStationId") String enStationId, @Param("enLaneType") Integer enLaneType,
                             @Param("enVehPlate") String enVehPlate,@Param("exStartTime") Date exStartTime,
                             @Param("exEndTime") Date exEndTime, @Param("exStationId") String exStationId,
                             @Param("exLaneType") Integer exLaneType, @Param("exVehPlate") String exVehPlate);

    @Select({"<script>" +
            "select to_char(a.TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"enTransTime\" ," +
            "a.TRANS_NUMBER as \"enTransNumber\" ," +
            "c.LANE_NAME as \"enLaneGb\" ," +
            "trim(a.VEH_PLATE) as \"enVehPlate\" ," +
            "d.DICT_LABEL as \"enVehColor\"  from TBL_EN_LPR_TRANS_${year} a " +
            "left join TBL_BASE_STATION_INFO b on b.STATION_GB=SUBSTR(a.LANE_GB, 1, 14) " +
            "left join TBL_LANE_INFO c on c.LANE_GB=a.LANE_GB " +
            "left join SYS_DICT_DATA d on d.DICT_VALUE=a.VEH_COLOR and d.DICT_TYPE='veh_color' " +
            "where 1=1 " +
            "<when test='enStartTime != null'> " +
            "and a.TRANS_TIME &gt;= #{enStartTime} " +
            "</when>"+
            "<when test='enEndTime != null'> " +
            "and a.TRANS_TIME &lt;= #{enEndTime} " +
            "</when>" +
            "<when test='enStationId != null'> " +
            "and b.STATION_ID= #{enStationId} " +
            "</when>"+
            "<when test='enLaneType != null'> " +
            "and c.LANE_TYPE= #{enLaneType} " +
            "</when>"+
            "<when test='enVehPlate != null'> " +
            "and trim(a.VEH_PLATE) like CONCAT(CONCAT('%',#{enVehPlate}),'%')   " +
            "</when>"+
            "</script>"})
    List<Map> selectEnLprTrans(@Param("year") String year,@Param("enStartTime") Date enStartTime, @Param("enEndTime") Date enEndTime,
                               @Param("enStationId") String enStationId, @Param("enLaneType") Integer enLaneType,
                               @Param("enVehPlate") String enVehPlate);

    @Select({"<script>" +
            "select to_char(a.TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"exTransTime\" ," +
            "a.TRANS_NUMBER as \"exTransNumber\" ," +
            "c.LANE_NAME as \"exLaneGb\" ," +
            "trim(a.VEH_PLATE) as \"exVehPlate\" ," +
            "d.DICT_LABEL as \"exVehColor\"  from TBL_EX_LPR_TRANS_${year} a " +
            "left join TBL_BASE_STATION_INFO b on b.STATION_GB=SUBSTR(a.LANE_GB, 1, 14) " +
            "left join TBL_LANE_INFO c on c.LANE_GB=a.LANE_GB " +
            "left join SYS_DICT_DATA d on d.DICT_VALUE=a.VEH_COLOR and d.DICT_TYPE='veh_color' " +
            "where 1=1 " +
            "<when test='exStartTime != null'> " +
            "and a.TRANS_TIME &gt;= #{exStartTime} " +
            "</when>"+
            "<when test='exEndTime != null'> " +
            "and a.TRANS_TIME &lt;= #{exEndTime} " +
            "</when>" +
            "<when test='exStationId != null'> " +
            "and b.STATION_ID= #{exStationId} " +
            "</when>"+
            "<when test='exLaneType != null'> " +
            "and c.LANE_TYPE= #{exLaneType} " +
            "</when>"+
            "<when test='exVehPlate != null'> " +
            "and trim(a.VEH_PLATE) like CONCAT(CONCAT('%',#{exVehPlate}),'%') " +
            "</when>"+
            "</script>"})
    List<Map> selectExLprTrans(@Param("year") String year,@Param("exStartTime") Date exStartTime, @Param("exEndTime") Date exEndTime,
                               @Param("exStationId") String exStationId, @Param("exLaneType") Integer exLaneType,
                               @Param("exVehPlate") String exVehPlate);

    @Select({"<script>" +
            "select to_char(a.EN_TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"enTransTime\" ," +
            "a.EN_TRANS_NUMBER as \"enTransNumber\" ," +
            "d.LANE_NAME as \"enLaneGb\" ," +
            "a.EN_VEH_PLATE as \"enVehPlate\" ," +
            "f.DICT_LABEL as \"enVehColor\" ," +
            "to_char(a.EX_TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"exTransTime\" ," +
            "a.EX_TRANS_NUMBER as \"exTransNumber\" ," +
            "e.LANE_NAME as \"exLaneGb\" ," +
            "a.EX_VEH_PLATE as \"exVehPlate\" ," +
            "g.DICT_LABEL as \"exVehColor\" from TBL_LPR_SUMMARY a " +
            "left join TBL_BASE_STATION_INFO b on b.STATION_GB=SUBSTR(a.EN_LANE_GB, 1, 14) " +
            "left join TBL_BASE_STATION_INFO c on c.STATION_GB=SUBSTR(a.EX_LANE_GB, 1, 14) " +
            "left join TBL_LANE_INFO d on d.LANE_GB=a.EN_LANE_GB " +
            "left join TBL_LANE_INFO e on e.LANE_GB=a.EX_LANE_GB " +
            "left join  SYS_DICT_DATA f on f.DICT_VALUE=a.EN_VEH_COLOR and f.DICT_TYPE='veh_color' " +
            "left join  SYS_DICT_DATA g on g.DICT_VALUE=a.EX_VEH_COLOR and g.DICT_TYPE='veh_color' " +
            "where 1=1 " +
            "<when test='enStartTime != null'> " +
            "and a.EN_TRANS_TIME &gt;= #{enStartTime} " +
            "</when>"+
            "<when test='enEndTime != null'> " +
            "and a.EN_TRANS_TIME &lt;= #{enEndTime} " +
            "</when>" +
            "<when test='enStationId != null'> " +
            "and b.STATION_ID= #{enStationId} " +
            "</when>"+
            "<when test='enLaneType != null'> " +
            "and d.LANE_TYPE= #{enLaneType} " +
            "</when>"+
            "<when test='enVehPlate != null'> " +
            "and a.EN_VEH_PLATE= #{enVehPlate} " +
            "</when>"+
            "<when test='exStartTime != null'> " +
            "and a.EX_TRANS_TIME &gt;= #{exStartTime} " +
            "</when>"+
            "<when test='exEndTime != null'> " +
            "and a.EX_TRANS_TIME &lt;= #{exEndTime} " +
            "</when>" +
            "<when test='exStationId != null'> " +
            "and c.STATION_ID= #{exStationId} " +
            "</when>"+
            "<when test='exLaneType != null'> " +
            "and e.LANE_TYPE= #{exLaneType} " +
            "</when>"+
            "<when test='exVehPlate != null'> " +
            "and a.EX_VEH_PLATE= #{exVehPlate} " +
            "</when>"+
            "</script>"})
    List<LprSummaryVo> selectLprTransList(SummaryVo summaryVo);

    @Select({"<script>" +
            "SELECT  " +
            "to_char(en.OP_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"enTransTime\", " +
            "en.ID as \"enTransNumber\", " +
            "lane.LANE_NAME as \"enLaneGb\", " +
            "SUBSTR(en.VEHICLE_ID, 1, INSTR(en.VEHICLE_ID, '_')-1) as \"enVehPlate\", " +
            "color.DICT_LABEL as \"enVehColor\" " +
            "FROM TBL_SHAR_SVID_RES_SENDER_${year} en " +
            "LEFT JOIN TBL_LANE_INFO lane on lane.LANE_GB = en.LANE_NUM " +
            "left join TBL_BASE_STATION_INFO a on a.STATION_GB=SUBSTR(en.LANE_NUM, 1, 14) " +
            "LEFT JOIN SYS_DICT_DATA color on color.DICT_VALUE=SUBSTR(en.VEHICLE_ID, INSTR(en.VEHICLE_ID, '_')+1,LENGTH(en.VEHICLE_ID)) and color.DICT_TYPE='veh_color' " +
            "WHERE lane.LANE_TYPE in (1,3) " +
            "<when test='enStartTime != null'> " +
            "and en.OP_TIME &gt;= #{enStartTime} " +
            "</when>"+
            "<when test='enEndTime != null'> " +
            "and en.OP_TIME &lt;= #{enEndTime} " +
            "</when>" +
            "<when test='enStationId != null'> " +
            "and a.STATION_ID= #{enStationId} " +
            "</when>"+
            "<when test='enLaneType != null'> " +
            "and lane.LANE_TYPE= #{enLaneType} " +
            "</when>"+
            "<when test='enVehPlate != null'> " +
            "and SUBSTR(en.VEHICLE_ID, 1, INSTR(en.VEHICLE_ID, '_')-1) like CONCAT(CONCAT('%',#{enVehPlate}),'%')   " +
            "</when>"+
            "</script>"})
        List<Map> selectLprInfoEn(@Param("year") String year,@Param("enStartTime") Date enStartTime, @Param("enEndTime") Date enEndTime,
                                  @Param("enStationId") String enStationId, @Param("enLaneType") Integer enLaneType,
                                  @Param("enVehPlate") String enVehPlate);

    @Select({"<script>" +
            "SELECT " +
            "to_char(ex.OP_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"exTransTime\", " +
            "ex.ID as \"exTransNumber\", " +
            "lane.LANE_NAME as \"exLaneGb\", " +
            "SUBSTR(ex.VEHICLE_ID, 1, INSTR(ex.VEHICLE_ID, '_')-1) as \"exVehPlate\", " +
            "color.DICT_LABEL as \"exVehColor\" " +
            "FROM TBL_SHAR_SVID_RES_SENDER_${year} ex " +
            "LEFT JOIN TBL_LANE_INFO lane on lane.LANE_GB = ex.LANE_NUM " +
            "left join TBL_BASE_STATION_INFO a on a.STATION_GB=SUBSTR(ex.LANE_NUM, 1, 14) " +
            "LEFT JOIN SYS_DICT_DATA color on color.DICT_VALUE=SUBSTR(ex.VEHICLE_ID, INSTR(ex.VEHICLE_ID, '_')+1,LENGTH(ex.VEHICLE_ID)) and color.DICT_TYPE='veh_color' " +
            "WHERE lane.LANE_TYPE in (2,4) "+
            "<when test='exStartTime != null'> " +
            "and ex.OP_TIME &gt;= #{exStartTime} " +
            "</when>"+
            "<when test='exEndTime != null'> " +
            "and ex.OP_TIME &lt;= #{exEndTime} " +
            "</when>" +
            "<when test='exStationId != null'> " +
            "and a.STATION_ID= #{exStationId} " +
            "</when>"+
            "<when test='exLaneType != null'> " +
            "and lane.LANE_TYPE= #{exLaneType} " +
            "</when>"+
            "<when test='exVehPlate != null'> " +
            "and SUBSTR(ex.VEHICLE_ID, 1, INSTR(ex.VEHICLE_ID, '_')-1 like CONCAT(CONCAT('%',#{exVehPlate}),'%') " +
            "</when>"+
            "</script>"})
    List<Map> selectLprInfoEx(@Param("year") String year,@Param("exStartTime") Date exStartTime, @Param("exEndTime") Date exEndTime,
                              @Param("exStationId") String exStationId, @Param("exLaneType") Integer exLaneType,
                              @Param("exVehPlate") String exVehPlate);
}
