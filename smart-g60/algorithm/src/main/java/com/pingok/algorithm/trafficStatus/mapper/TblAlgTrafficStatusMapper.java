package com.pingok.algorithm.trafficStatus.mapper;

import com.pingok.algorithm.trafficStatus.entity.TblAlgTrafficStatus;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TblAlgTrafficStatusMapper extends CommonRepository<TblAlgTrafficStatus> {

    @Select("<script>" +
            " SELECT " +
            " * " +
            " FROM ( " +
            " SELECT " +
            " * " +
            " FROM " +
            " TBL_ALG_TRAFFIC_STATUS " +
            " WHERE " +
            " GANTRY_ID = #{gantryId} " +
            " AND " +
            " LAST_GANTRY_ID = #{lastGantryId} " +
            " ORDER BY " +
            " CREATE_TIME DESC " +
            " ) " +
            " WHERE " +
            " ROWNUM = 1 " +
            "</script>")
    TblAlgTrafficStatus getTrafficStatus(@Param("gantryId") String gantryId, @Param("lastGantryId") String lastGantryId);

    @Select("<script>" +
            " SELECT " +
            " ts.id as id, " +
            " ts.GANTRY_ID as gantryId, " +
            " ts.LAST_GANTRY_ID as lastGantryId, " +
            " ts.TYPE as type, " +
            " ts.TRAVEL_TIME as travelTime, " +
            " ts.AVERAGE_SPEED as averageSpeed, " +
            " ts.TRAFFIC_STATUS as trafficStatus, " +
            " ts.CREATE_TIME as createTime, " +
            " ts.UPDATE_TIME as updateTime " +
            " FROM ( " +
            " SELECT " +
            " GANTRY_ID, " +
            " LAST_GANTRY_ID, " +
            " MAX(CREATE_TIME) as LAST_TIME " +
            " FROM " +
            " TBL_ALG_TRAFFIC_STATUS " +
            " WHERE " +
            " CREATE_TIME is not null " +
            " GROUP BY " +
            " GANTRY_ID, " +
            " LAST_GANTRY_ID " +
            " ) temp " +
            " LEFT JOIN " +
            " TBL_ALG_TRAFFIC_STATUS ts on ts.GANTRY_ID = temp.GANTRY_ID AND ts.LAST_GANTRY_ID = temp.LAST_GANTRY_ID AND " +
            " ts.CREATE_TIME = temp.LAST_TIME " +
            " where 1 = 1 " +
            " <if test = 'gantryId != null and gantryId != \"\"' > " +
            " and temp.GANTRY_ID = #{gantryId} " +
            " </if> " +
            " <if test = 'lastGantryId != null and lastGantryId != \"\"' > " +
            " and temp.LAST_GANTRY_ID = #{lastGantryId} " +
            " </if> " +
            " <if test = 'type != null' > " +
            " and ts.TYPE = #{type} " +
            " </if> " +
            " ORDER BY " +
            " temp.GANTRY_ID " +
            "</script>")
    List<TblAlgTrafficStatus> selectTrafficStatusListByBean(TblAlgTrafficStatus tblAlgTrafficStatus);
}
