package com.pingok.gantry.mapper.gantry;

import com.pingok.gantry.domain.entity.gantry.DfsGantryBaseinfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 */
public interface DfsGantryBaseinfoMapper extends CommonRepository<DfsGantryBaseinfo> {
    @Select("select * from dfs_gantry_baseinfo where StateTime between #{startTime} and #{endTime}")
    List<DfsGantryBaseinfo> findByStateTime(@Param("startTime")String startTime, @Param("endTime") String endTime);
}
