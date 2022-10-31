package com.pingok.vocational.mapper.nameList;

import com.pingok.vocational.domain.nameList.TblRecoveryStationUsed;
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
public interface TblRecoveryMapper extends CommonRepository<TblRecoveryStationUsed> {
    @Select({"<script>"+
            "select * from ( " +
            "select  " +
            "NVL(rv.ID,0) as \"id\", " +
            "rsu.STATION_HEX as \"stationHex\",  " +
            "bsi.STATION_NAME as \"stationName\",  " +
            "rsu.VERSION as \"version\",  " +
            "to_char(rsu.APPLY_TIME,'yyyy-mm-dd hh24:mi:ss') as \"applyTime\", " +
            "row_number() over(partition by rsu.STATION_HEX  order by rsu.VERSION desc) as \"rn\"  " +
            "from TBL_RECOVERY_STATION_USED rsu  " +
            "  LEFT JOIN TBL_BASE_STATION_INFO bsi on bsi.STATION_HEX = rsu.STATION_HEX  " +
            "  LEFT JOIN TBL_RECOVERY_VERSION rv on rv.VERSION = rsu.VERSION  " +
            ") a where a.\"rn\"=1 " +
            "<when test='stationName != null'> " +
            "and a.\"stationName\" like '%' || #{stationName} || '%' " +
            "</when> "+
            "<when test='version != null'> " +
            "and a.\"version\" like '%' || #{version} || '%' " +
            "</when> "+
            "</script>"})
    List<Map> getRecoveryList(@Param("stationName") String stationName,@Param("version") String version);
}
