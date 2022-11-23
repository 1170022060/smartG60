package com.pingok.vocational.service.baseinfo.impl;

import com.pingok.vocational.domain.baseinfo.TblBaseStationInfo;
import com.pingok.vocational.domain.baseinfo.vo.StationInfo;
import com.pingok.vocational.mapper.baseinfo.TblBaseStationInfoMapper;
import com.pingok.vocational.mapper.baseinfo.TblLaneInfoMapper;
import com.pingok.vocational.service.baseinfo.IBaseStationInfoService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 收费站基础信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class BaseStationInfoServiceImpl implements IBaseStationInfoService {

    @Autowired
    private TblBaseStationInfoMapper tblBaseStationInfoMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblLaneInfoMapper tblLaneInfoMapper;

    @Override
    public TblBaseStationInfo findByNetWorkAndStationId(String netWork, String stationId) {
        Example example = new Example(TblBaseStationInfo.class);
        example.createCriteria()
                .andEqualTo("netWork", netWork)
                .andEqualTo("stationId", stationId);
        return tblBaseStationInfoMapper.selectOneByExample(example);
    }

    @Override
    public TblBaseStationInfo selectBaseStationById(Long Id) {
        return tblBaseStationInfoMapper.selectByPrimaryKey(Id);
    }

    @Override
    public List<Map> selectBaseStation(String stationName) {
        return tblBaseStationInfoMapper.selectBaseStation(stationName);
    }

    @Override
    public int insertBaseStation(TblBaseStationInfo tblBaseStationInfo) {
        tblBaseStationInfo.setId(remoteIdProducerService.nextId());
        if (tblBaseStationInfo.getAdminStationId() == null) {
            tblBaseStationInfo.setAdminStationId(0L);
        }
        tblBaseStationInfo.setCreateTime(new Date());
        tblBaseStationInfo.setCreateUserId(SecurityUtils.getUserId());
        return tblBaseStationInfoMapper.insert(tblBaseStationInfo);
    }

    @Override
    public int updateBaseStation(TblBaseStationInfo tblBaseStationInfo) {
        tblBaseStationInfo.setUpdateTime(new Date());
        tblBaseStationInfo.setUpdateUserId(SecurityUtils.getUserId());
        return tblBaseStationInfoMapper.updateByPrimaryKey(tblBaseStationInfo);
    }

    @Override
    public List<Map> selectStationInfo() {
        return tblBaseStationInfoMapper.selectStationInfo();
    }

    @Override
    public List<Map> selectStationCenterInfo() {
        return tblBaseStationInfoMapper.selectStationCenterInfo();
    }

    @Override
    public List<StationInfo> selectStationLaneInfo() {
        List<StationInfo> lists = tblBaseStationInfoMapper.selectStationLaneInfo();
        for (StationInfo list : lists) {
            list.setLaneInfo(tblLaneInfoMapper.selectStationLaneInfo(list.getStationId()));
        }
        return lists;
    }
}
