package com.pingok.monitor.mapper.lane;

import com.pingok.monitor.domain.lane.TblLaneStatus;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_LANE_STATUS 数据层
 *
 * @author qiumin
 */
@Mapper
public interface TblLaneStatusMapper extends CommonRepository<TblLaneStatus> {
    @Select("SELECT tls.ID AS \"id\", " +
            "tli.NET_WORK AS \"netWork\", " +
            "tli.STATION_ID AS \"stationId\", " +
            "tli.LANE_TYPE AS \"laneType\", " +
            "tli.LANE_ID AS \"laneId\", " +
            "tli.MARK_NAME AS \"markName\", " +
            "tls.LANE_HEX AS \"laneHex\", " +
            "tls.TIME AS \"time\", " +
            "tls.OPT_ID AS \"optId\", " +
            "tls.OPT_NAME AS \"optName\", " +
            "tls.CARD_CNT AS \"cardCnt\", " +
            "tls.RECEIPT_TIMES AS \"receiptTimes\", " +
            "tls.SHIFT AS \"shift\", " +
            "tls.SOFT_VER AS \"softVer\", " +
            "tls.PASS_TIMES AS \"passTimes\", " +
            "tls.CPC_TIMES AS \"cpcTimes\", " +
            "tls.RSU_TIMES AS \"rsuTimes\", " +
            "tls.SPEC_TIMES AS \"specTimes\", " +
            "tls.STATUS AS \"status\", " +
            "tls.DISKS AS \"disks\", " +
            "tls.CPU AS \"cpu\", " +
            "tls.MEMORY AS \"memory\", " +
            "tls.ERROR_TRANS AS \"errorTrans\", " +
            "tls.ERROR_LPR_TRANS AS \"errorLprTrans\", " +
            "tls.LPR_STATUS AS \"lprStatus\", " +
            "tls.CAMERA_STATUS AS \"cameraStatus\", " +
            "tls.OVER_LOAD_STATUS AS \"overLoadStatus\", " +
            "tls.NET_STATUS AS \"netStatus\", " +
            "tls.PSAM_ID AS \"psamId\", " +
            "tls.OPT_INFO_UPDATE_TIME AS \"optInfoUpdateTime\", " +
            "tls.FREE_INFO_UPDATE_TIME AS \"freeInfoUpdateTime\", " +
            "tls.TRACTOR_BLACK_VER AS \"tractorBlackVer\", " +
            "tls.CURR_VERSION AS \"currVersion\", " +
            "tls.CURR_START_TIME AS \"currStartTime\", " +
            "tls.NEXT_VERSION AS \"nextVersion\", " +
            "tls.NEXT_VER_START_TIME AS \"nextVerStartTime\", " +
            "tls.CREATE_TIME AS \"createTime\", " +
            "tls.UPDATE_TIME AS \"updateTime\", " +
            "tls.ORIENTATION \"orientation\"  FROM TBL_LANE_STATUS tls JOIN TBL_LANE_INFO tli ON tli.LANE_ID = tls.LANE_ID WHERE tli.STATION_ID = #{stationId} AND tls.ORIENTATION = #{orientation}")
    List<Map> findByStationId(@Param("stationId") String stationId, @Param("orientation") Integer orientation);

    @Select("SELECT bsi.STATION_ID as \"stationId\",bsi.STATION_NAME as \"stationName\",NVL(SUM(tls.ERROR_TRANS), 0) as \"errorTrans\"," +
            "NVL(SUM(tls.ERROR_LPR_TRANS), 0) as \"errorLprTrans\" FROM TBL_BASE_STATION_INFO bsi  " +
            "left JOIN TBL_LANE_INFO tli on tli.STATION_ID=bsi.STATION_ID " +
            "LEFT JOIN TBL_LANE_STATUS tls on tls.LANE_ID = tli.LANE_ID " +
            "where STATION_HEX like '%310108%' AND STATION_HEX != '31010804' GROUP BY bsi.STATION_ID,bsi.STATION_NAME ")
    List<Map> getStationFlowUpload();
}
