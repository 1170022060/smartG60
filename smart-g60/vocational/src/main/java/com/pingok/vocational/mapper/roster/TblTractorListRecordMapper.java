package com.pingok.vocational.mapper.roster;

import com.pingok.vocational.domain.roster.TblTractorListRecord;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_TRACTOR_LIST_RECORD 数据层
 *
 * @author xia
 */
public interface TblTractorListRecordMapper extends CommonRepository<TblTractorListRecord> {
    @Select({"<script>" +
            "select a.ID as \"id\" ," +
            "a.VEH_PLATE as \"vehPlate\" ," +
            "b.DICT_LABEL as \"vehColor\"," +
            "to_char(a.START_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"startTime\"," +
            "to_char(a.END_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"endTime\"," +
            "a.STATUS  as \"status\"," +
            "to_char(a.CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"createTime\"," +
            "to_char(a.UPDATE_TIME, 'yyyy-mm-dd hh24:mi:ss') as \"updateTime\" " +
            " from TBL_TRACTOR_LIST_RECORD a " +
            "left join  SYS_DICT_DATA b on b.DICT_VALUE=a.VEH_COLOR and b.DICT_TYPE='veh_color' " +
            "where 1=1 " +
            "<when test='vehPlate != null'> " +
            "and a.VEH_PLATE= #{vehPlate} " +
            "</when>"+
            "<when test='status != null'> " +
            "and a.STATUS= #{status} " +
            "</when>"+
            "order by a.CREATE_TIME" +
            "</script>"})
    List<Map> selectTractorList(@Param("vehPlate") String vehPlate, @Param("status") Integer status);

    @Select("select * from TBL_TRACTOR_LIST_RECORD where VEH_PLATE= #{vehPlate} and VEH_COLOR= #{vehColor} and rownum = 1")
    TblTractorListRecord checkVehPlateUnique(@Param("vehPlate") String vehPlate, @Param("vehColor") Integer vehColor);
}
