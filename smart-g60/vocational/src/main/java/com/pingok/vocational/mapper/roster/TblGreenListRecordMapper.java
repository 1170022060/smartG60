package com.pingok.vocational.mapper.roster;

import com.pingok.vocational.domain.roster.TblGreenListRecord;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_GREEN_LIST_RECORD 数据层
 *
 * @author xia
 */
public interface TblGreenListRecordMapper extends CommonRepository<TblGreenListRecord> {

    @Select({"<script>" +
            "select a.PHONE as \"phone\" ," +
            "to_char(a.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "a.VEH_PLATE as \"vehPlate\" ," +
            "b.DICT_LABEL as \"vehClass\"," +
            "c.DICT_LABEL as \"vehColor\"," +
            "d.DICT_LABEL as \"startDistrictId\" ," +
            "e.DICT_LABEL as \"endDistrictId\" " +
            " from TBL_GREEN_LIST_RECORD a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.VEH_CLASS and b.DICT_TYPE='veh_class' " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.VEH_COLOR and c.DICT_TYPE='veh_color' " +
            "left join  SYS_DICT_DATA d on d.DICT_VALUE=a.START_DISTRICT_ID and d.DICT_TYPE='province_id' " +
            "left join  SYS_DICT_DATA e on e.DICT_VALUE=a.END_DISTRICT_ID and e.DICT_TYPE='province_id' " +
            "where 1=1" +
            "<when test='vehPlate != null'> " +
            "and a.VEH_PLATE like '%' || #{vehPlate} || '%' " +
            "</when>"+
            "order by a.CREATE_TIME" +
            "</script>"})
    List<Map> selectGreenList(@Param("vehPlate") String vehPlate);
}
