package com.pingok.vocational.service.device.impl;

import com.pingok.vocational.domain.device.TblDeviceCategory;
import com.pingok.vocational.domain.device.TblGantryInfo;
import com.pingok.vocational.mapper.device.TblGantryInfoMapper;
import com.pingok.vocational.service.device.IGantryInfoService;
import com.pingok.vocational.service.device.TblDeviceCategoryService;
import com.pingok.vocational.service.project.IProjectInfoService;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 门架设备信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class GantryInfoServiceImpl implements IGantryInfoService {

    @Autowired
    private TblGantryInfoMapper tblGantryInfoMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private IProjectInfoService projectInfoService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private TblDeviceCategoryService tblDeviceCategoryService;


    @Override
    public List<TblGantryInfo> selectAll() {
        return tblGantryInfoMapper.selectAll();
    }

    @Override
    public TblGantryInfo selectDeviceInfoGantryById(Long Id) {
        return tblGantryInfoMapper.selectByPrimaryKey(Id);
    }

    @Override
    public List<Map> selectDeviceInfoGantry(Long deviceCategory, Integer status, Long fieldBelong, String deviceId, Long userSide, Long managementSide, Integer serviceLife, String deviceName) {

        return tblGantryInfoMapper.selectDeviceInfoGantry(deviceCategory, status, fieldBelong, deviceId, userSide, managementSide, serviceLife, deviceName);
    }

    @Override
    public int insertDeviceInfoGantry(TblGantryInfo tblDeviceInfoGantry) {
        tblDeviceInfoGantry.setId(remoteIdProducerService.nextId());
        if (tblDeviceInfoGantry.getItemName() != null) {
            tblDeviceInfoGantry.setItemListTime(projectInfoService.selectProjectInfoById(tblDeviceInfoGantry.getItemName()).getProjectTime());
        }
        tblDeviceInfoGantry.setStatus(1);
        tblDeviceInfoGantry.setCreateTime(new Date());
        tblDeviceInfoGantry.setCreateUserId(SecurityUtils.getUserId());
        int r = tblGantryInfoMapper.insert(tblDeviceInfoGantry);
        if (StringUtils.isNotNull(tblDeviceInfoGantry.getGps())) {
            String[] gps = tblDeviceInfoGantry.getGps().split(",");
            TblDeviceCategory deviceCategory = tblDeviceCategoryService.selectCategoryById(tblDeviceInfoGantry.getDeviceCategory());
            redisService.geoAdd("gantry",Double.valueOf(gps[0].substring(1)),Double.valueOf(gps[1].substring(0,gps[1].length()-1)),tblDeviceInfoGantry.getId());
        }
        return r;
    }

    @Override
    public int updateDeviceInfoGantry(TblGantryInfo tblDeviceInfoGantry) {
        if (tblDeviceInfoGantry.getItemName() != null) {
            tblDeviceInfoGantry.setItemListTime(projectInfoService.selectProjectInfoById(tblDeviceInfoGantry.getItemName()).getProjectTime());
        }
        tblDeviceInfoGantry.setUpdateTime(new Date());
        tblDeviceInfoGantry.setUpdateUserId(SecurityUtils.getUserId());
        int r = tblGantryInfoMapper.updateByPrimaryKey(tblDeviceInfoGantry);
        if (StringUtils.isNotNull(tblDeviceInfoGantry.getGps())) {
            String[] gps = tblDeviceInfoGantry.getGps().split(",");
            TblDeviceCategory deviceCategory = tblDeviceCategoryService.selectCategoryById(tblDeviceInfoGantry.getDeviceCategory());
            redisService.geoAdd("gantry",Double.valueOf(gps[0].substring(1)),Double.valueOf(gps[1].substring(0,gps[1].length()-1)),tblDeviceInfoGantry.getId());
        }
        return r;
    }

    @Override
    public String checkDeviceIdUnique(TblGantryInfo tblDeviceInfoGantry) {
        Long id = StringUtils.isNull(tblDeviceInfoGantry.getId()) ? -1L : tblDeviceInfoGantry.getId();
        TblGantryInfo info = tblGantryInfoMapper.checkDeviceIdUnique(tblDeviceInfoGantry.getDeviceId());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkDeviceNameUnique(TblGantryInfo tblDeviceInfoGantry) {
        Long id = StringUtils.isNull(tblDeviceInfoGantry.getId()) ? -1L : tblDeviceInfoGantry.getId();
        TblGantryInfo info = tblGantryInfoMapper.checkDeviceNameUnique(tblDeviceInfoGantry.getDeviceName());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public List<Map> selectGantryName() {
        return tblGantryInfoMapper.selectGantryName();
    }
}
