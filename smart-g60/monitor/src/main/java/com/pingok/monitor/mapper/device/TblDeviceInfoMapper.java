package com.pingok.monitor.mapper.device;

import com.pingok.monitor.domain.device.vo.DeviceInfoVo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * TblDeviceInfo 数据层
 *
 * @author xia
 */
public interface TblDeviceInfoMapper {

    @Select("SELECT " +
            "tdi.ID AS \"id\", " +
            "tdi.DEVICE_ID AS \"deviceId\", " +
            "tdi.DEVICE_NAME AS \"deviceName\", " +
            "tdi.DEVICE_BRAND AS \"deviceBrand\", " +
            "tdi.DEVICE_MODEL AS \"deviceModel\", " +
            "tdi.DEVICE_IP AS \"deviceIp\", " +
            "tdi.PORT AS \"port\", " +
            "tdi.DIRECTION AS \"direction\", " +
            "tdi.STATION_BELONG AS \"stationBelong\", " +
            "tdi.PILE_NO AS \"pileNo\", " +
            "tdi.GPS AS \"gps\", " +
            "TO_CHAR(tds.TIME, 'yyyy-mm-dd hh:mm:ss') AS \"time\", " +
            "NVL( tds.STATUS, 0 ) AS \"status\", " +
            "CASE " +
            "WHEN tds.STATUS IS NULL THEN " +
            "'状态未知' ELSE tds.STATUS_DESC  " +
            "END AS \"statusDesc\"  " +
            "FROM " +
            "TBL_DEVICE_INFO tdi " +
            "LEFT JOIN TBL_DEVICE_STATUS tds ON tds.DEVICE_ID = tdi.ID  " +
            "WHERE " +
            "tdi.DEVICE_TYPE =#{deviceType}")
    public List<DeviceInfoVo> selectDeviceInfo(@Param("deviceType") Integer deviceType);

}
