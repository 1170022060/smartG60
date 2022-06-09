package com.pingok.vocational.service.release.Impl;

import com.pingok.vocational.domain.release.TblReleasePreset;
import com.pingok.vocational.mapper.release.TblReleasePresetMapper;
import com.pingok.vocational.service.release.IReleasePresetService;
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
 * 信息发布预设 服务层处理
 *
 * @author ruoyi
 */
@Service
public class ReleasePresetServiceImpl implements IReleasePresetService {

    @Autowired
    private TblReleasePresetMapper tblReleasePresetMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public TblReleasePreset selectReleasePresetById(Long Id) {
        return tblReleasePresetMapper.selectByPrimaryKey(Id);
    }

    @Override
    public List<Map> selectReleasePreset(Integer infoType,Integer status) {
        return tblReleasePresetMapper.selectReleasePreset(infoType, status);
    }

    @Override
    public List<Map> selectDevice(Long id) {

        TblReleasePreset tblReleasePreset=tblReleasePresetMapper.selectByPrimaryKey(id);
        int length=tblReleasePreset.getPresetInfo().length();
        if(length<=2)
        {
            return tblReleasePresetMapper.selectDeviceSpeed();
        }
        else{
            return tblReleasePresetMapper.selectDeviceInfo();
        }
    }

    @Override
    public List<Map> selectDeviceBoard(Integer type) {
        return tblReleasePresetMapper.selectDeviceBoard(type);
    }

    @Override
    public int insertReleasePreset(TblReleasePreset tblReleasePreset) {
        tblReleasePreset.setId(remoteIdProducerService.nextId());
        tblReleasePreset.setStatus(1);
        tblReleasePreset.setCreateTime(new Date());
        tblReleasePreset.setCreateUserId(SecurityUtils.getUserId());
        return tblReleasePresetMapper.insert(tblReleasePreset);
    }

    @Override
    public int updateReleasePreset(TblReleasePreset tblReleasePreset) {
        tblReleasePreset.setUpdateTime(new Date());
        tblReleasePreset.setUpdateUserId(SecurityUtils.getUserId());
        return tblReleasePresetMapper.updateByPrimaryKeySelective(tblReleasePreset);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        TblReleasePreset tblReleasePreset= tblReleasePresetMapper.selectByPrimaryKey(id);
        tblReleasePreset.setStatus(status);
        return tblReleasePresetMapper.updateByPrimaryKeySelective(tblReleasePreset);
    }

    @Override
    public String checkPresetInfoUnique(TblReleasePreset tblReleasePreset) {
        Long id = StringUtils.isNull(tblReleasePreset.getId()) ? -1L : tblReleasePreset.getId();
        TblReleasePreset info = tblReleasePresetMapper.checkPresetInfoUnique(tblReleasePreset.getPresetInfo());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public List<Map> selectReleaseInfo(Integer infoType) {
        return tblReleasePresetMapper.selectReleaseInfo(infoType);
    }
}
