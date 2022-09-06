package com.pingok.monitor.mapper.device;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TblGantryInfoMapper {

    @Select("select ID as \"id\" ," +
            "DEVICE_ID as \"deviceId\" ," +
            "DEVICE_NAME as \"deviceName\" ," +
            "GANTRY_TYPE as \"gantryType\" ," +
            "DIRECTION as \"direction\" ," +
            "PILE_NO as \"pileNo\" ," +
            "GPS as \"gps\" from TBL_GANTRY_INFO ")
    public List<Map> selectGantry();
}
