package com.pingok.vocational.mapper.primaryCar;

import com.pingok.vocational.domain.primaryCar.TblVehicleTrailInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Mapper
public interface TblVehicleTrailInfoMapper extends CommonRepository<TblVehicleTrailInfo> {
    
    @Select({"<script>" +
            "SELECT  " +
            "a.ID AS \"id\", " +
            "a.LICENSE AS \"license\", " +
            "b.DICT_LABEL AS \"color\", " +
            "a.LON AS \"lon\", " +
            "a.LAT AS \"lat\", " +
            "to_char(a.TIME,'yyyy-mm-dd hh24:mi:ss') AS \"time\", " +
            "a.SPEED AS \"speed\", " +
            "a.DIRECTION AS \"direction\", " +
            "a.ALTITUDE AS \"altitude\", " +
            "a.ACC AS \"acc\" " +
            "FROM TBL_VEHICLE_TRAIL_INFO a " +
            "left join SYS_DICT_DATA b on b.DICT_VALUE=a.COLOR and b.DICT_TYPE='veh_color' " +
            "where 1=1 " +
            "<when test='vehPlate != null'>" +
            "and a.LICENSE like '%' || #{vehPlate} || '%' " +
            "</when> " +
            "</script>"})
    List<Map> getVehTrailInfo(@Param("vehPlate") String vehPlate);
}
