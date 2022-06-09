package com.pingok.datacenter.service.lane;

import com.pingok.datacenter.domain.lane.TblLaneStatus;

/**
 * 车道状态 业务层
 *
 * @author qiumin
 */
public interface ILaneStatusService {
    /**
     * 上传车道状态
     * @param tblLaneStatus 车道状态
     */
    void update(TblLaneStatus tblLaneStatus);
}
