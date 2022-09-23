package com.pingok.charge.service.lane.optdeal;

import com.pingok.charge.domain.lane.optdeal.TblOptInfo;

import java.util.Date;
import java.util.List;

public interface IOptDealService {
    List<TblOptInfo> query(String startTime, String endTime, String belongStation);
}
