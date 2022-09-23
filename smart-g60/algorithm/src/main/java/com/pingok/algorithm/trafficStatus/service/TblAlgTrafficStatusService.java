package com.pingok.algorithm.trafficStatus.service;

import com.pingok.algorithm.trafficStatus.entity.TblAlgTrafficStatus;

import java.util.List;

/**
 * @author
 * 交通状态业务接口
 */
public interface TblAlgTrafficStatusService {

    /**
     * 生成交通状态记录
     * @param direction 方向 0:上行 1:下行
     */
    void saveTrafficStatusService(Integer direction) throws Exception;

    /**
     * 查询最新交通状态列表
     * @param tblAlgTrafficStatus
     * @return
     */
    List<TblAlgTrafficStatus> listByBean(TblAlgTrafficStatus tblAlgTrafficStatus);
}
