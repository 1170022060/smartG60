package com.pingok.algorithm.trafficStatus.mapper;

import com.pingok.algorithm.trafficStatus.entity.TblAlgTrafficStatusSpeed;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TblAlgTrafficStatusSpeedMapper extends CommonRepository<TblAlgTrafficStatusSpeed> {

    @Select("<script>" +
            " SELECT " +
            " TRAFFIC_STATUS " +
            " FROM " +
            " TBL_ALG_TRAFFIC_STATUS_SPEED " +
            " WHERE " +
            " LIMIT_SPEED = #{limitSpeed} " +
            " AND " +
            " MIN_SPEED <![CDATA[ <= ]]> #{averageSpeed} " +
            " AND " +
            " (MAX_SPEED IS null OR MAX_SPEED <![CDATA[ > ]]> #{averageSpeed}) " +
            "</script>")
    Integer getTrafficStatusSpeed(@Param("limitSpeed") Integer limitSpeed, @Param("averageSpeed") Double averageSpeed);

    @Select("<script>" +
            " SELECT " +
            " MAX_SPEED " +
            " FROM " +
            " TBL_ALG_TRAFFIC_STATUS_SPEED " +
            " WHERE " +
            " LIMIT_SPEED = #{limitSpeed} " +
            " AND " +
            " TRAFFIC_STATUS = #{trafficStatus} " +
            "</script>")
    int getMaxSpeed(@Param("limitSpeed") Integer limitSpeed, @Param("trafficStatus") Integer trafficStatus);
}
