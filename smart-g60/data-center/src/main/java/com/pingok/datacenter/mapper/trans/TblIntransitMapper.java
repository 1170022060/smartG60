package com.pingok.datacenter.mapper.trans;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TblIntransitMapper {
    @Select("select " +
            "ETC_LOCAL as \"etcLocal\", " +
            "ETC_ELSE as \"etcElse\", " +
            "MTC_SINGLE as \"mtcSingle\", " +
            "MTC_TRANS as \"mtcTrans\", " +
            "LICENSE as \"license\", " +
            "CREATE_TIME as \"createTime\" " +
            "from TBL_INTRANSIT_RECORD ")
    List<Map> selectIntransitFlow();
}
