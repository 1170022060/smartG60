package com.pingok.vocational.service.parkingLot.impl;

import com.pingok.vocational.mapper.parkingLot.TblSmartToiletCubicleMapper;
import com.pingok.vocational.service.parkingLot.ISmartToiletCubicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Service
public class SmartToiletCubicleImpl implements ISmartToiletCubicleService {
    @Autowired
    private TblSmartToiletCubicleMapper tblSmartToiletCubicleMapper;

    @Override
    public List<Map> getToiletCubicleTotal() {
        return tblSmartToiletCubicleMapper.getCubicleTotal();
    }
}
