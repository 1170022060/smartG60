package com.pingok.vocational.mapper.parkingLot;

import com.pingok.vocational.domain.parkingLot.TblParkingLot;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_PARKING_LOT 数据层
 *
 * @author qiu
 */
public interface TblParkingLotMapper extends CommonRepository<TblParkingLot> {

    @Select("select tpl.ID as \"id\"," +
            "tfi.FIELD_NAME || tpl.REGION_NAME as \"regionName\"," +
            "tpl.TOTAL-tpl.SURPLUS as \"occupy\"," +
            "tpl.SURPLUS as \"surplus\" from TBL_PARKING_LOT tpl " +
            "left join TBL_FIELD_INFO tfi on tfi.ID=tpl.FIELD_ID ")
    List<Map> parkingPlace();

    @Select({"<script>" +
            "SELECT tpl.ID as \"id\"," +
            "tfi.FIELD_NAME as \"fieldName\"," +
            "tpl.REGION_NAME as \"regionName\"," +
            "tpl.REGION_NUM as \"regionNum\"," +
            "tpl.TOTAL as \"total\"," +
            "tpl.SURPLUS as \"surplus\"  FROM TBL_PARKING_LOT tpl " +
            "JOIN TBL_FIELD_INFO tfi ON tfi.ID = tpl.FIELD_ID " +
            "WHERE 1=1 " +
            "<when test='fieldNum != null'> " +
            "and tfi.FIELD_NUM = #{fieldNum}" +
            "</when>"+
            "<when test='regionName != null'> " +
            "and tpl.REGION_NAME = #{regionName}" +
            "</when>"+
            "</script>"})
    List<Map> findByFieldRegion(@Param("fieldNum") String fieldNum,@Param("regionName") String regionName);

    @Select("SELECT " +
            "count(1) AS \"count\"  " +
            "FROM " +
            "TBL_PARKING_VEHICLE_INFO " +
            "WHERE " +
            "EX_TIME IS NULL  " +
            "AND CEIL( ( SYSDATE - EN_TIME ) * 24 ) > (SELECT CONFIG_VALUE FROM  SYS_CONFIG sc WHERE sc.CONFIG_KEY='parking.timeout') " +
            "AND PARKING_ID = #{parkingId}")
    Integer count(@Param("parkingId") Long parkingId);

    @Select({"<script>" +
            "SELECT distinct tpl.REGION_NAME as \"regionName\"  FROM TBL_PARKING_LOT tpl " +
            "JOIN TBL_FIELD_INFO tfi ON tfi.ID = tpl.FIELD_ID " +
            "WHERE 1=1 " +
            "<when test='fieldNum != null'> " +
            "and tfi.FIELD_NUM = #{fieldNum}" +
            "</when>"+
            "</script>"})
    List<Map> selectRegionName(@Param("fieldNum") String fieldNum);
}
