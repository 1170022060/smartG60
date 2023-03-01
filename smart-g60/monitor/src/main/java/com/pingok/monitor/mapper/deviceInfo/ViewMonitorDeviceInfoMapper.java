package com.pingok.monitor.mapper.deviceInfo;

import com.pingok.monitor.domain.deviceInfo.ViewMonitorDeviceInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * VIEW_MONITOR_DEVICE_INFO 数据层
 *
 * @author qiumin
 */
@Mapper
public interface ViewMonitorDeviceInfoMapper extends CommonRepository<ViewMonitorDeviceInfo> {
    @Select("SELECT " +
            "tdi.ID AS \"id\", " +
            "tdi.DEVICE_ID AS \"deviceId\", " +
            "tdi.CAMERA_ID AS \"cameraId\", "+
            "tdi.DEVICE_NAME AS \"deviceName\", " +
            "tdi.GPS AS \"gps\", " +
            "tdi.DEVICE_IP AS \"deviceIp\", " +
            "tds.STATUS AS \"status\", " +
            "to_char(tds.TIME,'yyyy-MM-dd HH24:mi:ss') AS \"statusTime\", " +
            "tds.STATUS_DESC AS \"statusDesc\", " +
            "(tdi.POS_X || '%') as \"posX\", "+
            "(tdi.POS_Y || '%') as \"posY\", "+
            "tdi.DEVICE_TYPE as \"deviceType\","+
            "tds.STATUS_DETAILS AS \"statusDetails\"  " +
            "FROM " +
            "TBL_DEVICE_STATUS tds " +
            "JOIN TBL_DEVICE_INFO tdi ON tdi.ID = tds.DEVICE_ID " +
            "JOIN TBL_FIELD_INFO tfi ON tfi.id = tdi.FIELD_BELONG  " +
            "WHERE " +
            "tdi.FIELD_BELONG in (3940,3941)" )
    List<Map> findByFieldNum(@Param("fieldNum") String fieldNum);

    @Select("SELECT " +
            "tdf.ID AS \"id\", " +
            "tdf.FAULT_ID AS \"faultId\", " +
            "tdf.STATUS AS \"status\", " +
            "tdf.FAULT_DESCRIPTION AS \"faultDescription\", " +
            "to_char(tdf.FAULT_TIME,'yyyy-MM-dd HH24:mi:ss') AS \"faultTime\"  " +
            "FROM " +
            "TBL_DEVICE_FAULT tdf  " +
            "WHERE " +
            "tdf.STATUS in (0,1) " +
            "AND tdf.DEVICE_ID = #{deviceId}")
    List<Map> findFaultByDeviceId(@Param("deviceId") Long deviceId);
}
