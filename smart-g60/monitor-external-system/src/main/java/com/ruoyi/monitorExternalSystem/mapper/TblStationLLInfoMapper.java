package com.ruoyi.monitorExternalSystem.mapper;

import com.ruoyi.common.core.mapper.CommonRepository;
import com.ruoyi.monitorExternalSystem.domain.TblStationLLInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TblStationLLInfoMapper extends CommonRepository<TblStationLLInfo> {
    @Select("select * from TBL_STATION_LL_INFO where 1=1")
    List<TblStationLLInfo> getStationInfo();
}
