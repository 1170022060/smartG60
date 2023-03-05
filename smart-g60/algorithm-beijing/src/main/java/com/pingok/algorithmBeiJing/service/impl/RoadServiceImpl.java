package com.pingok.algorithmBeiJing.service.impl;

import com.alibaba.fastjson.JSON;
import com.pingok.algorithmBeiJing.domain.TblRoadInfo;
import com.pingok.algorithmBeiJing.domain.TblRoadNodesInfo;
import com.pingok.algorithmBeiJing.domain.TblRoadVolumeVelocity;
import com.pingok.algorithmBeiJing.mapper.TblRoadInfoMapper;
import com.pingok.algorithmBeiJing.mapper.TblRoadNodesInfoMapper;
import com.pingok.algorithmBeiJing.mapper.TblRoadVolumeVelocityMapper;
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
                if(StringUtils.isNotEmpty(t.getGeoStr())){
                    t.setGeo(JSON.parseArray(t.getGeoStr()));
                }
            }
        }
        return list;
    }
}
