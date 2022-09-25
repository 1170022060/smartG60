package com.pingok.vod.mapper;

import com.pingok.vod.domain.TblDeviceInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TblDeviceInfo 数据层
 *
 * @author qiumin
 */
@Mapper
public interface TblDeviceInfoMapper extends CommonRepository<TblDeviceInfo> {

}
