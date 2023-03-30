package com.pingok.devicemonitor.mapper.gantry;

import com.pingok.devicemonitor.domain.gantry.TblGantryPicture;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author
 * @time 2022/5/20 18:00
 */
@Mapper
public interface TblGantryPictureMapper extends CommonRepository<TblGantryPicture> {

    int addTblGantryPicture(TblGantryPicture tblGantryPicture);
}
