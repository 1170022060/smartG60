package com.pingok.datacenter.mapper.roster;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface VersionMapper {

    @Select({"<script>" +
            "select Version from ${tableName} where LENGTH(VERSION)=12 and ROWNUM=1 order by VERSION desc " +
            "</script>"})
    String selectVersion(@Param("tableName") String tableName);
}
