package com.pingok.devicemonitor.mapper.gantry;

import com.pingok.devicemonitor.domain.gantry.TblGantryEventRelease;
import com.pingok.devicemonitor.domain.gantry.TblGantryInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TblGantryEventReleaseMapper extends CommonRepository<TblGantryEventRelease> {

}