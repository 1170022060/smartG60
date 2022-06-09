package com.pingok.algorithm.carbonEmission.mapper;

import com.pingok.algorithm.carbonEmission.domian.TblAlgOilConsumeVO;
import com.pingok.algorithm.carbonEmission.entity.TblAlgOilConsume;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TblAlgOilConsumeMapper extends CommonRepository<TblAlgOilConsume> {

    @Select("<script>" +
            " SELECT " +
            " V, " +
            " F " +
            " FROM ( " +
            " SELECT " +
            " SPEED AS V, " +
            " OIL_CONSUME as F " +
            " FROM " +
            " TBL_ALG_OIL_CONSUME " +
            " WHERE " +
            " VEHICLE_TYPE = #{vehicleType} " +
            " AND " +
            " TO_NUMBER(SPEED) <![CDATA[ <= ]]> #{speed} " +
            " ORDER BY " +
            " TO_NUMBER(SPEED) DESC " +
            " ) " +
            " WHERE " +
            " ROWNUM = 1 " +
            "</script>")
    TblAlgOilConsumeVO getAlgOilConsumeFirst(@Param("vehicleType") Integer vehicleType, @Param("speed") Double speed);

    @Select("<script>" +
            " SELECT " +
            " V, " +
            " F " +
            " FROM ( " +
            " SELECT " +
            " SPEED AS V, " +
            " OIL_CONSUME as F " +
            " FROM " +
            " TBL_ALG_OIL_CONSUME " +
            " WHERE " +
            " VEHICLE_TYPE = #{vehicleType} " +
            " AND " +
            " TO_NUMBER(SPEED) <![CDATA[ >= ]]> #{speed} " +
            " ORDER BY " +
            " TO_NUMBER(SPEED) ASC " +
            " ) " +
            " WHERE " +
            " ROWNUM = 1 " +
            "</script>")
    TblAlgOilConsumeVO getAlgOilConsumeLast(@Param("vehicleType") Integer vehicleType, @Param("speed") Double speed);

    @Select("<script>" +
            " SELECT " +
            " V, " +
            " F " +
            " FROM ( " +
            " SELECT " +
            " SPEED AS V, " +
            " OIL_CONSUME as F " +
            " FROM " +
            " TBL_ALG_OIL_CONSUME " +
            " WHERE " +
            " VEHICLE_TYPE = #{vehicleType} " +
            " AND " +
            " TO_NUMBER(SPEED) = #{speed} " +
            " ORDER BY " +
            " TO_NUMBER(SPEED) ASC " +
            " ) " +
            " WHERE " +
            " ROWNUM = 1 " +
            "</script>")
    TblAlgOilConsumeVO getAlgOilConsumeByBean(@Param("vehicleType") Integer vehicleType, @Param("speed") Double speed);
}
