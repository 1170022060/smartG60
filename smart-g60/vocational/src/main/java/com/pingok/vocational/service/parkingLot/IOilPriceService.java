package com.pingok.vocational.service.parkingLot;

import com.pingok.vocational.domain.parkingLot.TblOilPrice;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 今日油价 业务层
 *
 * @author xia
 */
public interface IOilPriceService {

    TblOilPrice selectOilPrice(Date date);
    List<Map> selectOilPriceList(Date date);
    TblOilPrice selectOilPriceById(Long Id);
    int insertOilPrice(TblOilPrice tblOilPrice);
    int updateOilPrice(TblOilPrice tblOilPrice);
    public String checkDateUnique(TblOilPrice tblOilPrice);
}
