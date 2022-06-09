package com.pingok.station.mapper.obuBlacklist;

import com.pingok.station.domain.obuBlacklist.BObuAppend;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BObuAppendMapper {
    /**
     * OBU黑名单增量入库
     *
     * @param bObuAppend
     * @return 结果
     */
    public int insertBObuAppend(BObuAppend bObuAppend);
}
