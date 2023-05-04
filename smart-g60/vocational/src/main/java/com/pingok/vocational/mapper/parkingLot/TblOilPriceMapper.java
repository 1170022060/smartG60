package com.pingok.vocational.mapper.parkingLot;

import com.pingok.vocational.domain.parkingLot.TblOilPrice;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Select({"<script>"+
            "select " +
            "to_char(a.TRANS_DATE,'yyyy-mm-dd hh24:mi:ss') as \"transDate\"," +
            "b.NICK_NAME as \"createUser\"," +
            "a.PRICE95 as \"price95\"," +
            "a.PRICE98 as \"price98\"," +
            "a.PRICE92 as \"price92\"," +
            "a.PRICE0 as \"price0\" " +
            "from TBL_OIL_PRICE a " +
            "left join  SYS_USER b on a.CREATE_USER_ID=b.USER_ID " +
            "where 1=1 " +
            "<when test='date != null'> " +
            " and to_date(to_char(TRANS_DATE,'yyyy-mm-dd'),'yyyy-mm-dd') = #{date} " +
            "</when>" +
            "order by TRANS_DATE DESC " +
            "</script>"})
    List<Map> getOilList(@Param("date")Date date);
}
