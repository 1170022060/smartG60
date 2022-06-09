package com.pingok.monitor.service.lane;

import com.pingok.monitor.domain.lane.TblLaneStatus;
import com.pingok.monitor.domain.lane.vo.LaneEnum;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
}
