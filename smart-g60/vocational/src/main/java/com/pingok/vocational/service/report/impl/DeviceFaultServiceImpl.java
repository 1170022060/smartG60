package com.pingok.vocational.service.report.impl;

import com.alibaba.fastjson.JSON;
import com.pingok.vocational.domain.report.TblDeviceFault;
import com.pingok.vocational.domain.report.vo.DeviceFaultSearch;
import com.pingok.vocational.domain.report.vo.DeviceFaultTypeVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.mapper.report.TblDeviceFaultMapper;
import com.pingok.vocational.service.report.IDeviceFaultService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 设备故障记录表 服务层处理
 *
 * @author ruoyi
 */
@Service
public class DeviceFaultServiceImpl implements IDeviceFaultService {

    @Autowired
    private TblDeviceFaultMapper tblDeviceFaultMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public List<Map> faultStatistics() {
        return tblDeviceFaultMapper.faultStatistics();
    }

    @Override
    public void add(TblDeviceFault tblDeviceFault) {
        tblDeviceFault.setId(remoteIdProducerService.nextId());
        tblDeviceFault.setStatus(1);
        tblDeviceFault.setCreateTime(DateUtils.getNowDate());
        tblDeviceFault.setCreateUserId(SecurityUtils.getUserId());
        tblDeviceFault.setRegisterType(2);
        tblDeviceFault.setFaultTime(DateUtils.getNowDate());
        tblDeviceFault.setFaultPhoto(JSON.toJSONString(tblDeviceFault.getFaultPhotoStr()));
        tblDeviceFault.setFaultVideo(JSON.toJSONString(tblDeviceFault.getFaultVideoStr()));
        tblDeviceFaultMapper.insert(tblDeviceFault);
    }

    @Override
    public TblDeviceFault findById(Long id) {
        TblDeviceFault tblDeviceFault = tblDeviceFaultMapper.selectByPrimaryKey(id);
        tblDeviceFault.setFaultPhotoStr(JSON.parseObject(tblDeviceFault.getFaultPhoto(), String[].class));
        tblDeviceFault.setFaultVideoStr(JSON.parseObject(tblDeviceFault.getFaultVideo(), String[].class));
        return tblDeviceFault;
    }

    @Override
    public void relieve(Long id, String remark) {
        TblDeviceFault tblDeviceFault = tblDeviceFaultMapper.selectByPrimaryKey(id);
        if (tblDeviceFault == null) {
            throw new ServiceException("设备id不存在");
        }
        tblDeviceFault.setRemark(remark);
        tblDeviceFault.setUpdateTime(DateUtils.getNowDate());
        tblDeviceFault.setUpdateUserId(SecurityUtils.getUserId());
        tblDeviceFault.setStatus(-1);
        tblDeviceFaultMapper.updateByPrimaryKey(tblDeviceFault);
    }

    @Override
    public void confirm(Long id, String remark) {
        TblDeviceFault tblDeviceFault = tblDeviceFaultMapper.selectByPrimaryKey(id);
        if (tblDeviceFault == null) {
            throw new ServiceException("设备id不存在");
        }
        tblDeviceFault.setRemark(remark);
        tblDeviceFault.setUpdateTime(DateUtils.getNowDate());
        tblDeviceFault.setUpdateUserId(SecurityUtils.getUserId());
        tblDeviceFault.setStatus(1);
        tblDeviceFaultMapper.updateByPrimaryKey(tblDeviceFault);
    }

    @Override
    public List<DeviceFaultSearch> search(String faultType, Long deviceId, String faultId, String faultDescription, Integer status) {
        return tblDeviceFaultMapper.search(faultType, deviceId, faultId, faultDescription, status);
    }

    @Override
    public List<Map> selectDeviceFaultByType(String faultType, Date startTime, Date endTime) {
        return tblDeviceFaultMapper.selectDeviceFaultByType(faultType, startTime, endTime);
    }

    @Override
    public List<DeviceFaultTypeVo> selectDeviceFaultByTypeList(ReportVo reportVo) {
        return tblDeviceFaultMapper.selectDeviceFaultByTypeList(reportVo);
    }

}
