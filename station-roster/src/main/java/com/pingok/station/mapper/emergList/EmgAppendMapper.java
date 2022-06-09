package com.pingok.station.mapper.emergList;

import com.pingok.station.domain.emergList.EmgAppend;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmgAppendMapper {

    /**
     * 抢险救灾名单入库
     *
     * @param emgAppend
     * @return 结果
     */
    public int insertEmgAppend(EmgAppend emgAppend);
}
