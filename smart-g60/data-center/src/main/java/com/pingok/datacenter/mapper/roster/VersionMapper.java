package com.pingok.datacenter.mapper.roster;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface VersionMapper {

    @Select("select * from ( " +
            "select Version ," +
            "row_number() over(order by VERSION desc) as \"rn\" " +
            "from TBL_BLACK_CARD_VERSION " +
            "where LENGTH(VERSION)=12 " +
            ") a where a.\"rn\"=1 ")
    String selectVersion(@Param("tableName") String tableName);

    @Select("select * from ( " +
            "select Version ," +
            "row_number() over(order by VERSION desc) as \"rn\" " +
            "from TBL_BLACK_CARD_VERSION " +
            "where LENGTH(VERSION)=8 " +
            ") a where a.\"rn\"=1 ")
    String selectVersionAll(@Param("tableName") String tableName);
}
