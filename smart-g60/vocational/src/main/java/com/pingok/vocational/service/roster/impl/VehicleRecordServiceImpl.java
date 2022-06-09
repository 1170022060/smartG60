package com.pingok.vocational.service.roster.impl;

import com.pingok.vocational.mapper.roster.TblVehicleRecordMapper;
import com.pingok.vocational.service.roster.IVehicleRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 车辆通行记录 服务层处理
 *
 * @author ruoyi
 */
@Service
public class VehicleRecordServiceImpl implements IVehicleRecordService {
    @Autowired
    private TblVehicleRecordMapper tblVehicleRecordMapper;

    @Override
    public List<Map> selectVehicleRecord(String vehPlate) {
        return tblVehicleRecordMapper.selectVehicleRecord(vehPlate);
    }
}
