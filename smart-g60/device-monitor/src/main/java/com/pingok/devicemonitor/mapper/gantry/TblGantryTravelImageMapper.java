package com.pingok.devicemonitor.mapper.gantry;

import com.pingok.devicemonitor.domain.gantry.TblGantryTravelImage;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TblGantryTravelImageMapper extends CommonRepository<TblGantryTravelImage> {

    int addTblGantryTravelimage(TblGantryTravelImage tblGantryTravelImage);

}