package com.pingok.vocational.mapper.parkingLot;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Mapper
public interface TblSmartToiletCubicleMapper {
    @Select("SELECT stc.SER_ID as \"serId\" ," +
            "SUM(DECODE(stc.STATUS, 0, 1,0)) as \"free\"," +
            "SUM(DECODE(stc.STATUS, 1, 1,0)) as \"use\"," +
            "SUM(DECODE(stc.STATUS, 2, 1,0)) as \"timeout\"," +
            "SUM(DECODE(stc.STATUS, 3, 1,0)) as \"fault\"  " +
            "FROM TBL_SMART_TOILET_CUBICLE stc GROUP BY stc.SER_ID ORDER BY stc.SER_ID")
    List<Map> getCubicleTotal();
}
