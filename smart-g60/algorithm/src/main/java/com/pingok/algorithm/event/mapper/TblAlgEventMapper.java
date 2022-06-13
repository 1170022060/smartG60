package com.pingok.algorithm.event.mapper;

import com.pingok.algorithm.event.entity.TblAlgEvent;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TblAlgEventMapper extends CommonRepository<TblAlgEvent> {

    @Select("<script>" +
            " SELECT " +
            " ID as id, " +
            " STATION_NUM as stationNum, " +
            " TRAFFIC_DIRECTION as trafficDirection, " +
            " LINE_NUM as lineNum, " +
            " EVENT_TYPE as eventType, " +
            " START_TIME as startTime, " +
            " END_TIME as endTime, " +
            " CREATE_TIME as createTime, " +
            " UPDATE_TIME as updateTime, " +
            " LIMIT_SPEED as limitSpeed, " +
            " STATUS as status " +
            " FROM " +
            " TBL_ALG_EVENT " +
            " WHERE " +
            " STATUS IN (0, 1) " +
            " ORDER BY " +
            " CREATE_TIME DESC " +
            "</script>")
    List<TblAlgEvent> selectNoFinishEventList();
}
