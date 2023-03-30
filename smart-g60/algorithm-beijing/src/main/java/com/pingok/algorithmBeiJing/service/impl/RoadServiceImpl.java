package com.pingok.algorithmBeiJing.service.impl;

import com.alibaba.fastjson.JSON;
import com.pingok.algorithmBeiJing.domain.*;
import com.pingok.algorithmBeiJing.mapper.*;
import com.pingok.algorithmBeiJing.service.IRoadService;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @time 2022/5/20 12:23
 */
@Slf4j
@Service
public class RoadServiceImpl implements IRoadService {

    @Autowired
    private TblRoadInfoMapper tblRoadInfoMapper;

    @Autowired
    private TblRoadNodesInfoMapper tblRoadNodesInfoMapper;

    @Autowired
    private TblRoadVolumeVelocityMapper tblRoadVolumeVelocityMapper;

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Autowired
    private TblRoadForecastMapper tblRoadForecastMapper;

    @Autowired
    private TblRoadStatisEventInfoMapper tblRoadStatisEventInfoMapper;

    @Autowired
    private TblRoadVideoEventInfoMapper tblRoadVideoEventInfoMapper;

    @Autowired
    private TblRoadProfitPredMapper tblRoadProfitPredMapper;

    @Autowired
    private TblRoadCo2PerProfitMapper tblRoadCo2PerProfitMapper;

    @Override
    public void addRoadCo2PerProfit(TblRoadCo2PerProfit tblRoadCo2PerProfit) {
        tblRoadCo2PerProfit.setId(remoteIdProducerService.nextId());
        tblRoadCo2PerProfitMapper.insert(tblRoadCo2PerProfit);
    }

    @Override
    public void addRoadProfitPred(TblRoadProfitPred tblRoadProfitPred) {
        tblRoadProfitPred.setId(remoteIdProducerService.nextId());
        tblRoadProfitPredMapper.insert(tblRoadProfitPred);
    }

    @Override
    public void addRoadVideoEvent(TblRoadVideoEventInfo tblRoadVideoEventInfo) {
        tblRoadVideoEventInfo.setId(tblRoadVideoEventInfo.getId());
        tblRoadVideoEventInfoMapper.insert(tblRoadVideoEventInfo);
    }

    @Override
    public void addRoadStatisEvent(TblRoadStatisEventInfo tblRoadStatisEventInfo) {
        tblRoadStatisEventInfo.setId(remoteIdProducerService.nextId());
        tblRoadStatisEventInfoMapper.insert(tblRoadStatisEventInfo);
    }

    @Override
    public void addRoadForecast(TblRoadForecast tblRoadForecast) {
        tblRoadForecast.setId(remoteIdProducerService.nextId());
        tblRoadForecastMapper.insert(tblRoadForecast);
    }

    @Override
    public void addRoadVolumeVelocity(TblRoadVolumeVelocity tblRoadVolumeVelocity) {
        tblRoadVolumeVelocity.setId(remoteIdProducerService.nextId());
        tblRoadVolumeVelocityMapper.insert(tblRoadVolumeVelocity);
    }

    @Override
    public List<TblRoadInfo> selectAll() {
        return tblRoadInfoMapper.selectAll();
    }

    @Override
    public List<TblRoadNodesInfo> selectAllNode() {
        List<TblRoadNodesInfo> list = tblRoadNodesInfoMapper.selectAll();
        if (list != null && list.size() > 0) {
            for (TblRoadNodesInfo t : list) {
                if (StringUtils.isNotEmpty(t.getGeoStr())) {
                    t.setGeo(JSON.parseArray(t.getGeoStr()));
                }
            }
        }
        return list;
    }
}
