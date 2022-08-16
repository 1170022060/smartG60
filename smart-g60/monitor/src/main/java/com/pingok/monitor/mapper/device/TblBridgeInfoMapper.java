package com.pingok.monitor.mapper.device;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_BRIDGE_INFO 数据层
 *
 * @author xia
 */
public interface TblBridgeInfoMapper {

    @Select("select ID as \"id\" ," +
            "NAME as \"name\" ," +
            "SERIAL_NO as \"serialNo\" ," +
            "'[' || LONGITUDE ||','|| LATITUDE || ']' as \"gps\"  from TBL_BRIDGE_INFO ")
    List<Map> selectBridgeInfo();
}
