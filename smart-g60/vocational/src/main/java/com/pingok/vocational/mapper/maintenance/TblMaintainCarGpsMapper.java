package com.pingok.vocational.mapper.maintenance;

import com.pingok.vocational.domain.maintenance.TblMaintainCarGps;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_MAINTAIN_CAR_GPS 数据层
 *
 * @author xia
 */
@Mapper
public interface TblMaintainCarGpsMapper extends CommonRepository<TblMaintainCarGps> {

    @Select({"<script>" +
            "SELECT  " +
            "tmcg.ID as \"id\", " +
            "tmcg.LICENSE_PLATE as \"vehPlate\", " +
            "to_char(tmcg.TIME,'yyyy-mm-dd hh24:mi:ss') as \"time\", " +
            "tmcg.REVERSE_GEOCODE as \"position\", " +
            "tmcg.SPEED as \"speed\", " +
            "to_char(tmcg.CREATE_TIME,'yyyy-mm-dd hh24:mi:ss') as \"createTime\", " +
            "to_char(tmcg.UPDATE_TIME,'yyyy-mm-dd hh24:mi:ss') as \"updateTime\" " +
            "FROM TBL_MAINTAIN_CAR_GPS tmcg " +
            "where 1=1 " +
            "<when test='vehPlate != null'> " +
            "and tmcg.VEH_PLATE like '%' || #{vehPlate} || '%' " +
            "</when>"+
            "order by tmcg.CREATE_TIME " +
            "</script>"})
    List<Map> selectInfo(@Param("vehPlate") String vehPlate);
}
