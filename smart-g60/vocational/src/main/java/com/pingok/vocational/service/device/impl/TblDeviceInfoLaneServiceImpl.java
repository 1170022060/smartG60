package com.pingok.vocational.service.device.impl;

import com.pingok.vocational.domain.device.TblDeviceInfoLane;
import com.pingok.vocational.domain.device.vo.LaneEnum;
import com.pingok.vocational.mapper.device.TblDeviceInfoLaneMapper;
import com.pingok.vocational.service.device.TblDeviceInfoLaneService;
import com.pingok.vocational.service.project.IProjectInfoService;
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
 * 车道设备信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class TblDeviceInfoLaneServiceImpl implements TblDeviceInfoLaneService {

    @Autowired
    private TblDeviceInfoLaneMapper tblDeviceInfoLaneMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private IProjectInfoService projectInfoService;

    @Override
    public TblDeviceInfoLane selectDeviceInfoLaneById(Long Id) {
        return tblDeviceInfoLaneMapper.selectByPrimaryKey(Id);
    }

    @Override
    public List<Map> selectDeviceInfoLane(String laneHex,Long deviceCategory, Integer status, String laneBelong, String deviceId, Long userSide, Long managementSide, Integer serviceLife, String deviceName) {

        return tblDeviceInfoLaneMapper.selectDeviceInfoLane(laneHex, deviceCategory, status,  laneBelong,  deviceId,  userSide,  managementSide,serviceLife,deviceName);
    }

    @Override
    public int insertDeviceInfoLane(LaneEnum laneEnum) {
        laneEnum.getTblDeviceInfoLane().setId(remoteIdProducerService.nextId());
        if(laneEnum.getTblDeviceInfoLane().getItemName()!=null)
        {
            laneEnum.getTblDeviceInfoLane().setItemListTime(projectInfoService.selectProjectInfoById(laneEnum.getTblDeviceInfoLane().getItemName()).getProjectTime());
        }
        laneEnum.getTblDeviceInfoLane().setStationBelong(laneEnum.getLaneHex().substring(4,8));
        laneEnum.getTblDeviceInfoLane().setLaneBelong(laneEnum.getLaneHex());
        laneEnum.getTblDeviceInfoLane().setStatus(1);
        laneEnum.getTblDeviceInfoLane().setCreateTime(new Date());
        laneEnum.getTblDeviceInfoLane().setCreateUserId(SecurityUtils.getUserId());
        return tblDeviceInfoLaneMapper.insert(laneEnum.getTblDeviceInfoLane());
    }

    @Override
    public int updateDeviceInfoLane(TblDeviceInfoLane tblDeviceInfoLane) {
        if(tblDeviceInfoLane.getItemName()!=null)
        {
            tblDeviceInfoLane.setItemListTime(projectInfoService.selectProjectInfoById(tblDeviceInfoLane.getItemName()).getProjectTime());
        }
        tblDeviceInfoLane.setUpdateTime(new Date());
        tblDeviceInfoLane.setUpdateUserId(SecurityUtils.getUserId());
        return tblDeviceInfoLaneMapper.updateByPrimaryKey(tblDeviceInfoLane);
    }
    @Override
    public int updateStatus(Long id, Integer status) {
        TblDeviceInfoLane tblDeviceInfoLane= tblDeviceInfoLaneMapper.selectByPrimaryKey(id);
        tblDeviceInfoLane.setUpdateTime(new Date());
        tblDeviceInfoLane.setUpdateUserId(SecurityUtils.getUserId());
        tblDeviceInfoLane.setStatus(status);
        return tblDeviceInfoLaneMapper.updateByPrimaryKey(tblDeviceInfoLane);
    }

    @Override
    public String checkDeviceIdUnique(TblDeviceInfoLane tblDeviceInfoLane) {
        Long id = StringUtils.isNull(tblDeviceInfoLane.getId()) ? -1L : tblDeviceInfoLane.getId();
        TblDeviceInfoLane info = tblDeviceInfoLaneMapper.checkDeviceIdUnique(tblDeviceInfoLane.getDeviceId());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public String checkDeviceNameUnique(TblDeviceInfoLane tblDeviceInfoLane) {
        Long id = StringUtils.isNull(tblDeviceInfoLane.getId()) ? -1L : tblDeviceInfoLane.getId();
        TblDeviceInfoLane info = tblDeviceInfoLaneMapper.checkDeviceNameUnique(tblDeviceInfoLane.getDeviceName());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
