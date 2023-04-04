package com.pingok.vocational.mapper.parkingLot;

import com.pingok.vocational.domain.parkingLot.TblOilPrice;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * TBL_OIL_PRICE 数据层
 *
 * @author xia
 */
@Mapper
public interface TblOilPriceMapper extends CommonRepository<TblOilPrice> {

    @Select("select * from (select * from TBL_OIL_PRICE order by CREATE_TIME desc) where rownum = 1")
    TblOilPrice selectNewPrice(@Param("transDate") Date transDate);

    @Select("select * from TBL_OIL_PRICE where TRANS_DATE = #{transDate} ")
    TblOilPrice checkDateUnique(@Param("transDate") Date transDate);

}
