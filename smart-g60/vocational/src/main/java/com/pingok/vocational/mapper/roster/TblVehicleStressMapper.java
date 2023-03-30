package com.pingok.vocational.mapper.roster;

import com.pingok.vocational.domain.roster.TblVehicleRecord;
import com.pingok.vocational.domain.roster.TblVehicleStress;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_VEHICLE_STRESS 数据层
 *
 * @author xia
 */
public interface TblVehicleStressMapper extends CommonRepository<TblVehicleStress> {
    @Select({"<script>" +
            "select a.VEH_PLATE as \"vehPlate\"," +
            "b.DICT_LABEL as \"vehClass\"," +
            "c.DICT_LABEL as \"vehColor\"," +
            "to_char(a.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "to_char(a.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\" from  TBL_VEHICLE_STRESS a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.VEH_CLASS and b.DICT_TYPE='veh_class' " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.VEH_COLOR and c.DICT_TYPE='veh_color' " +
            "where 1=1 " +
            "<when test='vehType != null'> " +
            "and a.VEH_TYPE = #{vehType} " +
            "</when>"+
            "<when test='vehPlate != null'> " +
            "and a.VEH_PLATE like '%' || #{vehPlate} || '%' " +
            "</when>"+
            "</script>"})
    List<Map> selectVehicleRecord(@Param("vehType") Integer vehType,@Param("vehPlate") String vehPlate);
}
