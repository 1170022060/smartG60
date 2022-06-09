package com.pingok.charge.service.lane;


import com.pingok.charge.domain.lane.vo.LaneStatus;

/**
 * 车道状态 业务层
 *
 * @author qiumin
 */
public interface ILaneStatusService {
    /**
     * 上传车道状态
     * @param laneStatus 车道状态
     */
    void update(LaneStatus laneStatus);
}
