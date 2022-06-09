package com.pingok.vocational.service.roster.impl;

import com.pingok.vocational.mapper.roster.TblVehicleStressMapper;
import com.pingok.vocational.service.roster.IVehicleStressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * 重点车辆清单 服务层处理
 *
 * @author ruoyi
 */
@Service
public class VehicleStressServiceImpl implements IVehicleStressService {
    @Autowired
    private TblVehicleStressMapper tblVehicleStressMapper;

    @Override
    public List<Map> selectVehicleStress(Integer vehType,String vehPlate) {
        return  tblVehicleStressMapper.selectVehicleRecord(vehType,vehPlate);
    }
}
