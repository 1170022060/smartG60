package com.pingok.vod.mapper;

import com.pingok.vod.domain.TblDeviceInfo;
import com.pingok.vod.domain.TblMonitorPreset;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * TBL_MONITOR_PRESET 数据层
 *
 * @author qiumin
 */
@Mapper
public interface TblMonitorPresetMapper extends CommonRepository<TblMonitorPreset> {

    @Select("select DEVICE_ID FROM TBL_MONITOR_PRESET WHERE USER_ID = #{userId,jdbcType=NUMERIC}")
    List<Object> findByUserId(@Param("userId") Long userId);
}
