package com.pingok.vocational.service.parkingLot.impl;

import com.pingok.vocational.domain.parkingLot.TblParkingLot;
import com.pingok.vocational.domain.parkingLot.TblParkingVehicleInfo;
import com.pingok.vocational.mapper.parkingLot.TblParkingLotMapper;
import com.pingok.vocational.mapper.parkingLot.TblParkingStatisticsMapper;
import com.pingok.vocational.mapper.parkingLot.TblParkingVehicleInfoMapper;
import com.pingok.vocational.service.parkingLot.IParkingLotService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 停车场 服务层实现层
 *
 * @author qiumin
 */
@Service
public class ParkingLotServiceImpl implements IParkingLotService {

    @Autowired
    private TblParkingVehicleInfoMapper tblParkingVehicleInfoMapper;
    @Autowired
    private TblParkingLotMapper tblParkingLotMapper;
    @Autowired
    private TblParkingStatisticsMapper tblParkingStatisticsMapper;

    @Override
    @Transactional
    public void driveAway(Long id) {
        TblParkingVehicleInfo tblParkingVehicleInfo = tblParkingVehicleInfoMapper.selectByPrimaryKey(id);
        tblParkingVehicleInfo.setExTime(DateUtils.getNowDate());
        tblParkingVehicleInfo.setUpdateTime(DateUtils.getNowDate());
        tblParkingVehicleInfo.setUpdateUserId(SecurityUtils.getUserId());
        tblParkingVehicleInfoMapper.updateByPrimaryKey(tblParkingVehicleInfo);

        TblParkingLot tblParkingLot = tblParkingLotMapper.selectByPrimaryKey(tblParkingVehicleInfo.getParkingId());
        tblParkingLot.setSurplus(tblParkingLot.getSurplus() + 1);
        tblParkingLot.setUpdateUserId(SecurityUtils.getUserId());
        tblParkingLot.setUpdateTime(DateUtils.getNowDate());
        tblParkingLotMapper.updateByPrimaryKey(tblParkingLot);
    }

    @Override
    public void updateSurplus(Long id, Integer surplus) {
        TblParkingLot tblParkingLot = tblParkingLotMapper.selectByPrimaryKey(id);
        if (surplus > tblParkingLot.getTotal()) {
            throw new ServiceException("剩余车位数不能大于总车位数");
        }
        tblParkingLot.setSurplus(surplus);
        tblParkingLot.setUpdateTime(DateUtils.getNowDate());
        tblParkingLot.setUpdateUserId(SecurityUtils.getUserId());
        tblParkingLotMapper.updateByPrimaryKey(tblParkingLot);
    }

    @Override
    public List<Map> trafficChange(Date date) {
        List<Map> trafficList=tblParkingStatisticsMapper.trafficSum(date);
        for(Map map :trafficList)
        {
            Map trafficCurrent=tblParkingVehicleInfoMapper.trafficCurrent(Long.parseLong(map.get("fieldId").toString()));
            map.putAll(trafficCurrent);

            Map map1=new HashMap<>();
            Integer trafficDailyBus=tblParkingStatisticsMapper.trafficDaily(date,Long.parseLong(map.get("fieldId").toString()),1);
            Integer trafficDailyTruck=tblParkingStatisticsMapper.trafficDaily(date,Long.parseLong(map.get("fieldId").toString()),2);
            map1.put("dailyCumulativeBus",trafficDailyBus);
            map1.put("dailyCumulativeTruck",trafficDailyTruck);

            map.putAll(map1);

            List<Map> trafficStatisticsBus=tblParkingStatisticsMapper.trafficStatistics(date,Long.parseLong(map.get("fieldId").toString()),1);
            map.put("hourStatisticsBus",trafficStatisticsBus);
            List<Map> trafficStatisticsTruck=tblParkingStatisticsMapper.trafficStatistics(date,Long.parseLong(map.get("fieldId").toString()),2);
            map.put("hourStatisticsTruck",trafficStatisticsTruck);
        }
        return trafficList;
    }

    @Override
    public List<Map> parkingPlace() {
        List<Map> parkingPlaceList=tblParkingLotMapper.parkingPlace();
        for(Map map :parkingPlaceList)
        {
            Map overtime=tblParkingVehicleInfoMapper.overtime(Long.parseLong(map.get("id").toString()));
            map.putAll(overtime);
        }
        return parkingPlaceList;
    }
}
