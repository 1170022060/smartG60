package com.pingok.vocational.service.device.impl;

import com.pingok.vocational.domain.device.TblDeviceCategory;
import com.pingok.vocational.domain.device.TblDeviceInfo;
import com.pingok.vocational.mapper.device.TblDeviceInfoMapper;
import com.pingok.vocational.service.device.TblDeviceCategoryService;
import com.pingok.vocational.service.device.TblDeviceInfoService;
import com.pingok.vocational.service.project.IProjectInfoService;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 设备信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class TblDeviceInfoServiceImpl implements TblDeviceInfoService {
    @Autowired
    private TblDeviceInfoMapper tblDeviceInfoMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private IProjectInfoService projectInfoService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private TblDeviceCategoryService tblDeviceCategoryService;

    @Override
    public List<Map> findBydeviceCategory(Long deviceCategory) {
        return tblDeviceInfoMapper.findBydeviceCategory(deviceCategory);
    }

    @Override
    public TblDeviceInfo selectDeviceInfoById(Long Id) {
        return tblDeviceInfoMapper.selectByPrimaryKey(Id);
    }

    @Override
    public List<Map> selectDeviceInfo(Long deviceCategory, Integer status, Long fieldBelong, String deviceId, Long userSide, Long managementSide, Integer serviceLife, String deviceName) {

        return tblDeviceInfoMapper.selectDeviceInfo( deviceCategory, status,  fieldBelong,  deviceId,  userSide,  managementSide,serviceLife,deviceName);
    }

    @Override
    public int insertDeviceInfo(TblDeviceInfo tblDeviceInfo) {
        tblDeviceInfo.setId(remoteIdProducerService.nextId());
        if(tblDeviceInfo.getItemName()!=null)
        {
            tblDeviceInfo.setItemListTime(projectInfoService.selectProjectInfoById(tblDeviceInfo.getItemName()).getProjectTime());
        }
        tblDeviceInfo.setStatus(1);
        tblDeviceInfo.setCreateTime(new Date());
        tblDeviceInfo.setCreateUserId(SecurityUtils.getUserId());
        int r = tblDeviceInfoMapper.insert(tblDeviceInfo);
        if (StringUtils.isNotNull(tblDeviceInfo.getGps())) {
            String[] gps = tblDeviceInfo.getGps().split(",");
            TblDeviceCategory deviceCategory = tblDeviceCategoryService.selectCategoryById(tblDeviceInfo.getDeviceCategory());
            redisService.geoAdd(deviceCategory.getCategoryNum(),Double.valueOf(gps[0]),Double.valueOf(gps[1]),tblDeviceInfo.getId());
        }
        return r;
    }

    @Override
    public int updateDeviceInfo(TblDeviceInfo tblDeviceInfo) {
        if(tblDeviceInfo.getItemName()!=null)
        {
            tblDeviceInfo.setItemListTime(projectInfoService.selectProjectInfoById(tblDeviceInfo.getItemName()).getProjectTime());
        }
        tblDeviceInfo.setUpdateTime(new Date());
        tblDeviceInfo.setUpdateUserId(SecurityUtils.getUserId());
        int r = tblDeviceInfoMapper.updateByPrimaryKeySelective(tblDeviceInfo);
        if (StringUtils.isNotNull(tblDeviceInfo.getGps())) {
            String[] gps = tblDeviceInfo.getGps().split(",");
            TblDeviceCategory deviceCategory = tblDeviceCategoryService.selectCategoryById(tblDeviceInfo.getDeviceCategory());
            redisService.geoAdd(deviceCategory.getCategoryNum(),Double.valueOf(gps[0]),Double.valueOf(gps[1]),tblDeviceInfo.getId());
        }
        return r;
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        TblDeviceInfo tblDeviceInfo= tblDeviceInfoMapper.selectByPrimaryKey(id);
        tblDeviceInfo.setStatus(status);
        return tblDeviceInfoMapper.updateByPrimaryKeySelective(tblDeviceInfo);
    }

    @Override
    public String checkDeviceIdUnique(TblDeviceInfo tblDeviceInfo) {
        Long id = StringUtils.isNull(tblDeviceInfo.getId()) ? -1L : tblDeviceInfo.getId();
        TblDeviceInfo info = tblDeviceInfoMapper.checkDeviceIdUnique(tblDeviceInfo.getDeviceId());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkDeviceNameUnique(TblDeviceInfo tblDeviceInfo) {
        Long id = StringUtils.isNull(tblDeviceInfo.getId()) ? -1L : tblDeviceInfo.getId();
        TblDeviceInfo info = tblDeviceInfoMapper.checkDeviceNameUnique(tblDeviceInfo.getDeviceName());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public List<Map> selectDeviceName() {
        return tblDeviceInfoMapper.selectDeviceName();
    }

    @Override
    public List<Map> selectInfoBoard(Long deviceCategory, String deviceName, String pileNo, String manufacturer, String deviceModel) {
        return tblDeviceInfoMapper.selectInfoBoard(deviceCategory, deviceName, pileNo, manufacturer, deviceModel);
    }
}
