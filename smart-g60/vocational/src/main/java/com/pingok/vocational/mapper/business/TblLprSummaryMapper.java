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

}
