package com.pingok.vocational.service.primaryCar.impl;

import com.pingok.vocational.mapper.primaryCar.*;
import com.pingok.vocational.service.primaryCar.IPrimaryCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Service
public class primaryCarServiceImpl implements IPrimaryCarService {

    @Autowired
    private TblPrimaryVehInfoMapper tblPrimaryVehInfoMapper;
    @Autowired
    private TblVehicleTrailInfoMapper tblVehicleTrailInfoMapper;
    @Autowired
    private TblWayBillInfoMapper tblWayBillInfoMapper;
    @Autowired
    private TblPrimaryGpsInfoMapper tblPrimaryGpsInfoMapper;
    @Autowired
    private TblPrimaryGpsInfoLogMapper tblPrimaryGpsInfoLogMapper;
    @Autowired
    private TblOwInfoMapper tblOwInfoMapper;

    @Override
    public List<Map> getPrimaryVehInfo(String vehPlate) {
        return tblPrimaryVehInfoMapper.getPrimaryVehInfo(vehPlate);
    }

    @Override
    public List<Map> getVehTrailInfo(String vehPlate) {
        return tblVehicleTrailInfoMapper.getVehTrailInfo(vehPlate);
    }

    @Override
    public List<Map> getWayBillInfo(String vehPlate) {
        return tblWayBillInfoMapper.getWayBillInfo(vehPlate);
    }

    @Override
    public List<Map> selectPrimaryGpsInfo(String vehPlate) {
        return tblPrimaryGpsInfoMapper.selectPrimaryGpsInfo(vehPlate);
    }

    @Override
    public List<Map> getVehGpsList(String vehPlate) {
        return tblPrimaryGpsInfoLogMapper.getVehGpsList(vehPlate);
    }

    @Override
    public List<Map> selectOwInfo(String vehPlate, Date checkStartTime, Date checkEndTime) {
        return tblOwInfoMapper.selectOwInfo(vehPlate,checkStartTime,checkEndTime);
    }
}
