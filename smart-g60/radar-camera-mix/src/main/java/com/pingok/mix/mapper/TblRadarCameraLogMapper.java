package com.pingok.mix.mapper;

import com.pingok.mix.domain.TblRadarCameraLog;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TblRadarCameraLogMapper extends CommonRepository<TblRadarCameraLog> {
    @Select("delete * from tbl_radar_camera_log where 1=1 and id = #{id}")
    void deleteById(@Param("id") Long id);
}
