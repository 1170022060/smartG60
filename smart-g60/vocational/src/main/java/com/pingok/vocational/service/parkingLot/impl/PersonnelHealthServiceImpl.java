package com.pingok.vocational.service.parkingLot.impl;

import com.pingok.vocational.domain.parkingLot.TblPersonnelHealth;
import com.pingok.vocational.mapper.parkingLot.TblPersonnelHealthMapper;
import com.pingok.vocational.service.parkingLot.IPersonnelHealthService;
import com.ruoyi.common.core.utils.PinYinUtil;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 人员健康信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class PersonnelHealthServiceImpl implements IPersonnelHealthService {

    @Autowired
    private TblPersonnelHealthMapper tblPersonnelHealthMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public TblPersonnelHealth selectPersonnelHealthById(Long Id) {
        return tblPersonnelHealthMapper.selectByPrimaryKey(Id);
    }

    @Override
    public List<Map> selectPersonnelHealth(String name, Long fieldId, Date date) {
        return tblPersonnelHealthMapper.selectPersonnelHealth(name,fieldId,date);
    }

    @Override
    public List<Map> selectHealthStatistics(Integer type, Long fieldId, Date date) {
        return tblPersonnelHealthMapper.selectHealthStatistics(type,fieldId,date);
    }

    @Override
    public List<Map> selectHealthMonitor(Date date) {
        return tblPersonnelHealthMapper.selectHealthMonitor(date);
    }

    @Override
    public int insertPersonnelHealth(TblPersonnelHealth tblPersonnelHealth) {
        tblPersonnelHealth.setId(remoteIdProducerService.nextId());
        tblPersonnelHealth.setCreateTime(new Date());
        tblPersonnelHealth.setCreateUserId(SecurityUtils.getUserId());
        return tblPersonnelHealthMapper.insert(tblPersonnelHealth);
    }

    @Override
    public int updatePersonnelHealth(TblPersonnelHealth tblPersonnelHealth) {
        tblPersonnelHealth.setUpdateTime(new Date());
        tblPersonnelHealth.setUpdateUserId(SecurityUtils.getUserId());
        return tblPersonnelHealthMapper.updateByPrimaryKeySelective(tblPersonnelHealth);
    }
}
