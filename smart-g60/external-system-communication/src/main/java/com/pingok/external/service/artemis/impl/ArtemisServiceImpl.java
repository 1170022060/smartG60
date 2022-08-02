package com.pingok.external.service.artemis.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.pingok.external.config.ArtemisLocalConfig;
import com.pingok.external.service.artemis.IArtemisService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteDeviceMonitorService;
import com.ruoyi.system.api.domain.device.TblDeviceInfo;
import com.ruoyi.system.api.domain.device.TblDeviceStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ArtemisServiceImpl implements IArtemisService {

    @Autowired
    private RemoteDeviceMonitorService remoteDeviceMonitorService;

    @Override
    public String httpRequest(String api, String body) {
        ArtemisConfig.host = ArtemisLocalConfig.HOST;
        ArtemisConfig.appKey = ArtemisLocalConfig.APPKEY;
        ArtemisConfig.appSecret = ArtemisLocalConfig.APPSECRET;
        String ARTEMIS_PATH = "/artemis";
        String previewURLsApi = ARTEMIS_PATH + api;
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", previewURLsApi);
            }
        };
        String contentType = "application/json";
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType, null);
        return result;
    }

    @Override
    public void updateStatus() {
        JSONObject body = new JSONObject();
        body.put("pageNo", 1);
        body.put("pageSize", 1000);
        String result = httpRequest("/api/resource/v1/cameras", body.toJSONString());
        if (result != null) {
            JSONObject resuleJ = JSONObject.parseObject(result);
            JSONArray list = resuleJ.getJSONObject("data").getJSONArray("list");
            JSONObject device;
            R<TblDeviceInfo> r;
            R r1;
            TblDeviceInfo deviceInfo;
            String cameraIndexCode;
            JSONObject deviceStatus = null;
            TblDeviceStatus tblDeviceStatus;
            if (!StringUtils.isNull(list) && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    device = list.getJSONObject(i);
                    cameraIndexCode = device.getString("cameraIndexCode");

                    body = new JSONObject();
                    body.put("cameraIndexCode", cameraIndexCode);
                    result = httpRequest("/api/resource/v1/cameras/indexCode", body.toJSONString());
                    if (result != null) {
                        deviceStatus = JSONObject.parseObject(result).getJSONObject("data");
                    }
                    r = remoteDeviceMonitorService.selectByDeviceId(cameraIndexCode);
                    if (!StringUtils.isNull(r) && r.getCode() == R.SUCCESS) {
                        deviceInfo = r.getData();
                        if (!StringUtils.isNull(deviceInfo)) {
                            tblDeviceStatus = new TblDeviceStatus();
                            tblDeviceStatus.setDeviceId(deviceInfo.getId());
                            tblDeviceStatus.setTime(DateUtils.getNowDate());
                            if(!StringUtils.isNull(deviceStatus)){
                                switch (deviceStatus.getInteger("status")){
                                    case 0:
                                        tblDeviceStatus.setStatus(0);
                                        tblDeviceStatus.setStatusDesc("状态未知");
                                        break;
                                    case 1:
                                        tblDeviceStatus.setStatus(1);
                                        tblDeviceStatus.setStatusDesc("正常");
                                        break;
                                    case 2:
                                        tblDeviceStatus.setStatus(2);
                                        tblDeviceStatus.setStatusDesc("离线");
                                        break;
                                }
                            }else{
                                tblDeviceStatus.setStatus(0);
                                tblDeviceStatus.setStatusDesc("状态未知");
                            }
                            r1 = remoteDeviceMonitorService.updateHeartbeat(tblDeviceStatus);
                            if (StringUtils.isNull(r1) || r1.getCode() == R.FAIL) {
                                log.error(device.getString("cameraIndexCode") + "设备状态更新失败");
                            }
                        } else {
                            log.error(device.getString("cameraIndexCode") + "设备编号未录入系统，请联系管理员处理");
                        }
                    } else {
                        log.error(device.getString("cameraIndexCode") + "设备编号未录入系统，请联系管理员处理");
                    }
                }
            }
        }
    }
}
