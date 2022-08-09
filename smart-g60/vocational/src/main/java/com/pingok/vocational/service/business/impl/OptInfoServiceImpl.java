package com.pingok.vocational.service.business.impl;

import com.pingok.vocational.domain.business.TblOptInfo;
import com.pingok.vocational.mapper.business.TblOptInfoMapper;
import com.pingok.vocational.service.business.IOptInfoService;
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
 * 员工信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class OptInfoServiceImpl implements IOptInfoService {

    @Autowired
    private TblOptInfoMapper tblOptInfoMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public TblOptInfo selectOptInfoById(Long Id) {
        return tblOptInfoMapper.selectByPrimaryKey(Id);
    }

    @Override
    public List<Map> selectOptInfo(String belongStation, String belongCenter, String optName, Integer optId, Integer status) {
        return tblOptInfoMapper.selectOptInfo(belongStation, belongCenter, optName, optId, status);
    }

    @Override
    public int insertOptInfo(TblOptInfo tblOptInfo) {
        tblOptInfo.setId(remoteIdProducerService.nextId());
        tblOptInfo.setStatus(1);
        tblOptInfo.setCreateTime(new Date());
        tblOptInfo.setCreateUserId(SecurityUtils.getUserId());
        return tblOptInfoMapper.insert(tblOptInfo);
    }

    @Override
    public int updateOptInfo(TblOptInfo tblOptInfo) {
        tblOptInfo.setUpdateTime(new Date());
        tblOptInfo.setUpdateUserId(SecurityUtils.getUserId());
        return tblOptInfoMapper.updateByPrimaryKeySelective(tblOptInfo);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        TblOptInfo tblOptInfo= tblOptInfoMapper.selectByPrimaryKey(id);
        tblOptInfo.setUpdateTime(new Date());
        tblOptInfo.setUpdateUserId(SecurityUtils.getUserId());
        tblOptInfo.setStatus(status);
        return tblOptInfoMapper.updateByPrimaryKeySelective(tblOptInfo);
    }

    @Override
    public String checkOptIdUnique(TblOptInfo tblOptInfo) {
        Long id = StringUtils.isNull(tblOptInfo.getId()) ? -1L : tblOptInfo.getId();
        TblOptInfo info = tblOptInfoMapper.checkOptIdUnique(tblOptInfo.getOptId());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
