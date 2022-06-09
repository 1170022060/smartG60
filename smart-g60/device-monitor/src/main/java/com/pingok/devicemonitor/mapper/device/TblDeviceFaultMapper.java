package com.pingok.devicemonitor.mapper.device;

import com.pingok.devicemonitor.domain.device.TblDeviceFault;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TblDeviceFaultMapper extends CommonRepository<TblDeviceFault> {
}