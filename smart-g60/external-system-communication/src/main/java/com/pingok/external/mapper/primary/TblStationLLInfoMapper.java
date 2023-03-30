package com.pingok.external.mapper.primary;

import com.pingok.external.domain.primary.TblStationLLInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TblStationLLInfoMapper extends CommonRepository<TblStationLLInfo> {
    @Select("select * from TBL_STATION_LL_INFO where 1=1")
    List<TblStationLLInfo> getStationInfo();
}
