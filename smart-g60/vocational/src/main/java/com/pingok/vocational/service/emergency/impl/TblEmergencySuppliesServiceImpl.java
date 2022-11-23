package com.pingok.vocational.service.emergency.impl;

import com.pingok.vocational.domain.emergency.TblEmergencySupplies;
import com.pingok.vocational.mapper.emergency.TblEmergencySuppliesMapper;
import com.pingok.vocational.service.emergency.TblEmergencySuppliesService;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 应急资源信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class TblEmergencySuppliesServiceImpl implements TblEmergencySuppliesService {
    @Autowired
    private TblEmergencySuppliesMapper tblEmergencySuppliesMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public TblEmergencySupplies selectEmergencySuppliesById(Long Id) {
        return tblEmergencySuppliesMapper.selectByPrimaryKey(Id);
    }

    @Override
    public List<Map> selectSupplies(Integer suppliesType,String suppliesName, String vehPlate, String expertName,Integer status) {
        return tblEmergencySuppliesMapper.selectEmergencySupplies(suppliesType,suppliesName, vehPlate, expertName,status);
    }

    @Override
    public List<Map> selectEmergencyName(Integer suppliesType) {
        return tblEmergencySuppliesMapper.selectEmergencyName(suppliesType);
    }


    @Override
    public int insertEmergencySupplies(TblEmergencySupplies tblEmergencySupplies) {
        tblEmergencySupplies.setId(remoteIdProducerService.nextId());
        tblEmergencySupplies.setStatus(1);
        tblEmergencySupplies.setCreateTime(new Date());
        tblEmergencySupplies.setCreateUserId(SecurityUtils.getUserId());
        return tblEmergencySuppliesMapper.insert(tblEmergencySupplies);
    }

    @Override
    public int updateEmergencySupplies(TblEmergencySupplies tblEmergencySupplies) {
        tblEmergencySupplies.setUpdateTime(new Date());
        tblEmergencySupplies.setUpdateUserId(SecurityUtils.getUserId());
        return tblEmergencySuppliesMapper.updateByPrimaryKey(tblEmergencySupplies);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        TblEmergencySupplies tblEmergencySupplies= tblEmergencySuppliesMapper.selectByPrimaryKey(id);
        tblEmergencySupplies.setUpdateTime(new Date());
        tblEmergencySupplies.setUpdateUserId(SecurityUtils.getUserId());
        return tblEmergencySuppliesMapper.updateByPrimaryKey(tblEmergencySupplies);
    }

    @Override
    public String checkSuppliesNameUnique(TblEmergencySupplies tblEmergencySupplies) {
        Long id = StringUtils.isNull(tblEmergencySupplies.getId()) ? -1L : tblEmergencySupplies.getId();
        Long fieldBelong = StringUtils.isNull(tblEmergencySupplies.getFieldBelong()) ? 0L : tblEmergencySupplies.getFieldBelong();
        TblEmergencySupplies info = tblEmergencySuppliesMapper.checkSuppliesNameUnique(tblEmergencySupplies.getSuppliesName(),fieldBelong);
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkVehPlateUnique(TblEmergencySupplies tblEmergencySupplies) {
        Long id = StringUtils.isNull(tblEmergencySupplies.getId()) ? -1L : tblEmergencySupplies.getId();
        TblEmergencySupplies info = tblEmergencySuppliesMapper.checkVehPlateUnique(tblEmergencySupplies.getVehPlate(),tblEmergencySupplies.getVehColor());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkExpertNameUnique(TblEmergencySupplies tblEmergencySupplies) {
        Long id = StringUtils.isNull(tblEmergencySupplies.getId()) ? -1L : tblEmergencySupplies.getId();
        TblEmergencySupplies info = tblEmergencySuppliesMapper.checkExpertNameUnique(tblEmergencySupplies.getExpertName(),tblEmergencySupplies.getExpertPhone());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
