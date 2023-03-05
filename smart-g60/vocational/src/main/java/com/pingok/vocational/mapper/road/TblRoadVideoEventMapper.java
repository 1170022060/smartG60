package com.pingok.vocational.mapper.road;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TblRoadVideoEventMapper {
    @Select({"<script>" +
            "select " +
            "CAMERA_ID as \"cameraId\"," +
            "case when TYPE =1 then '拥堵等级' when TYPE =2 then '超速' when TYPE=3 then '数据连续缺失' when TYPE=4 then '设备异常' end as \"eventType\"," +
            "WARNING as \"warning\"," +
            "TRACK as \"track\" " +
            "from TBL_ROAD_VIDEO_EVENT_INFO " +
            "</script>"})
    List<Map> selectRoadVideoEvent();
}
