package com.pingok.datacenter.mapper.sectorlog;

import com.pingok.datacenter.domain.sectorlog.TblSectorLog;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TblSectorLogMapper extends CommonRepository<TblSectorLog> {

    @Select("select count(1) from TBL_SECTOR_LOG where GID = #{gid}")
    int checkFieldNameUnique(@Param("gid") String gid);
}
