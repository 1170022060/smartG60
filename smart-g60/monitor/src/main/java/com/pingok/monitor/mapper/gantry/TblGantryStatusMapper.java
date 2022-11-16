package com.pingok.monitor.mapper.gantry;

import com.pingok.monitor.domain.gantry.TblGantryStatus;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Mapper
public interface TblGantryStatusMapper extends CommonRepository<TblGantryStatus> {
    @Select("SELECT NVL(SUM(TRANSACTION_NUMBER), 0) FROM TBL_GANTRY_STATUS")
    Integer findAllTransactionNumber();

    @Select("SELECT NVL(SUM(TRAVELIMAGE_NUMBER), 0) FROM TBL_GANTRY_STATUS")
    Integer findAllTravelimageNumber();
    
    @Select("SELECT  " +
            "  tgi.ID as \"id\",  " +
            "  tgi.DEVICE_ID as \"deviceId\",  " +
            "  tgi.DEVICE_NAME as \"deviceName\",  " +
            "  tgi.PILE_NO as \"pileNo\",  " +
            "  to_char(tgs.TIME,'yyyy-MM-dd HH24:mi:ss') as \"time\",  " +
            "  tgs.STATUS as \"status\",  " +
            "  tgs.STATUS_DESC as \"statusDesc\",  " +
            "  NVL(tgs.TRANSACTION_NUMBER, 0) as \"transactionNumber\", " +
            "  NVL(tgs.TRAVELIMAGE_NUMBER,0) as \"travelimageNumber\",  " +
            "case when NVL(a.total, 0)!=0 then ROUND(NVL(tgs.TRANSACTION_NUMBER,0)/a.total, 2) ELSE 0 END as \"transRate\"," +
            "case when NVL(b.total, 0)!=0 then ROUND(NVL(tgs.TRAVELIMAGE_NUMBER,0)/b.total, 2) ELSE 0 END as \"picRate\"," +
            "  tgi.POS_X as \"posX\", " +
            "  tgi.POS_Y as \"posY\", " +
            "  tgi.IS_FRONT as \"isFront\"," +
            "  tgi.DIRECTION as \"direction\"  " +
            "  FROM  " +
            "  TBL_GANTRY_INFO tgi  " +
            "  LEFT JOIN TBL_GANTRY_STATUS tgs ON tgi.ID = tgs.DEVICE_ID" +
            "  LEFT JOIN (" +
            "   SELECT GANTRY_ID,COUNT(*) as total FROM TBL_GANTRY_TRANSACTION_2022 " +
            "   WHERE TRANS_TIME between TO_DATE(#{startTime},'yyyy-MM-dd hh24:mi:ss') " +
            " AND TO_DATE(#{endTime},'yyyy-MM-dd hh24:mi:ss') GROUP BY GANTRY_ID)a on a.GANTRY_ID = tgi.DEVICE_ID" +
            " LEFT JOIN(" +
            " SELECT GANTRY_ID,COUNT(*) as total FROM TBL_GANTRY_TRAVELIMAGE_2022 " +
            " WHERE PIC_TIME between TO_DATE(#{startTime},'yyyy-MM-dd hh24:mi:ss') " +
            " AND TO_DATE(#{endTime},'yyyy-MM-dd hh24:mi:ss') GROUP BY GANTRY_ID" +
            ")b on b.GANTRY_ID = tgi.DEVICE_ID order by tgi.DEVICE_ID")
    List<Map> gantryStatus(@Param("startTime")String startTime,@Param("endTime")String endTime);

    @Select("SELECT COUNT(*) as \"total\" FROM TBL_GANTRY_TRANSACTION_2022 WHERE to_char(TRANS_TIME,'yyyy-MM-dd') = #{currentDate} ")
    Object getGantryTotalFlow(@Param("currentDate") String currentDate);

    @Select("SELECT NVL(SUM(TRANSACTION_NUMBER+TRAVELIMAGE_NUMBER),0) as \"total\" FROM TBL_GANTRY_STATUS WHERE to_char(TIME,'yyyy-MM-dd') = #{currentDate} ")
    Object getGantryNotUploadFlow(@Param("currentDate") String currentDate);
}
