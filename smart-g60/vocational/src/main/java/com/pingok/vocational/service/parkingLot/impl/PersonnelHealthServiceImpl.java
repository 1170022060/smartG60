package com.pingok.vocational.service.parkingLot.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.vocational.domain.parkingLot.TblPersonnelHealth;
import com.pingok.vocational.mapper.parkingLot.TblPersonnelHealthMapper;
import com.pingok.vocational.service.parkingLot.IPersonnelHealthService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 人员健康信息 服务层处理
 *
 * @author ruoyi
 */
@Service
public class PersonnelHealthServiceImpl implements IPersonnelHealthService {

    @Autowired
    private TblPersonnelHealthMapper tblPersonnelHealthMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public TblPersonnelHealth selectPersonnelHealthById(Long Id) {
        return tblPersonnelHealthMapper.selectByPrimaryKey(Id);
    }

    @Override
    public List<Map> selectPersonnelHealth(Long serviceId, Long fieldId, Date date) {
        return tblPersonnelHealthMapper.selectPersonnelHealth(serviceId,fieldId,date);
    }

    @Override
    public List<Map> selectHealthStatistics(Integer type, Long fieldId, Date date) {
        return tblPersonnelHealthMapper.selectHealthStatistics(type,fieldId,date);
    }

    @Override
    public List<Map> selectHealthMonitor(Date date) {
        return tblPersonnelHealthMapper.selectHealthMonitor(date);
    }

    @Override
    public AjaxResult insertPersonnelHealth(JSONObject personnelHealth) {
        TblPersonnelHealth tblPersonnelHealth;
        JSONArray arr=personnelHealth.getJSONArray("healthInfo");
        JSONObject obj;
        for (int i=0;i<arr.size();i++){
            obj=arr.getJSONObject(i);
            tblPersonnelHealth = new TblPersonnelHealth();
            tblPersonnelHealth.setId(remoteIdProducerService.nextId());
            tblPersonnelHealth.setServiceId(personnelHealth.getLong("serviceId"));
            tblPersonnelHealth.setTransDate(personnelHealth.getDate("transDate"));
            tblPersonnelHealth.setFieldId(obj.getLong("fieldId"));
            tblPersonnelHealth.setAbnormalNum(obj.getInteger("normalNum"));
            tblPersonnelHealth.setNormalNum(obj.getInteger("abnormalNum"));
            tblPersonnelHealth.setCreateTime(new Date());
            tblPersonnelHealth.setCreateUserId(SecurityUtils.getUserId());
            tblPersonnelHealthMapper.insert(tblPersonnelHealth);
        }
        return null;
    }

    @Override
    public int updatePersonnelHealth(TblPersonnelHealth tblPersonnelHealth) {
        tblPersonnelHealth.setUpdateTime(new Date());
        tblPersonnelHealth.setUpdateUserId(SecurityUtils.getUserId());
        return tblPersonnelHealthMapper.updateByPrimaryKeySelective(tblPersonnelHealth);
    }
}
