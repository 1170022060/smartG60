package com.pingok.vocational.service.parkingLot;

import com.pingok.vocational.domain.parkingLot.TblOilPrice;

import java.util.Date;

/**
 * 今日油价 业务层
 *
 * @author xia
 */
public interface IOilPriceService {

    TblOilPrice selectOilPrice(Date date);
}
