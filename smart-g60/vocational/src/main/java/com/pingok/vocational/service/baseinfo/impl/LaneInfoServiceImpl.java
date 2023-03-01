package com.pingok.vocational.service.baseinfo.impl;

import com.pingok.vocational.domain.baseinfo.TblLaneInfo;
import com.pingok.vocational.mapper.baseinfo.TblLaneInfoMapper;
import com.pingok.vocational.service.baseinfo.ILaneInfoService;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 车道基础信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class LaneInfoServiceImpl implements ILaneInfoService {

    @Autowired
    private TblLaneInfoMapper tblLaneInfoMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public TblLaneInfo findByLaneHex(String laneHex) {
        Example example = new Example(TblLaneInfo.class);
        example.createCriteria().andEqualTo("laneHex", laneHex);
        return tblLaneInfoMapper.selectOneByExample(example);
    }

    @Override
    public TblLaneInfo selectLaneById(Long Id) {
        return tblLaneInfoMapper.selectByPrimaryKey(Id);
    }

    @Override
    public List<Map> selectLane(String stationId, Integer status) {
        return tblLaneInfoMapper.selectLane(stationId, status);
    }

    @Override
    public int insertLane(TblLaneInfo tblLaneInfo) {
        tblLaneInfo.setId(remoteIdProducerService.nextId());
        tblLaneInfo.setStatus(1);
        tblLaneInfo.setCreateTime(new Date());
        tblLaneInfo.setCreateUserId(SecurityUtils.getUserId());
        return tblLaneInfoMapper.insert(tblLaneInfo);
    }

    @Override
    public int updateLane(TblLaneInfo tblLaneInfo) {
        tblLaneInfo.setUpdateTime(new Date());
        tblLaneInfo.setUpdateUserId(SecurityUtils.getUserId());
        return tblLaneInfoMapper.updateByPrimaryKey(tblLaneInfo);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        TblLaneInfo tblLaneInfo = tblLaneInfoMapper.selectByPrimaryKey(id);
        tblLaneInfo.setUpdateTime(new Date());
        tblLaneInfo.setUpdateUserId(SecurityUtils.getUserId());
        tblLaneInfo.setStatus(status);
        return tblLaneInfoMapper.updateByPrimaryKey(tblLaneInfo);
    }

    @Override
    public String checkLaneNameUnique(TblLaneInfo tblLaneInfo) {
        Long id = StringUtils.isNull(tblLaneInfo.getId()) ? -1L : tblLaneInfo.getId();
        TblLaneInfo info = tblLaneInfoMapper.checkLaneNameUnique(tblLaneInfo.getLaneName());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public List<Map> selectLaneInfo() {
        return tblLaneInfoMapper.selectLaneInfo();
    }
}
