package com.pingok.api.service.beidou.impl;

import com.pingok.api.mapper.beidou.TblMaintainCarGpsMapper;
import com.pingok.api.service.beidou.IBeiDouService;
import com.ruoyi.system.api.domain.gps.TblMaintainCarGps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BeiDouServiceImpl implements IBeiDouService {

    @Autowired
    private TblMaintainCarGpsMapper tblMaintainCarGpsMapper;

    @Override
    public List<TblMaintainCarGps> list() {
        return tblMaintainCarGpsMapper.selectAll();
    }
}
