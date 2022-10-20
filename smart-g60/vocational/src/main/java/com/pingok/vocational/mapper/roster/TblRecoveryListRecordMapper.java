package com.pingok.vocational.mapper.roster;

import com.pingok.vocational.domain.roster.TblRecoveryListRecord;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_RECOVERY_LIST_RECORD 数据层
 *
 * @author xia
 */
public interface TblRecoveryListRecordMapper extends CommonRepository<TblRecoveryListRecord> {

    @Select({"<script>" +
            "select a.VERSION as \"version\" ," +
            "b.DICT_LABEL as \"type\" ," +
            "c.DICT_LABEL as \"status\" ," +
            "a.VEH_PLATE as \"vehPlate\" ," +
            "d.DICT_LABEL as \"vehColor\"," +
            "a.REASON as \"reason\" ," +
            "a.FEE as \"fee\" ," +
            "a.COUNT as \"count\" ," +
            "to_char(a.ARREARS_DATE, 'yyyy-mm-dd hh24:mi:ss') as \"arrearsDate\" from TBL_RECOVERY_LIST_RECORD a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.TYPE and b.DICT_TYPE='recovery_type' " +
            "left join  SYS_DICT_DATA c on c.DICT_VALUE=a.STATUS and c.DICT_TYPE='list_sign' " +
            "left join  SYS_DICT_DATA d on d.DICT_VALUE=a.VEH_COLOR and d.DICT_TYPE='veh_color' " +
            "where 1=1 " +
            "<when test='vehPlate != null'> " +
            " and VEH_PLATE like '%' || #{vehPlate} || '%' " +
            "</when>"+
            "order by ARREARS_DATE" +
            "</script>"})
    List<Map> selectRecoveryList(@Param("vehPlate") String vehPlate);
}
