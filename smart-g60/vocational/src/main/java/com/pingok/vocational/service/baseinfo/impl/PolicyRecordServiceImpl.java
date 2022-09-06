package com.pingok.vocational.service.baseinfo.impl;

import com.pingok.vocational.domain.baseinfo.TblPolicyRecord;
import com.pingok.vocational.mapper.baseinfo.TblPolicyRecordMapper;
import com.pingok.vocational.service.baseinfo.IPolicyRecordService;
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
 * 收费政策记录 服务层处理
 *
 * @author ruoyi
 */
@Service
public class PolicyRecordServiceImpl implements IPolicyRecordService {

    @Autowired
    private TblPolicyRecordMapper tblPolicyRecordMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public TblPolicyRecord selectPolicyRecordById(Long Id) {
        return tblPolicyRecordMapper.selectByPrimaryKey(Id);
    }

    @Override
    public List<Map> selectPolicyRecord(String title,Integer status) {
        return tblPolicyRecordMapper.selectPolicyRecord(title,status);
    }

    @Override
    public int insertPolicyRecord(TblPolicyRecord tblPolicyRecord) {
        tblPolicyRecord.setId(remoteIdProducerService.nextId());
        tblPolicyRecord.setStatus(1);
        tblPolicyRecord.setCreateTime(new Date());
        tblPolicyRecord.setCreateUserId(SecurityUtils.getUserId());
        return tblPolicyRecordMapper.insert(tblPolicyRecord);
    }

    @Override
    public int updatePolicyRecord(TblPolicyRecord tblPolicyRecord) {
        tblPolicyRecord.setUpdateTime(new Date());
        tblPolicyRecord.setUpdateUserId(SecurityUtils.getUserId());
        return tblPolicyRecordMapper.updateByPrimaryKeySelective(tblPolicyRecord);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        TblPolicyRecord tblPolicyRecord= tblPolicyRecordMapper.selectByPrimaryKey(id);
        tblPolicyRecord.setUpdateTime(new Date());
        tblPolicyRecord.setUpdateUserId(SecurityUtils.getUserId());
        tblPolicyRecord.setStatus(status);
        return tblPolicyRecordMapper.updateByPrimaryKeySelective(tblPolicyRecord);
    }

    @Override
    public String checkTitleUnique(TblPolicyRecord tblPolicyRecord) {
        Long id = StringUtils.isNull(tblPolicyRecord.getId()) ? -1L : tblPolicyRecord.getId();
        TblPolicyRecord info = tblPolicyRecordMapper.checkTitleUnique(tblPolicyRecord.getTitle());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
