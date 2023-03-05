package com.pingok.algorithmBeiJing.service;

import com.pingok.algorithmBeiJing.domain.TblRoadInfo;
import com.pingok.algorithmBeiJing.domain.TblRoadNodesInfo;
import com.pingok.algorithmBeiJing.domain.TblRoadVolumeVelocity;

import java.util.List;

public interface IRoadService {

    /**
     * 插入流量流速计算结果
     * @param tblRoadVolumeVelocity
     */
    void addRoadVolumeVelocity(TblRoadVolumeVelocity tblRoadVolumeVelocity);

    /**
     * 查询所有路段
     * @return
     */
    List<TblRoadInfo> selectAll();

    /**
     * 查询所有路段节点
     * @return
     */
    List<TblRoadNodesInfo> selectAllNode();

}
