package com.pingok.vocational.service.road;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IRoadService {

    /**
     * 查询道路视频事件信息
     * @return
     */
    List<Map> selectRoadVideoEvent(Integer type);

    /**
     * 查询道路统计事件信息
     * @return
     */
    List<Map> selectRoadStatisEvent(String vehPlate, Date startTime,Date endTime);
}
