package com.pingok.monitor.mapper.gantry;

import com.pingok.monitor.domain.gantry.TblGantryStatus;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


@Mapper
public interface TblGantryStatusMapper extends CommonRepository<TblGantryStatus> {
    @Select("SELECT NVL(SUM(TRANSACTION_NUMBER), 0) FROM TBL_GANTRY_STATUS")
    Integer findAllTransactionNumber();

    @Select("SELECT NVL(SUM(TRAVELIMAGE_NUMBER), 0) FROM TBL_GANTRY_STATUS")
    Integer findAllTravelimageNumber();
    
    @Select("SELECT " +
            "tgi.ID as \"id\", " +
            "tgi.DEVICE_ID as \"deviceId\", " +
            "tgi.DEVICE_NAME as \"deviceName\", " +
            "tgi.PILE_NO as \"pileNo\", " +
            "to_char(tgs.TIME,'yyyy-MM-dd HH24:mi:ss') as \"time\", " +
            "tgs.STATUS as \"status\", " +
            "tgs.STATUS_DESC as \"statusDesc\", " +
            "tgs.TRANSACTION_NUMBER as \"transactionNumber\", " +
            "tgs.TRAVELIMAGE_NUMBER as \"travelimageNumber\", " +
            "tgi.POS_X as \"posX\", "+
            "tgi.POS_Y as \"posY\", "+
            "tgi.DIRECTION as \"direction\" " +
            "FROM " +
            "TBL_GANTRY_INFO tgi " +
            "LEFT JOIN TBL_GANTRY_STATUS tgs ON tgi.ID = tgs.DEVICE_ID")
    List<Map> gantryStatus();
}
