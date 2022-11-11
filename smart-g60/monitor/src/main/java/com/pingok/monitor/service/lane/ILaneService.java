package com.pingok.monitor.service.lane;

import com.pingok.monitor.domain.lane.TblLaneStatus;
import com.pingok.monitor.domain.lane.vo.LaneEnum;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 车道 业务层
 *
 * @author qiumin
 */
public interface ILaneService {
    /**
     * 根据站ID查询车道信息
     * @param stationId 站ID
     * @return
     */
    LaneEnum findByStationId(String stationId);

    /**
     * 获取收费站流水上传状态
     * @return
     */
    List<Map> getStationFlowUpload();

    Object getStationTotalFlow(String currentDate);

    /**
     * 获取站信息
     * @return
     */
    List<Map> getStationInfo();
}
