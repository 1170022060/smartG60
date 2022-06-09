package com.pingok.vod.mapper;

import com.pingok.vod.domain.TblDeviceInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TblDeviceInfo 数据层
 *
 * @author qiumin
 */
@Mapper
public interface TblDeviceInfoMapper extends CommonRepository<TblDeviceInfo> {

    @Select("SELECT tdi.ID as \"id\",tdi.DEVICE_ID as \"deviceId\",tdi.DEVICE_NAME as \"deviceName\",tdi.IS_CONTROL as \"isControl\" FROM TBL_DEVICE_INFO tdi JOIN TBL_DEVICE_CATEGORY tdc on tdc.ID = tdi.DEVICE_CATEGORY WHERE tdi.IS_CONTROL IS NOT NULL and\n" +
            " tdc.CATEGORY_NAME = '摄像机'")
    List<Map> findAllCamera();

    @Select("SELECT tdi.ID as \"id\",tdi.DEVICE_ID as \"deviceId\",tdi.DEVICE_NAME as \"deviceName\",tdi.IS_CONTROL as \"isControl\" FROM TBL_DEVICE_INFO tdi JOIN TBL_DEVICE_CATEGORY tdc on tdc.ID = tdi.DEVICE_CATEGORY JOIN TBL_BASE_STATION_INFO tbsi on tbsi.STATION_ID = tdi.STATION_BELONG JOIN  SYS_DEPT dept ON dept.DEPT_NAME = tbsi.STATION_NAME JOIN  SYS_USER usr ON usr.DEPT_ID = dept.DEPT_ID WHERE tdi.IS_CONTROL IS NOT NULL and tdc.CATEGORY_NAME = '摄像机' and tdi.MONITOR_LEVEL = 2 AND usr.USER_ID = #{userId,jdbcType=NUMERIC}")
    List<Map> findAllCameraByUserId(@Param("userId") Long userId);
}
