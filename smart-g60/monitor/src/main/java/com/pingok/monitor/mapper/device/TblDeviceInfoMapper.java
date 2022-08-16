package com.pingok.monitor.mapper.device;

import com.pingok.monitor.domain.device.vo.DeviceInfoVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * TblDeviceInfo 数据层
 *
 * @author xia
 */
public interface TblDeviceInfoMapper {

    @Select("select ID as \"id\" ," +
            "DEVICE_ID as \"deviceId\" ," +
            "DEVICE_NAME as \"deviceName\" ," +
            "DEVICE_BRAND as \"deviceBrand\" ," +
            "DEVICE_MODEL as \"deviceModel\" ," +
            "DEVICE_IP as \"deviceIp\" ," +
            "PORT as \"port\" ," +
            "DIRECTION as \"direction\" ," +
            "STATION_BELONG as \"stationBelong\" ," +
            "PILE_NO as \"pileNo\" ," +
            "GPS as \"gps\" from TBL_DEVICE_INFO " +
            "where DEVICE_TYPE =9")
    public List<DeviceInfoVo> selectVMS();

    @Select("select ID as \"id\" ," +
            "DEVICE_ID as \"deviceId\" ," +
            "DEVICE_NAME as \"deviceName\" ," +
            "DEVICE_BRAND as \"deviceBrand\" ," +
            "DEVICE_MODEL as \"deviceModel\" ," +
            "DEVICE_IP as \"deviceIp\" ," +
            "PORT as \"port\" ," +
            "DIRECTION as \"direction\" ," +
            "STATION_BELONG as \"stationBelong\" ," +
            "PILE_NO as \"pileNo\" ," +
            "GPS as \"gps\" from TBL_DEVICE_INFO " +
            "where DEVICE_TYPE =11")
    public List<DeviceInfoVo> selectVD();

    @Select("select ID as \"id\" ," +
            "DEVICE_ID as \"deviceId\" ," +
            "DEVICE_NAME as \"deviceName\" ," +
            "DEVICE_BRAND as \"deviceBrand\" ," +
            "DEVICE_MODEL as \"deviceModel\" ," +
            "DEVICE_IP as \"deviceIp\" ," +
            "PORT as \"port\" ," +
            "DIRECTION as \"direction\" ," +
            "STATION_BELONG as \"stationBelong\" ," +
            "PILE_NO as \"pileNo\" ," +
            "GPS as \"gps\" from TBL_DEVICE_INFO " +
            "where DEVICE_TYPE =10")
    public List<DeviceInfoVo> selectCAM();

    @Select("select ID as \"id\" ," +
            "DEVICE_ID as \"deviceId\" ," +
            "DEVICE_NAME as \"deviceName\" ," +
            "DEVICE_BRAND as \"deviceBrand\" ," +
            "DEVICE_MODEL as \"deviceModel\" ," +
            "DEVICE_IP as \"deviceIp\" ," +
            "PORT as \"port\" ," +
            "DIRECTION as \"direction\" ," +
            "STATION_BELONG as \"stationBelong\" ," +
            "PILE_NO as \"pileNo\" ," +
            "GPS as \"gps\" from TBL_DEVICE_INFO " +
            "where DEVICE_TYPE =12")
    public List<DeviceInfoVo> selectPilotLight();
}
