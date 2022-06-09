package com.pingok.monitor.service.deviceInfo.impl;

import com.pingok.monitor.domain.deviceInfo.ViewMonitorDeviceInfo;
import com.pingok.monitor.mapper.deviceInfo.ViewMonitorDeviceInfoMapper;
import com.pingok.monitor.service.deviceInfo.IDeviceInfoService;
import com.ruoyi.common.security.utils.DictUtils;
import com.ruoyi.system.api.domain.SysDictData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 设备基础信息 服务层处理
 *
 * @author qiumin
 */
@Service
public class DeviceInfoServiceImpl implements IDeviceInfoService {

    @Autowired
    private ViewMonitorDeviceInfoMapper viewMonitorDeviceInfoMapper;

    @Override
    public List<ViewMonitorDeviceInfo> getInfo() {
        List<ViewMonitorDeviceInfo> monitorDeviceInfos = null;
        List<SysDictData> sysDictDataList = DictUtils.getDictCache("monitoring_screen");
        if (!sysDictDataList.isEmpty() && sysDictDataList.size() > 0) {
            List<String> categoryNames = new ArrayList();
            for (SysDictData s : sysDictDataList) {
                categoryNames.add(s.getDictValue());
            }
            Example example = new Example(ViewMonitorDeviceInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andIn("categoryName", categoryNames);
            monitorDeviceInfos = viewMonitorDeviceInfoMapper.selectByExample(example);
        }
        return monitorDeviceInfos;
    }

    @Override
    public List<Map> findByFieldNum(String fieldNum) {
        List<Map> map = viewMonitorDeviceInfoMapper.findByFieldNum(fieldNum);
        for (Map m : map) {
            m.put("faultList",viewMonitorDeviceInfoMapper.findFaultByDeviceId(Long.valueOf(String.valueOf(m.get("id")))));
        }
        return map;
    }
}
