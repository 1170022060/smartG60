package com.pingok.vocational.mapper.parkingLot;

import com.pingok.vocational.domain.parkingLot.TblParkingLot;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_PARKING_LOT 数据层
 *
 * @author qiu
 */
public interface TblParkingLotMapper extends CommonRepository<TblParkingLot> {

    @Select("select tpl.ID as \"id\"," +
            "tfi.FIELD_NAME || tpl.REGION_NAME as \"regionName\"," +
            "tpl.TOTAL-tpl.SURPLUS as \"occupy\"," +
            "tpl.SURPLUS as \"surplus\" from TBL_PARKING_LOT tpl " +
            "left join TBL_FIELD_INFO tfi on tfi.ID=tpl.FIELD_ID ")
    List<Map> parkingPlace();

}
