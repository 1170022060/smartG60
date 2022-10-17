package com.pingok.monitor.service.faultAlarm.impl;

import com.pingok.monitor.domain.device.TblDeviceFault;
import com.pingok.monitor.domain.parkingLot.TblParkingVehicleInfo;
import com.pingok.monitor.domain.smartToilet.TblSmartToiletCubicle;
import com.pingok.monitor.mapper.device.TblDeviceFaultMapper;
import com.pingok.monitor.mapper.faultAlarm.FaultAlarmMapper;
import com.pingok.monitor.mapper.parkingLot.ParkingLotMapper;
import com.pingok.monitor.mapper.smartToilet.TblSmartToiletCubicleMapper;
import com.pingok.monitor.service.faultAlarm.IFaultAlarmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Service
public class FaultAlarmServiceImpl implements IFaultAlarmService {
    @Autowired
    private FaultAlarmMapper faultAlarmMapper;
    @Autowired
    private TblSmartToiletCubicleMapper tblSmartToiletCubicleMapper;
    @Autowired
    private TblDeviceFaultMapper tblDeviceFaultMapper;
    @Autowired
    private ParkingLotMapper parkingLotMapper;
    @Override
    public List<Map> getFaultAlarm() {
        return faultAlarmMapper.getFaultAlarm();
    }

    @Override
    public int alarmConfirm(Integer type, Long id, Integer alarmStatus) {
        if (type == 1){
//            设备告警
            TblDeviceFault tblDeviceFault = tblDeviceFaultMapper.selectByPrimaryKey(id);
            tblDeviceFault.setStatus(alarmStatus);
            return tblDeviceFaultMapper.updateByPrimaryKey(tblDeviceFault);
        }else if (type == 2){
//            停车超时告警
            TblParkingVehicleInfo tblParkingVehicleInfo = parkingLotMapper.selectByPrimaryKey(id);
            tblParkingVehicleInfo.setAlarmStatus(alarmStatus);
            return parkingLotMapper.updateByPrimaryKey(tblParkingVehicleInfo);
        }else if (type == 3){
//            坑位超时或故障告警
            TblSmartToiletCubicle tblSmartToiletCubicle = tblSmartToiletCubicleMapper.selectByPrimaryKey(id);
            tblSmartToiletCubicle.setAlarmStatus(alarmStatus);
            return tblSmartToiletCubicleMapper.updateByPrimaryKey(tblSmartToiletCubicle);
        }
        return 0;
    }
}
