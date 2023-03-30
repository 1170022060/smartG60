package com.pingok.algorithmBeiJing.service;

import com.pingok.algorithmBeiJing.domain.*;

import java.util.List;

public interface IRoadService {


    /**
     * 碳排放数据入库
     * @param tblRoadCo2PerProfit
     */
    void addRoadCo2PerProfit(TblRoadCo2PerProfit tblRoadCo2PerProfit);

    /**
     * 收益预测信息入库
     * @param tblRoadProfitPred
     */
    void addRoadProfitPred(TblRoadProfitPred tblRoadProfitPred);

    /**
     * 插入视频类事件
     *
     * @param tblRoadVideoEventInfo
     */
    void addRoadVideoEvent(TblRoadVideoEventInfo tblRoadVideoEventInfo);

    /**
     * 插入统计类事件
     *
     * @param tblRoadStatisEventInfo
     */
    void addRoadStatisEvent(TblRoadStatisEventInfo tblRoadStatisEventInfo);

    /**
     * 插入预测类数据
     *
     * @param tblRoadForecast
     */
    void addRoadForecast(TblRoadForecast tblRoadForecast);

    /**
     * 插入流量流速计算结果
     *
     * @param tblRoadVolumeVelocity
     */
    void addRoadVolumeVelocity(TblRoadVolumeVelocity tblRoadVolumeVelocity);

    /**
     * 查询所有路段
     *
     * @return
     */
    List<TblRoadInfo> selectAll();

    /**
     * 查询所有路段节点
     *
     * @return
     */
    List<TblRoadNodesInfo> selectAllNode();

}
