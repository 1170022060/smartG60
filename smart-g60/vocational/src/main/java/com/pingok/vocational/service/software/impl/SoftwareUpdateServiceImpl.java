package com.pingok.vocational.service.software.impl;

import com.pingok.vocational.domain.software.TblSoftwareUpdate;
import com.pingok.vocational.mapper.software.TblSoftwareUpdateMapper;
import com.pingok.vocational.service.software.ISoftwareUpdateService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 软件更新信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SoftwareUpdateServiceImpl implements ISoftwareUpdateService {

    @Autowired
    private TblSoftwareUpdateMapper tblSoftwareUpdateMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public TblSoftwareUpdate selectSoftwareUpdateById(Long Id) {
        return tblSoftwareUpdateMapper.selectByPrimaryKey(Id);
    }

    @Override
    public List<Map> selectSoftwareUpdate(String name, Integer softwareType, Integer status) {
        return tblSoftwareUpdateMapper.selectSoftwareUpdate(name, softwareType, status);
    }

    @Override
    public int insertSoftwareUpdate(TblSoftwareUpdate tblSoftwareUpdate) {
        tblSoftwareUpdate.setId(remoteIdProducerService.nextId());
        tblSoftwareUpdate.setStatus(1);
        tblSoftwareUpdate.setCreateTime(new Date());
        tblSoftwareUpdate.setCreateUserId(SecurityUtils.getUserId());
        return tblSoftwareUpdateMapper.insert(tblSoftwareUpdate);
    }

    @Override
    public int updateSoftwareUpdate(TblSoftwareUpdate tblSoftwareUpdate) {
        tblSoftwareUpdate.setUpdateTime(new Date());
        tblSoftwareUpdate.setUpdateUserId(SecurityUtils.getUserId());
        return tblSoftwareUpdateMapper.updateByPrimaryKeySelective(tblSoftwareUpdate);
    }

    @Override
    public int updatePublished(Long id) {
        TblSoftwareUpdate tblSoftwareUpdate= tblSoftwareUpdateMapper.selectByPrimaryKey(id);
        tblSoftwareUpdate.setUpdateTime(new Date());
        tblSoftwareUpdate.setUpdateUserId(SecurityUtils.getUserId());
        tblSoftwareUpdate.setStatus(2);
        return tblSoftwareUpdateMapper.updateByPrimaryKeySelective(tblSoftwareUpdate);
    }

    @Override
    public int updateDiscard(Long id) {
        TblSoftwareUpdate tblSoftwareUpdate= tblSoftwareUpdateMapper.selectByPrimaryKey(id);
        tblSoftwareUpdate.setUpdateTime(new Date());
        tblSoftwareUpdate.setUpdateUserId(SecurityUtils.getUserId());
        tblSoftwareUpdate.setStatus(0);
        return tblSoftwareUpdateMapper.updateByPrimaryKeySelective(tblSoftwareUpdate);
    }
}
