package com.pingok.vocational.service.blackCard;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IBlackCardService {
    List<Map> getNowList(String mediaId, Date startDate, Date endDate);
    List<Map> getStationUsedList();
    List<Map> getRecordList(String mediaId);
}
