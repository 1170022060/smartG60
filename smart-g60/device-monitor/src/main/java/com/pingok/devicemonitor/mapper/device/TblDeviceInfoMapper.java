package com.pingok.devicemonitor.mapper.device;

import com.pingok.devicemonitor.domain.device.TblDeviceInfo;

import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TblDeviceInfoMapper extends CommonRepository<TblDeviceInfo> {
}