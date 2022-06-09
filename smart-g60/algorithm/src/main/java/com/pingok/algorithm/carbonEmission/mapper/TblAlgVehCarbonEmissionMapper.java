package com.pingok.algorithm.carbonEmission.mapper;

import com.pingok.algorithm.carbonEmission.entity.TblAlgVehCarbonEmission;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TblAlgVehCarbonEmissionMapper extends CommonRepository<TblAlgVehCarbonEmission> {

    @Select("<script>" +
            " SELECT " +
            " SUM(TO_NUMBER(CARBON_EMISSION)) as carbonEmission " +
            " FROM " +
            " TBL_ALG_VEH_CARBON_EMISSION " +
            " WHERE " +
            " CARBON_EMISSION_TIME = #{queryTime} " +
            "</script>")
    TblAlgVehCarbonEmission getAlgVehCarbonEmission(@Param("queryTime") String queryTime);
}
