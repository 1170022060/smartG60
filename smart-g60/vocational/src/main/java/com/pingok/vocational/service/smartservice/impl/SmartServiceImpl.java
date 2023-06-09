package com.pingok.vocational.service.smartservice.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.vocational.domain.device.TblDeviceRepair;
import com.pingok.vocational.domain.report.TblDeviceFault;
import com.pingok.vocational.mapper.device.TblDeviceRepairMapper;
import com.pingok.vocational.mapper.device.TblDeviceStatusMapper;
import com.pingok.vocational.mapper.report.TblDeviceFaultMapper;
import com.pingok.vocational.service.smartservice.ISmartService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.DictUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.domain.SysDictData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 服务区 服务层处理
 *
 * @author ruoyi
 */
@Service
public class SmartServiceImpl implements ISmartService {

    @Autowired
    private TblDeviceStatusMapper tblDeviceStatusMapper;
    @Autowired
    private TblDeviceFaultMapper tblDeviceFaultMapper;
    @Autowired
    private TblDeviceRepairMapper tblDeviceRepairMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;


    @Override
    public List<Map> serviceDevice(String fieldNum, Integer deviceType, Integer status) {
        List<Map> maps = tblDeviceStatusMapper.serviceDevice(fieldNum, deviceType, status);
        for (Map m : maps) {
            if (m.get("statusDetails") != null) {
                m.put("statusDetails", JSONObject.parseObject(String.valueOf(m.get("statusDetails"))));
            }
        }
        return maps;
    }

    @Override
    public int insertServiceFault(TblDeviceFault tblDeviceFault) {
        tblDeviceFault.setId(remoteIdProducerService.nextId());
        tblDeviceFault.setRegisterType(1);
        tblDeviceFault.setStatus(1);
        tblDeviceFault.setCreateTime(DateUtils.getNowDate());
        tblDeviceFault.setCreateUserId(SecurityUtils.getUserId());
        tblDeviceFault.setRegisterType(2);
        tblDeviceFault.setFaultTime(DateUtils.getNowDate());
        return tblDeviceFaultMapper.insert(tblDeviceFault);
    }

    @Override
    public List<Map> serviceDeviceStatus(String fieldNum, Integer deviceType) {
        return tblDeviceStatusMapper.serviceDeviceStatus(fieldNum, deviceType);
    }

    @Override
    public List<Map> serviceDeviceFault(String fieldNum, Integer deviceType,String deviceId, String faultId, String faultDescription, Integer status) {
        return tblDeviceStatusMapper.serviceDeviceFault(fieldNum,  deviceType, deviceId,  faultId,  faultDescription, status);
    }

    @Override
    public int insertServiceRepair(TblDeviceRepair tblDeviceRepair) {
        tblDeviceRepair.setId(remoteIdProducerService.nextId());
        tblDeviceRepair.setResponsibleUserId(SecurityUtils.getUserId());
        return tblDeviceRepairMapper.insert(tblDeviceRepair);
    }

    @Override
    public JSONArray getDeviceStatusDesc() {
        List<Map> descList = tblDeviceStatusMapper.getDeviceStatusDesc();
        Map group = descList.stream().collect(Collectors.groupingBy(t -> t.get("deviceType")));
        //字典
        List<SysDictData> sysDictDataList = DictUtils.getDictCache("device_type");
        List<BigDecimal> keyList = new ArrayList<>(group.keySet());
        JSONArray ret = new JSONArray();
        BigDecimal normal = new BigDecimal(0);
        for(BigDecimal key: keyList) {
            JSONObject jo = new JSONObject();
            jo.put("deviceType", key);
            for (SysDictData d : sysDictDataList) {
                if (key.intValue() == Integer.parseInt(d.getDictValue())) {
                    jo.put("deviceCategory", d.getDictLabel());
                    break;
                }
            }
            List<Map> valList = (List<Map>)group.get(key);
            long nFault = valList.stream().filter(x -> x.get("deviceStatus").equals(normal)).count();
            jo.put("statusDesc", nFault > 0 ? "异常" : "正常");
            ret.add(jo);
        }

        return ret;
    }
}
