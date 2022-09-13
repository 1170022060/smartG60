package com.pingok.datacenter.service.simulatedSorting.impl;

import com.pingok.datacenter.domain.simulatedSorting.TblSimulatedSorting;
import com.pingok.datacenter.domain.simulatedSorting.vo.SimulatedSortingVo;
import com.pingok.datacenter.mapper.simulatedSorting.TblSimulatedSortingMapper;
import com.pingok.datacenter.service.simulatedSorting.ISimulatedSortingService;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 模拟清分 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SimulatedSortingServiceImpl implements ISimulatedSortingService {

    @Autowired
    private TblSimulatedSortingMapper tblSimulatedSortingMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public List<SimulatedSortingVo> dayStatistics(String startTime, String endTime) {
        return tblSimulatedSortingMapper.dayStatistics(startTime, endTime);
    }

    @Override
    public void simulatedSorting(String year, String startTime, String endTime) {
        List<TblSimulatedSorting> list = tblSimulatedSortingMapper.simulatedSorting(year, startTime, endTime);
        for (TblSimulatedSorting info : list) {
            info.setId(remoteIdProducerService.nextId());
            tblSimulatedSortingMapper.insert(info);
        }
    }
}
