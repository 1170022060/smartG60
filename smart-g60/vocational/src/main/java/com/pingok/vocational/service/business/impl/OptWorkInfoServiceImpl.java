package com.pingok.vocational.service.business.impl;

import com.pingok.vocational.mapper.business.TblOptWorkInfoMapper;
import com.pingok.vocational.service.business.IOptWorkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 操作员工班信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class OptWorkInfoServiceImpl implements IOptWorkInfoService {
    @Autowired
    private TblOptWorkInfoMapper tblOptWorkInfoMapper;

    @Override
    public List<Map> selectOptWorkInfo(Date startDate, Date endDate, String stationId, String optName, Integer shift) {
        return tblOptWorkInfoMapper.selectOptWorkInfo( startDate,  endDate,  stationId,  optName,  shift);
    }
}
