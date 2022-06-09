package com.pingok.event.mapper.videoEvent;

import com.pingok.event.domain.videoEvent.TblEventVehicleEvent;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TblEventVehicleEventMapper extends CommonRepository<TblEventVehicleEvent> {

    @Select("select Id from TBL_DEVICE_INFO where DEVICE_ID = #{deviceId}")
    Object findByDeviceId(@Param("deviceId")String deviceId);
}
