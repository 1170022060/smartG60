package com.pingok.devicemonitor.mapper.smartToilet;

import com.pingok.devicemonitor.domain.smartToilet.TblSmartToiletHealth;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.hibernate.validator.constraints.NotEmpty;

@Mapper
public interface TblSmartToiletHealthMapper extends CommonRepository<TblSmartToiletHealth> {
}