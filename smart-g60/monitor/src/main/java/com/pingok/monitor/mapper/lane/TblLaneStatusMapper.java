package com.pingok.monitor.mapper.lane;

import com.pingok.monitor.domain.lane.TblLaneStatus;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
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
            "NVL(SUM(tls.ERROR_LPR_TRANS), 0) as \"errorLprTrans\"," +
            "a.PROMPTNESS_LPR as \"promptnessLpr\",a.PROMPTNESS_TRANS as \"promptnessTrans\" FROM TBL_BASE_STATION_INFO bsi  " +
            "left JOIN TBL_LANE_INFO tli on tli.STATION_ID=bsi.STATION_ID " +
            "LEFT JOIN TBL_LANE_STATUS tls on tls.LANE_ID = tli.LANE_ID " +
            "LEFT JOIN (SELECT * FROM TBL_SOFTWARE_INFO tsi WHERE NAME ='站上传部/省程序')a on UPPER(SUBSTR(a.NUM, -6, 4) ) = bsi.STATION_ID " +
            "where STATION_HEX like '%310108%' AND STATION_HEX != '31010804' GROUP BY bsi.STATION_ID,bsi.STATION_NAME，a.PROMPTNESS_LPR,a.PROMPTNESS_TRANS ")
    List<Map> getStationFlowUpload();
    
    @Select("SELECT SUM(\"totalFlow\") as \"total\" FROM( " +
            "SELECT COUNT(*) as \"totalFlow\" FROM TBL_EX_TRANS_2022 WHERE to_char(WORK_DATE,'yyyy-MM-dd') = #{currentDate}  " +
            "UNION ALL " +
            "SELECT COUNT(*) as \"totalFlow\" FROM TBL_EN_TRANS_2022 WHERE to_char(WORK_DATE,'yyyy-MM-dd') = #{currentDate} )")
    Object getTotalFlow(@Param("currentDate") String currentDate);

    @Select("SELECT NVL(SUM((tls.ERROR_TRANS + tls.ERROR_LPR_TRANS)),0) as \"total\" FROM TBL_BASE_STATION_INFO bsi  " +
            "left JOIN TBL_LANE_INFO tli on tli.STATION_ID=bsi.STATION_ID  " +
            "LEFT JOIN TBL_LANE_STATUS tls on tls.LANE_ID = tli.LANE_ID  " +
            "where STATION_HEX like '%310108%' AND STATION_HEX != '31010804' AND to_char(TIME,'yyyy-MM-dd') = #{currentDate} ")
    Object getNotUploadTotalFlow(@Param("currentDate") String currentDate);

    @Select("SELECT STATION_HEX as \"stationHex\",STATION_NAME as \"stationName\",POS_X as \"posX\",POS_Y as \"posY\" " +
            "FROM TBL_BASE_STATION_INFO where STATION_HEX like '%310108%' AND STATION_HEX != '31010804' ")
    List<Map> getStationInfo();

    @Select("SELECT " +
            "tdf.ID as \"deviceId\",tdi.DEVICE_NAME as \"deviceName\",tbsi.STATION_NAME as \"position\"," +
            "to_char(tdf.FAULT_TIME,'yyyy-MM-dd HH24:mi:ss') as \"faultTime\",tdf.FAULT_TYPE as \"faultDESC\"," +
            "tdf.FAULT_DESCRIPTION as \"faultDetail\",'--' as \"laneName\",tdf.STATUS as \"status\" " +
            "FROM TBL_DEVICE_FAULT tdf " +
            "LEFT JOIN TBL_DEVICE_INFO tdi on tdi.ID=tdf.DEVICE_ID " +
            "LEFT JOIN TBL_BASE_STATION_INFO tbsi on tbsi.STATION_HEX =tdi.STATION_BELONG " +
            "WHERE tdf.STATUS = 0 AND tbsi.STATION_HEX=#{stationHex} ")
    List<Map> getFaultList(@Param("stationHex") String stationHex);

    @Select("SELECT SUM(\"count\") as \"total\" FROM ( " +
            "SELECT COUNT(*) as \"count\" FROM TBL_DEVICE_INFO tdi " +
            "LEFT JOIN TBL_BASE_STATION_INFO tbsi on tbsi.STATION_HEX =tdi.STATION_BELONG " +
            "WHERE tbsi.STATION_HEX = #{stationHex} " +
            "UNION ALL " +
            "SELECT COUNT(*)as \"count\" FROM TBL_DEVICE_INFO_LANE tdil " +
            "LEFT JOIN TBL_BASE_STATION_INFO tbsi on tbsi.STATION_HEX =tdil.STATION_BELONG " +
            "WHERE tbsi.STATION_HEX = #{stationHex})")
    Integer getDeviceTotal(@Param("stationHex") String stationHex);

    @Select("SELECT  " +
            "COUNT(*) as \"total\", " +
            "SUM(DECODE(tds.STATUS,1, 1, 0))as \"normal\", " +
            "SUM(DECODE(tdf.STATUS,1, 1, 0))as \"fault\", " +
            "tdi.DEVICE_TYPE as \"deviceType\",DECODE(tdc.CATEGORY_NAME, null,sdd.DICT_LABEL, tdc.CATEGORY_NAME) as \"deviceCat\" " +
            "FROM TBL_DEVICE_INFO tdi " +
            "LEFT JOIN TBL_BASE_STATION_INFO tbsi on tbsi.STATION_HEX =tdi.STATION_BELONG " +
            "LEFT JOIN TBL_DEVICE_CATEGORY tdc on tdc.ID = tdi.DEVICE_CATEGORY " +
            "LEFT JOIN (SELECT DISTINCT DEVICE_ID,STATUS FROM TBL_DEVICE_STATUS ORDER BY DEVICE_ID) tds on tdi.ID=tds.DEVICE_ID " +
            "LEFT JOIN (SELECT DISTINCT DEVICE_ID,STATUS FROM TBL_DEVICE_FAULT ORDER BY DEVICE_ID) tdf on tdf.DEVICE_ID=tds.DEVICE_ID " +
            "LEFT JOIN TBL_DEVICE_CATEGORY tdc on tdc.ID = tdi.DEVICE_CATEGORY " +
            "LEFT JOIN SYS_DICT_DATA sdd on tdi.DEVICE_TYPE = sdd.DICT_VALUE AND sdd.DICT_TYPE = 'device_type' " +
            "WHERE tbsi.STATION_HEX = #{stationHex} " +
            "GROUP BY tdi.DEVICE_TYPE,sdd.DICT_LABEL,tdc.CATEGORY_NAME ")
    List<Map> getDeviceTypeTotal(@Param("stationHex") String stationHex);
    
    @Select(" SELECT  " +
            " tli.LANE_HEX as \"laneHex\",tli.LANE_NAME \"laneName\",tli.LANE_TYPE \"laneType\",tls.STATUS as \"status\" " +
            " FROM TBL_LANE_INFO tli " +
            " LEFT JOIN TBL_BASE_STATION_INFO tbsi on UPPER(tbsi.STATION_ID) =tli.STATION_ID " +
            " LEFT JOIN TBL_LANE_STATUS tls on tls.LANE_HEX=tli.LANE_HEX " +
            " WHERE  " +
            " tbsi.STATION_HEX = #{stationHex} AND  " +
            " tli.LANE_TYPE != 20 " +
            " GROUP BY tli.LANE_TYPE,tli.LANE_NAME,tli.LANE_HEX,tls.STATUS ORDER BY tli.LANE_TYPE,tli.LANE_NAME")
    List<Map> getLaneListByStation(@Param("stationHex") String stationHex);
}
