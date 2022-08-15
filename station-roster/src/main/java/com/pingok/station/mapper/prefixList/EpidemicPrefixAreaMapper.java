package com.pingok.station.mapper.prefixList;

import com.pingok.station.domain.prefixList.EpidemicPrefixArea;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EpidemicPrefixAreaMapper {
    /**
     * 中高风险地区车牌前缀名单全量入库
     *
     * @param epidemicPrefixArea
     * @return 结果
     */
    public int insertPrefix(EpidemicPrefixArea epidemicPrefixArea);

    /**
     * 清除中高风险地区车牌前缀名单
     *
     * @return 结果
     */
    public void deleteAll();
}
