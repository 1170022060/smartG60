package com.pingok.vocational.mapper.device;

import com.pingok.vocational.domain.device.TblGantryInfoDtl;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * TBL_GANTRY_INFO_DTL 数据层
 *
 * @author xia
 */
public interface TblGantryInfoDtlMapper extends CommonRepository<TblGantryInfoDtl> {

    @Select("select ID as \"id\" from TBL_GANTRY_INFO_DTL where INFO_ID= #{infoId} " )
    public Long[] selectDtlId(@Param("infoId")Long infoId);
}
