package com.pingok.monitor.mapper.device;

import com.pingok.monitor.domain.device.TblDeviceStatus;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TblDeviceStatusMapper extends CommonRepository<TblDeviceStatus> {
}
