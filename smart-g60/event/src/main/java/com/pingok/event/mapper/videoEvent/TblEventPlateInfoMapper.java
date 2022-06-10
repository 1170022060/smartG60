package com.pingok.event.mapper.videoEvent;

import com.pingok.event.domain.videoEvent.TblEventPlateInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TblEventPlateInfoMapper extends CommonRepository<TblEventPlateInfo> {
    @Select({"<script>" +
            "select UBI_SOURCE_ID as \"ubiSourceId\", UI_SPEED as \"uiSpeed\" from (" +
            "select ROW_NUMBER()over(PARTITION by UBI_SOURCE_ID ORDER BY UBI_SOURCE_ID desc) as \"sid\", UBI_SOURCE_ID, UI_SPEED " +
            "from TBL_EVENT_PLATE_INFO WHERE UI_VEHICLE_LANE = #{laneNo}" +
            ") dual where UBI_SOURCE_ID in " +
            "<foreach collection = \"camIds\" index=\"index\" item=\"camId\" open=\"(\" separator=\",\" close=\")\">" +
            " #{camId}" +
            "</foreach>" +
            "</script>"})
    List<TblEventPlateInfo> selectByLane(@Param("laneNo") Integer laneNo, @Param("camIds") List<String> camIds);

}