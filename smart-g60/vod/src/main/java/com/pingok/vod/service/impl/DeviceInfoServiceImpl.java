package com.pingok.vod.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.vod.domain.DeviceDto;
import com.pingok.vod.domain.TblDeviceInfo;
import com.pingok.vod.mapper.TblDeviceInfoMapper;
import com.pingok.vod.service.IDeviceInfoService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 设备信息 服务层处理
 *
 * @author qiumin
 */
@Service
public class DeviceInfoServiceImpl implements IDeviceInfoService {
    @Autowired
    private TblDeviceInfoMapper tblDeviceInfoMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private MonitorPresetServiceImpl monitorPresetService;


    @Override
    public TblDeviceInfo findById(Long id) {
        return tblDeviceInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TblDeviceInfo> findAllByType() {
        Example example = new Example(TblDeviceInfo.class);
        example.orderBy("deviceId").asc();
        example.createCriteria().andEqualTo("deviceType", 10);
        return tblDeviceInfoMapper.selectByExample(example);
    }

    @Override
    public void add(TblDeviceInfo tblDeviceInfo) {
        tblDeviceInfo.setCreateTime(DateUtils.getNowDate());
        tblDeviceInfo.setCreateUserId(SecurityUtils.getUserId());
        tblDeviceInfo.setId(remoteIdProducerService.nextId());
        tblDeviceInfoMapper.insert(tblDeviceInfo);
    }

    @Override
    public void edit(TblDeviceInfo tblDeviceInfo) {
        tblDeviceInfo.setUpdateTime(DateUtils.getNowDate());
        tblDeviceInfo.setUpdateUserId(SecurityUtils.getUserId());
        tblDeviceInfoMapper.insert(tblDeviceInfo);
    }

    @Override
    public TblDeviceInfo findByDeviceId(String deviceId) {
        Example example = new Example(TblDeviceInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deviceId", deviceId);
        return tblDeviceInfoMapper.selectOneByExample(example);
    }

    @Override
    public void updateCameraList(JSONArray jsonArray) {
        Example example;
        JSONObject camera;
        TblDeviceInfo tblDeviceInfo;
        for (int i = 0; i < jsonArray.size(); i++) {
            camera = jsonArray.getJSONObject(i);
            example = new Example(TblDeviceInfo.class);
            example.createCriteria().andEqualTo("deviceId", camera.getString("id"));
            tblDeviceInfo = tblDeviceInfoMapper.selectOneByExample(example);
            if (tblDeviceInfo == null) {
                tblDeviceInfo = new TblDeviceInfo();
                tblDeviceInfo.setId(remoteIdProducerService.nextId());
                tblDeviceInfo.setCreateTime(DateUtils.getNowDate());
                tblDeviceInfo.setDeviceId(camera.getString("id"));
                tblDeviceInfo.setDeviceName(camera.getString("name"));
                tblDeviceInfo.setIsControl(camera.getInteger("ptz_able"));
                tblDeviceInfo.setDeviceType(10);
                tblDeviceInfoMapper.insert(tblDeviceInfo);
            } else {
                tblDeviceInfo.setUpdateTime(DateUtils.getNowDate());
                tblDeviceInfo.setDeviceName(camera.getString("name"));
                tblDeviceInfo.setIsControl(camera.getInteger("ptz_able"));
                tblDeviceInfoMapper.updateByPrimaryKey(tblDeviceInfo);
            }
        }
    }

    @Override
    public List<DeviceDto> getDeviceByRoadIds(List<Integer> roadIds) {
        return tblDeviceInfoMapper.getDeviceByRoadIds(roadIds);
    }



}
