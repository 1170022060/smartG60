package com.pingok.vocational.service.parkingLot.impl;

import com.pingok.vocational.domain.parkingLot.TblParkingLot;
import com.pingok.vocational.domain.parkingLot.TblParkingStatistics;
import com.pingok.vocational.domain.parkingLot.TblParkingVehicleInfo;
import com.pingok.vocational.domain.parkingLot.vo.AreaVo;
import com.pingok.vocational.mapper.parkingLot.TblEventPassengerFlowMapper;
import com.pingok.vocational.mapper.parkingLot.TblParkingLotMapper;
import com.pingok.vocational.mapper.parkingLot.TblParkingStatisticsMapper;
import com.pingok.vocational.mapper.parkingLot.TblParkingVehicleInfoMapper;
import com.pingok.vocational.service.parkingLot.IParkingLotService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @Autowired
    private TblEventPassengerFlowMapper tblEventPassengerFlowMapper;

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

    @Override
    public List<Map> passengerFlow(Date date) throws ParseException {

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        String dailyTime=formatter.format(date);

        Integer hour= date.getHours();

        List<Map> passengerFlow=tblEventPassengerFlowMapper.field();
        for(Map map :passengerFlow)
        {
            Map humanFlow=tblParkingVehicleInfoMapper.humanFlow(Long.parseLong(map.get("fieldId").toString()));
            map.putAll(humanFlow);
            List<AreaVo> areaVos=new ArrayList<>();
            if(map.get("fieldName").toString().contains("北"))
            {

                areaVos.add(new AreaVo(1,"北服务区商铺"));
                areaVos.add(new AreaVo(2,"北服务区小超市"));
                areaVos.add(new AreaVo(3,"北服务区男厕"));
                areaVos.add(new AreaVo(4,"北服务区女厕"));
                for(AreaVo areaVo :areaVos)
                {
                    if (areaVo.getAreaId() == 1){
                        areaVo.getDeviceIds().add(181);
                        areaVo.getDeviceIds().add(182);
                    }else if (areaVo.getAreaId() == 2){
                        areaVo.getDeviceIds().add(183);
                    }else if (areaVo.getAreaId() == 3){
                        areaVo.getDeviceIds().add(184);;
                    }else{
                        areaVo.getDeviceIds().add(185);
                    }
                    areaVo.setDailyTotal(tblEventPassengerFlowMapper.dailyTotal(dailyTime,areaVo.getAreaId(),Long.parseLong(map.get("fieldId").toString())));
                    if (tblEventPassengerFlowMapper.actualFlow(dailyTime,areaVo.getAreaId(),Long.parseLong(map.get("fieldId").toString())) == null){
                        areaVo.setActualFlow(0);
                    }else{
                        areaVo.setActualFlow(tblEventPassengerFlowMapper.actualFlow(dailyTime,areaVo.getAreaId(),Long.parseLong(map.get("fieldId").toString())));
                    }
                    areaVo.setPeakFlow(tblEventPassengerFlowMapper.peakFlow(dailyTime,areaVo.getAreaId(),Long.parseLong(map.get("fieldId").toString())));
                    areaVo.setAvgFlow(tblEventPassengerFlowMapper.avgFlow(areaVo.getAreaId(),dailyTime,hour,Long.parseLong(map.get("fieldId").toString())));
                    if (tblEventPassengerFlowMapper.hourFlow(areaVo.getAreaId(),dailyTime,hour,Long.parseLong(map.get("fieldId").toString())) == null){
                        areaVo.setHourFlow(0);
                    }else{
                        areaVo.setHourFlow(tblEventPassengerFlowMapper.hourFlow(areaVo.getAreaId(),dailyTime,hour,Long.parseLong(map.get("fieldId").toString())));
                    }
                    areaVo.setNoMask(tblEventPassengerFlowMapper.noMaskFlow(dailyTime,areaVo.getAreaId(),Long.parseLong(map.get("fieldId").toString())));
                }
                map.put("area",areaVos);
            }
            if(map.get("fieldName").toString().contains("南"))
            {
                areaVos.add(new AreaVo(1,"南服务区商铺"));
                areaVos.add(new AreaVo(2,"南服务区超市"));
                areaVos.add(new AreaVo(3,"南服务区男厕"));
                areaVos.add(new AreaVo(4,"南服务区女厕"));
                areaVos.add(new AreaVo(5,"南服务区艺海棠"));
                areaVos.add(new AreaVo(6,"南服务区司机之家"));
                for(AreaVo areaVo :areaVos)
                {
                    if (areaVo.getAreaId() == 1){
                        areaVo.getDeviceIds().add(186);
                        areaVo.getDeviceIds().add(187);
                        areaVo.getDeviceIds().add(188);
                    }else if (areaVo.getAreaId() == 2){
                        areaVo.getDeviceIds().add(190);
                        areaVo.getDeviceIds().add(191);
                    }else if (areaVo.getAreaId() == 3){
                        areaVo.getDeviceIds().add(192);
                    }else if (areaVo.getAreaId() == 4){
                        areaVo.getDeviceIds().add(193);
                        areaVo.getDeviceIds().add(194);
                    }else if (areaVo.getAreaId() == 5){
                        areaVo.getDeviceIds().add(189);
                    }else{
                        areaVo.getDeviceIds().add(195);
                    }
                    areaVo.setDailyTotal(tblEventPassengerFlowMapper.dailyTotal(dailyTime,areaVo.getAreaId(),Long.parseLong(map.get("fieldId").toString())));
                    if (tblEventPassengerFlowMapper.actualFlow(dailyTime,areaVo.getAreaId(),Long.parseLong(map.get("fieldId").toString())) == null){
                        areaVo.setActualFlow(0);
                    }else{
                        areaVo.setActualFlow(tblEventPassengerFlowMapper.actualFlow(dailyTime,areaVo.getAreaId(),Long.parseLong(map.get("fieldId").toString())));
                    }
                    areaVo.setPeakFlow(tblEventPassengerFlowMapper.peakFlow(dailyTime,areaVo.getAreaId(),Long.parseLong(map.get("fieldId").toString())));
                    areaVo.setAvgFlow(tblEventPassengerFlowMapper.avgFlow(areaVo.getAreaId(),dailyTime,hour,Long.parseLong(map.get("fieldId").toString())));
                    if (tblEventPassengerFlowMapper.hourFlow(areaVo.getAreaId(),dailyTime,hour,Long.parseLong(map.get("fieldId").toString())) == null){
                        areaVo.setHourFlow(0);
                    }else{
                        areaVo.setHourFlow(tblEventPassengerFlowMapper.hourFlow(areaVo.getAreaId(),dailyTime,hour,Long.parseLong(map.get("fieldId").toString())));
                    }
                    areaVo.setNoMask(tblEventPassengerFlowMapper.noMaskFlow(dailyTime,areaVo.getAreaId(),Long.parseLong(map.get("fieldId").toString())));
                }
                map.put("area",areaVos);
            }
        }
        return passengerFlow;
    }
    @Override
    public List<Map> parkMonitor(String fieldNum, String regionName) {
        List<Map> findByFieldRegion=tblParkingLotMapper.findByFieldRegion(fieldNum,regionName);
        for(Map map :findByFieldRegion)
        {
            Integer count=tblParkingLotMapper.count(Long.parseLong(map.get("id").toString()));
            map.put("overtime",count);
        }
        return findByFieldRegion;
    }

    @Override
    public List<Map> overtimeInfo(String fieldNum, String regionName) {
        return tblParkingVehicleInfoMapper.overtimeInfo(fieldNum,regionName);
    }

    @Override
    public List<Map> selectRegionName(String fieldNum) {
        return tblParkingLotMapper.selectRegionName(fieldNum);
    }

    @Override
    public List<Map> traffic(String fieldNum, Integer vehType, Date startDate, Date endDate,Integer statisticsType) {
        return tblParkingStatisticsMapper.traffic(fieldNum,vehType,startDate,endDate,statisticsType);
    }

    @Override
    public Map trafficCount(String fieldNum, Integer vehType, Date startDate, Date endDate) {
        return tblParkingStatisticsMapper.trafficCount(fieldNum,vehType,startDate,endDate);
    }

    @Override
    public List<Map> humanFlow(String fieldNum, Integer areaId, Date startDate, Date endDate, Integer statisticsType) {
        return tblEventPassengerFlowMapper.humanFlow(fieldNum,areaId,startDate,endDate,statisticsType);
    }

    @Override
    public Map humanFlowCount(String fieldNum, Integer areaId, Date startDate, Date endDate) {
        return tblEventPassengerFlowMapper.humanFlowCount(fieldNum,areaId,startDate,endDate);
    }
}
