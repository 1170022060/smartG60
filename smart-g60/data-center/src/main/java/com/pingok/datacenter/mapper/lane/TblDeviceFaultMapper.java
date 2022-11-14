package com.pingok.datacenter.mapper.lane;

import com.pingok.datacenter.domain.lane.TblDeviceFault;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

@Mapper
public interface TblDeviceFaultMapper extends CommonRepository<TblDeviceFault> {

    @Select("SELECT to_number(CONFIG_VALUE) FROM  SYS_CONFIG sc WHERE sc.CONFIG_KEY= #{configKey} ")
    BigDecimal value(@Param("configKey") String configKey);

    @Select("SELECT LANE_NAME FROM TBL_LANE_INFO WHERE LANE_HEX= #{laneHex} ")
    String lane(@Param("laneHex") String laneHex);
}