package com.pingok.external.service.bridge.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.domain.bridge.TblBridgeInfo;
import com.pingok.external.domain.bridge.TblBridgeTemperature;
import com.pingok.external.domain.bridge.TblBridgeTemperatureLog;
import com.pingok.external.mapper.bridge.TblBridgeTemperatureLogMapper;
import com.pingok.external.mapper.bridge.TblBridgeTemperatureMapper;
import com.pingok.external.service.bridge.IBridgeService;
import com.pingok.external.service.bridge.IBridgeTemperatureService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.system.api.RemoteConfigService;
import com.ruoyi.system.api.RemoteEventService;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.domain.event.TblEventRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;

@Slf4j
@Service
public class BridgeTemperatureServiceImpl implements IBridgeTemperatureService {

    @Value("${bridgeTemperature.host}")
    private String host;

    @Value("${bridgeTemperature.sessid}")
    private String sessid;


    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblBridgeTemperatureMapper tblBridgeTemperatureMapper;
    @Autowired
    private TblBridgeTemperatureLogMapper tblBridgeTemperatureLogMapper;
    @Autowired
    private RemoteConfigService remoteConfigService;
    @Autowired
    private RemoteEventService remoteEventService;
    @Autowired
    private IBridgeService iBridgeService;

    @Override
    public void getTemperature() {
        JSONObject param = new JSONObject();
        param.put("sessid", sessid);
        param.put("updatetm", DateUtils.getPreTime(DateUtils.getNowDate(), -5));
        String r = HttpUtil.post(host, param.toJSONString());
        if (!StringUtils.isEmpty(r)) {
            JSONObject ret = JSONObject.parseObject(r);
            if (ret.getBoolean("success")) {
                Example example = new Example(TblBridgeTemperature.class);
                JSONArray results = ret.getJSONArray("results");
                JSONObject object;
                TblBridgeTemperature bridgeTemperature;
                TblBridgeTemperatureLog bridgeTemperatureLog;
                R re;
                BigDecimal max = null;
                BigDecimal min = null;
                re = remoteConfigService.getConfigKey("bridge.temperature.max");
                if (re.getCode() == R.SUCCESS && re.getData() != null) {
                    max = new BigDecimal(re.getData().toString());
                }
                re = remoteConfigService.getConfigKey("bridge.temperature.min");
                if (re.getCode() == R.SUCCESS && re.getData() != null) {
                    min = new BigDecimal(re.getData().toString());
                }
                int size = results.size();
                for (int i = 0; i < size; i++) {
                    object = results.getJSONObject(i);
                    if (object.containsKey("data") && object.getJSONArray("data").size() > 0) {
                        example.clear();
                        example.createCriteria()
                                .andEqualTo("deviceId", object.getString("id"));
                        bridgeTemperature = tblBridgeTemperatureMapper.selectOneByExample(example);
                        if (bridgeTemperature == null) {
                            bridgeTemperature = new TblBridgeTemperature();
                            bridgeTemperature.setId(remoteIdProducerService.nextId());
                            bridgeTemperature.setCreateTime(DateUtils.getNowDate());
                            bridgeTemperature.setDeviceId(object.getString("id"));
                            bridgeTemperature.setTime(object.getDate("recv_tm"));
                            bridgeTemperature.setTemperature(object.getJSONArray("data").getJSONObject(0).getBigDecimal("data"));
                            tblBridgeTemperatureMapper.insert(bridgeTemperature);
                        } else {
                            bridgeTemperature.setTime(object.getDate("recv_tm"));
                            bridgeTemperature.setTemperature(object.getJSONArray("data").getJSONObject(0).getBigDecimal("data"));
                            bridgeTemperature.setUpdateTime(DateUtils.getNowDate());
                            tblBridgeTemperatureMapper.updateByPrimaryKey(bridgeTemperature);
                        }
                        bridgeTemperatureLog = new TblBridgeTemperatureLog();
                        BeanUtils.copyNotNullProperties(bridgeTemperature, bridgeTemperatureLog);
                        bridgeTemperatureLog.setId(remoteIdProducerService.nextId());
                        bridgeTemperatureLog.setCreateTime(DateUtils.getNowDate());
                        tblBridgeTemperatureLogMapper.insert(bridgeTemperatureLog);

                        if (max != null && bridgeTemperatureLog.getTemperature().compareTo(max) > -1) {
                            TblBridgeInfo  bridge = iBridgeService.selectBridgeInfoById(bridgeTemperature.getStructureId());
                            TblEventRecord eventRecord = new TblEventRecord();
                            eventRecord.setEventType("28");
                            eventRecord.setEventTime(bridgeTemperatureLog.getTime());
                            eventRecord.setStatus(0);
                            eventRecord.setLocationInterval(bridge.getLongitude() + "," + bridge.getLatitude());
                            eventRecord.setRemark(bridge.getName()+"桥面温度过高：" + bridgeTemperatureLog.getTemperature() + "℃");
                            re = remoteEventService.add(eventRecord);
                            if (re.getCode() == R.FAIL) {
                                log.error(bridge.getName()+"桥面温度过高事件推送失败：" + re.getMsg());
                            }
                        } else if (min != null && bridgeTemperatureLog.getTemperature().compareTo(min) < 1) {
                            TblBridgeInfo  bridge = iBridgeService.selectBridgeInfoById(bridgeTemperature.getStructureId());
                            TblEventRecord eventRecord = new TblEventRecord();
                            eventRecord.setEventType("29");
                            eventRecord.setEventTime(bridgeTemperatureLog.getTime());
                            eventRecord.setStatus(0);
                            eventRecord.setLocationInterval(bridge.getLongitude() + "," + bridge.getLatitude());
                            eventRecord.setRemark(bridge.getName()+"桥面温度过低：" + bridgeTemperatureLog.getTemperature() + "℃");
                            re = remoteEventService.add(eventRecord);
                            if (re.getCode() == R.FAIL) {
                                log.error(bridge.getName()+"桥面温度过高事件推送失败：" + re.getMsg());
                            }
                        }
                    }
                }
            } else {
                throw new ServiceException("桥面温度信息更新失败,错误：" + ret.getString("error"));
            }
        }
    }
}
