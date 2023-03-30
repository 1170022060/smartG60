package com.pingok.vocational.mapper.nameList;

import com.pingok.vocational.domain.roster.TblRecoveryListRecord;
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
public interface TblRecoveryListRecordNMapper extends CommonRepository<TblRecoveryListRecord> {
    @Select("select " +
            "ID as \"id\"," +
            "VERSION as \"version\"," +
            "VEHICLE_ID as \"vehicleId\"," +
            "REASON as \"reason\"," +
            "OWE_FEE as \"oweFee\"," +
            "EVASION_COUNT as \"evasionCount\"," +
            "VERSION_ID as \"versionId\"," +
            "to_char(UPDATE_TIME,'yyyy-mm-dd hh24:mi:ss') as \"updateTime\"," +
            "to_char(CREATION_TIME,'yyyy-mm-dd hh24:mi:ss') as \"creationTime\"," +
            "TYPE as \"type\"," +
            "case when STATUS=1 then '进入名单' when STATUS = 2 then '解除名单' end as \"status\"  " +
            "from TBL_RECOVERY_LIST_RECORD_LOG  " +
            "where VERSION_ID = #{id} ")
    List<Map> getRecoveryListById(@Param("id") Long id);
}
