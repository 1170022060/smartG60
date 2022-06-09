package com.pingok.vod.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import java.util.Map;

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


    @Override
    public TblDeviceInfo findById(Long id) {
        return tblDeviceInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map> findAllByType(Integer type) {
        List<Map> list = null;
        switch (type) {
            case 1:
                list = tblDeviceInfoMapper.findAllCamera();
                break;
            case 2:
                list = tblDeviceInfoMapper.findAllCameraByUserId(SecurityUtils.getUserId());
                break;
        }
        return list;
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
        Example example = new Example(TblDeviceInfo.class);
        Example.Criteria criteria = example.createCriteria();
        JSONObject camera;
        TblDeviceInfo tblDeviceInfo;
        for (int i = 0; i < jsonArray.size(); i++) {
            camera = jsonArray.getJSONObject(i);
            criteria.andEqualTo("deviceId", camera.getString("id"));
            tblDeviceInfo = tblDeviceInfoMapper.selectOneByExample(example);
            if (tblDeviceInfo == null) {
                tblDeviceInfo = new TblDeviceInfo();
                tblDeviceInfo.setId(remoteIdProducerService.nextId());
                tblDeviceInfo.setCreateTime(DateUtils.getNowDate());
                tblDeviceInfo.setDeviceName(camera.getString("name"));
                tblDeviceInfo.setIsControl(camera.getInteger("ptz_able"));
                tblDeviceInfoMapper.insert(tblDeviceInfo);
            } else {
                tblDeviceInfo.setUpdateTime(DateUtils.getNowDate());
                tblDeviceInfo.setDeviceName(camera.getString("name"));
                tblDeviceInfo.setIsControl(camera.getInteger("ptz_able"));
                tblDeviceInfoMapper.updateByPrimaryKey(tblDeviceInfo);
            }
        }
    }
}
