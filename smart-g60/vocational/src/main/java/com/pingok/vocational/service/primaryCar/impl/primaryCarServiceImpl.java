package com.pingok.vocational.service.primaryCar.impl;

import com.pingok.vocational.mapper.primaryCar.TblPrimaryVehInfoMapper;
import com.pingok.vocational.mapper.primaryCar.TblVehicleTrailInfoMapper;
import com.pingok.vocational.mapper.primaryCar.TblWayBillInfoMapper;
import com.pingok.vocational.service.primaryCar.IPrimaryCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
