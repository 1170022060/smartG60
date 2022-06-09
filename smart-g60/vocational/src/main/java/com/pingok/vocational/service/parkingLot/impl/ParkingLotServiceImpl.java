package com.pingok.vocational.service.parkingLot.impl;

import com.pingok.vocational.domain.parkingLot.TblParkingLot;
import com.pingok.vocational.domain.parkingLot.TblParkingVehicleInfo;
import com.pingok.vocational.mapper.parkingLot.TblParkingLotMapper;
import com.pingok.vocational.mapper.parkingLot.TblParkingVehicleInfoMapper;
import com.pingok.vocational.service.parkingLot.IParkingLotService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
