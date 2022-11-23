package com.pingok.vocational.service.software.impl;

import com.pingok.vocational.domain.software.TblSoftwareInfo;
import com.pingok.vocational.mapper.software.TblSoftwareInfoMapper;
import com.pingok.vocational.service.software.ISoftwareInfoService;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.utils.PinYinUtil;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 软件信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SoftwareInfoServiceImpl implements ISoftwareInfoService {

    @Autowired
    private TblSoftwareInfoMapper tblSoftwareInfoMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public TblSoftwareInfo selectSoftwareInfoById(Long Id) {
        return tblSoftwareInfoMapper.selectByPrimaryKey(Id);
    }

    @Override
    public List<Map> selectSoftwareInfo(String stationId, String name) {
        return tblSoftwareInfoMapper.selectSoftwareInfo(stationId,name);
    }

    @Override
    public int insertSoftwareInfo(TblSoftwareInfo tblSoftwareInfo) {
        tblSoftwareInfo.setId(remoteIdProducerService.nextId());
        tblSoftwareInfo.setCreateTime(new Date());
        tblSoftwareInfo.setCreateUserId(SecurityUtils.getUserId());
        return tblSoftwareInfoMapper.insert(tblSoftwareInfo);
    }

    @Override
    public int updateSoftwareInfo(TblSoftwareInfo tblSoftwareInfo) {
        tblSoftwareInfo.setUpdateTime(new Date());
        tblSoftwareInfo.setUpdateUserId(SecurityUtils.getUserId());
        return tblSoftwareInfoMapper.updateByPrimaryKey(tblSoftwareInfo);
    }

    @Override
    public String checkSoftNumUnique(TblSoftwareInfo tblSoftwareInfo) {
        Long id = StringUtils.isNull(tblSoftwareInfo.getId()) ? -1L : tblSoftwareInfo.getId();
        TblSoftwareInfo info = tblSoftwareInfoMapper.checkSoftNumUnique(tblSoftwareInfo.getNum());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
