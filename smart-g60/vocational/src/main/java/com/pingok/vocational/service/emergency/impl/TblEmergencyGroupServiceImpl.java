package com.pingok.vocational.service.emergency.impl;

import com.alibaba.fastjson.JSON;
import com.pingok.vocational.domain.emergency.TblEmergencyGroup;
import com.pingok.vocational.mapper.emergency.TblEmergencyGroupMapper;
import com.pingok.vocational.service.emergency.TblEmergencyGroupService;
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
 * 应急小组信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class TblEmergencyGroupServiceImpl implements TblEmergencyGroupService {
    @Autowired
    private TblEmergencyGroupMapper tblEmergencyGroupMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public TblEmergencyGroup selectEmergencyGroupById(Long Id) {
        TblEmergencyGroup tblEmergencyGroup=tblEmergencyGroupMapper.selectByPrimaryKey(Id);
        tblEmergencyGroup.setGroupLeaderDepStr(JSON.parseObject(tblEmergencyGroup.getGroupLeaderDep(), Long[].class));
        tblEmergencyGroup.setGroupMembersStr(JSON.parseObject(tblEmergencyGroup.getGroupMembers(), Long[].class));
        return tblEmergencyGroup;
    }

    @Override
    public List<Map> selectEmergencyGroup(String groupName, Long groupLeader,Integer status) {
        return tblEmergencyGroupMapper.selectEmergencyGroup(groupName, groupLeader,status);
    }

    @Override
    public int insertEmergencyGroup(TblEmergencyGroup tblEmergencyGroup) {

        tblEmergencyGroup.setGroupLeaderDep(JSON.toJSONString(tblEmergencyGroup.getGroupLeaderDepStr()));
        tblEmergencyGroup.setGroupMembers(JSON.toJSONString(tblEmergencyGroup.getGroupMembersStr()));
        tblEmergencyGroup.setId(remoteIdProducerService.nextId());
        tblEmergencyGroup.setStatus(1);
        tblEmergencyGroup.setCreateTime(new Date());
        tblEmergencyGroup.setCreateUserId(SecurityUtils.getUserId());
        return tblEmergencyGroupMapper.insert(tblEmergencyGroup);
    }

    @Override
    public int updateEmergencyGroup(TblEmergencyGroup tblEmergencyGroup) {

        tblEmergencyGroup.setGroupLeaderDep(JSON.toJSONString(tblEmergencyGroup.getGroupLeaderDepStr()));
        tblEmergencyGroup.setGroupMembers(JSON.toJSONString(tblEmergencyGroup.getGroupMembersStr()));
        tblEmergencyGroup.setUpdateTime(new Date());
        tblEmergencyGroup.setUpdateUserId(SecurityUtils.getUserId());
        return tblEmergencyGroupMapper.updateByPrimaryKeySelective(tblEmergencyGroup);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        TblEmergencyGroup tblEmergencyGroup= tblEmergencyGroupMapper.selectByPrimaryKey(id);
        tblEmergencyGroup.setUpdateTime(new Date());
        tblEmergencyGroup.setUpdateUserId(SecurityUtils.getUserId());
        tblEmergencyGroup.setStatus(status);
        return tblEmergencyGroupMapper.updateByPrimaryKeySelective(tblEmergencyGroup);
    }

    @Override
    public String checkGroupNameUnique(TblEmergencyGroup tblEmergencyGroup) {

        Long id = StringUtils.isNull(tblEmergencyGroup.getId()) ? -1L : tblEmergencyGroup.getId();
        TblEmergencyGroup info = tblEmergencyGroupMapper.checkGroupNameUnique(tblEmergencyGroup.getGroupName());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public List<Map> selectDeptUser() {
        return tblEmergencyGroupMapper.selectDeptUser();
    }
}
