package com.pingok.station.mapper.cardBlacklist;

import com.pingok.station.domain.cardBlacklist.BCardAppend;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BCardAppendMapper {
    /**
     * 黑名单增量入库
     *
     * @param bCardAppend
     * @return 结果
     */
    public int insertBCardAppend(BCardAppend bCardAppend);
}
