package com.pingok.vocational.mapper.emergency;


import com.pingok.vocational.domain.emergency.TblEmergencySupplies;
import com.pingok.vocational.domain.roster.TblTractorListRecord;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_EMERGENCY_SUPPLIES 数据层
 *
 * @author xia
 */
public interface TblEmergencySuppliesMapper extends CommonRepository<TblEmergencySupplies> {

    @Select({"<script>" +
            "select a.ID as \"id\"," +
            "f.DICT_LABEL as \"suppliesType\"," +
            "a.SUPPLIES_NAME as \"suppliesName\"," +
            "a.SUPPLIES_COUNT as \"suppliesCount\"," +
            "a.GOODS_ID as \"goodsId\"," +
            "a.SPECIFICATION as \"specification\"," +
            "a.BRAND as \"brand\"," +
            "b.DICT_LABEL as \"manufacturer\" ," +
            "a.VEH_PLATE as \"vehPlate\"," +
            "g.DICT_LABEL as \"vehClass\"," +
            "h.DICT_LABEL as \"vehColor\"," +
            "a.VEH_PHOTO as \"vehPhoto\"," +
            "c.FIELD_NAME as \"fieldBelong\" ," +
            "a.EXPERT_NAME as \"expertName\"," +
            "a.EXPERT_PHONE as \"expertPhone\"," +
            "a.REMARK as \"remark\"," +
            "a.STATUS  as \"status\"," +
            "to_char(a.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "to_char(a.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\"," +
            "case when a.CREATE_USER_ID is null then null else a.CREATE_USER_ID || ':' || d.USER_NAME end as \"createUserName\"," +
            "case when a.UPDATE_USER_ID is null then null else a.UPDATE_USER_ID || ':' || e.USER_NAME end as \"updateUserName\" from TBL_EMERGENCY_SUPPLIES a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.MANUFACTURER and b.DICT_TYPE='manufacturer' " +
            "left join TBL_FIELD_INFO c on c.ID=a.FIELD_BELONG " +
            "left join  SYS_USER d on a.CREATE_USER_ID=d.USER_ID " +
            "left join  SYS_USER e on a.UPDATE_USER_ID=e.USER_ID " +
            "left join  SYS_DICT_DATA f on f.DICT_VALUE=to_char(a.SUPPLIES_TYPE) and f.DICT_TYPE='supplies_type' " +
            "left join  SYS_DICT_DATA g on g.DICT_VALUE=a.VEH_CLASS and g.DICT_TYPE='veh_class' " +
            "left join  SYS_DICT_DATA h on h.DICT_VALUE=a.VEH_COLOR and h.DICT_TYPE='veh_color' " +
            "where to_number(a.SUPPLIES_TYPE) = #{suppliesType} " +
            "<when test='suppliesName != null'> " +
            "and a.SUPPLIES_NAME like CONCAT(CONCAT('%',#{suppliesName}),'%') " +
            "</when>"+
            "<when test='vehPlate != null'> " +
            "and a.VEH_PLATE like CONCAT(CONCAT('%',#{vehPlate}),'%') " +
            "</when>"+
            "<when test='expertName != null'> " +
            "and a.EXPERT_NAME like CONCAT(CONCAT('%',#{expertName}),'%') " +
            "</when>"+
            "<when test='status != null'> " +
            "and a.STATUS= #{status} " +
            "</when>"+
            "</script>"})
    List<Map> selectEmergencySupplies(@Param("suppliesType") Integer suppliesType,@Param("suppliesName") String suppliesName, @Param("vehPlate") String vehPlate, @Param("expertName") String expertName, @Param("status") Integer status);

    @Select("select a.ID as \"id\" , " +
            "case SUPPLIES_TYPE when 1 Then a.SUPPLIES_NAME ||'_'|| b.FIELD_NAME when 2 then a.VEH_PLATE ||'_'|| cast(c.DICT_LABEL as varchar2(20)) ||'车牌' when 3 then a.EXPERT_NAME ||'_'|| a.EXPERT_PHONE end as \"suppliesName\" " +
            "from TBL_EMERGENCY_SUPPLIES a " +
            "left join TBL_FIELD_INFO b on a.FIELD_BELONG=b.ID " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.VEH_COLOR and c.DICT_TYPE='veh_color' " +
            "where a.SUPPLIES_TYPE= #{suppliesType} " +
            "and a.STATUS = 1")
    List<Map> selectEmergencyName(@Param("suppliesType") Integer suppliesType);

    @Select("select * from TBL_EMERGENCY_SUPPLIES where SUPPLIES_NAME= #{suppliesName} and FIELD_BELONG= #{fieldBelong} and SUPPLIES_TYPE=1 and rownum = 1")
    TblEmergencySupplies checkSuppliesNameUnique(@Param("suppliesName") String suppliesName, @Param("fieldBelong") Long fieldBelong);

    @Select("select * from TBL_EMERGENCY_SUPPLIES where VEH_PLATE= #{vehPlate} and VEH_COLOR= #{vehColor} and SUPPLIES_TYPE=2 and rownum = 1")
    TblEmergencySupplies checkVehPlateUnique(@Param("vehPlate") String vehPlate, @Param("vehColor") Integer vehColor);

    @Select("select * from TBL_EMERGENCY_SUPPLIES where EXPERT_NAME= #{expertName} and EXPERT_PHONE= #{expertPhone} and SUPPLIES_TYPE=3 and rownum = 1")
    TblEmergencySupplies checkExpertNameUnique(@Param("expertName") String expertName, @Param("expertPhone") String expertPhone);
}
