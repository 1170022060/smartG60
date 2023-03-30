package com.pingok.vocational.service.maintenance.impl;

import com.pingok.vocational.domain.maintenance.TblMaintainCarGps;
import com.pingok.vocational.mapper.maintenance.TblMaintainCarGpsMapper;
import com.pingok.vocational.service.maintenance.IMaintainCarGpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 养护车辆GPS信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class MaintainCarGpsServiceImpl implements IMaintainCarGpsService {

    @Autowired
    private TblMaintainCarGpsMapper tblMaintainCarGpsMapper;

    @Override
    public List<TblMaintainCarGps> selectAll() {
        return tblMaintainCarGpsMapper.selectAll();
    }

    @Override
    public List<Map> selectInfo(String vehPlate) {
        return tblMaintainCarGpsMapper.selectInfo(vehPlate);
    }
}
