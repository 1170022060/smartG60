package com.pingok.vocational.service.acrossRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IAcrossRecordService {
    /**
     * 查询入口车辆信息
     *
     * @param vehPlate
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map> selectAcrossRecord(String vehPlate, Date startTime, Date endTime);

    /**
     * 查看车辆的通行记录
     *
     * @param time
     * @param passId
     * @return
     */
    List<Map> selectRecord(Date time, String passId);
}
