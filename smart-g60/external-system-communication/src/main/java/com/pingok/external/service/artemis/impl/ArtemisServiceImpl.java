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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ArtemisServiceImpl implements IArtemisService {

    @Autowired
    private RemoteDeviceMonitorService remoteDeviceMonitorService;

    @Override
    public String httpRequest(String api, String body) {
        ArtemisConfig ArtemisConfig = new ArtemisConfig();
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
        String result = null;
        try {
            result = ArtemisHttpUtil.doPostStringArtemis(ArtemisConfig,path, body, null, null, contentType, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    @Override
    public void updateStatus() {
        JSONObject body = new JSONObject();
        body.put("pageNo", 1);
        body.put("pageSize", 1000);
        String result = httpRequest("/api/resource/v2/camera/search", body.toJSONString());
        if (result != null) {
            JSONObject resuleJ = JSONObject.parseObject(result);
            JSONArray list = resuleJ.getJSONObject("data").getJSONArray("list");
            JSONObject device;
            R<TblDeviceInfo> r;
            R r1;
            TblDeviceInfo deviceInfo;
            JSONObject deviceStatus = null;
            JSONObject data;
            TblDeviceStatus tblDeviceStatus;
            List<String> indexCodes;
            if (!StringUtils.isNull(list) && list.size() > 0) {
                indexCodes = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    device = list.getJSONObject(i);
                    indexCodes.add(device.getString("indexCode"));
                }
                body = new JSONObject();
                body.put("indexCodes", indexCodes);
                body.put("pageNo", 1);
                body.put("pageSize", 1000);
                result = httpRequest("/api/nms/v1/online/camera/get", body.toJSONString());
                if (result != null) {
                    data = JSONObject.parseObject(result);
                    if(data.getString("code").equals("0")){
                        list = data.getJSONObject("data").getJSONArray("list");
                        if (!StringUtils.isNull(list) && list.size() > 0) {
                            for (int i = 0; i < list.size(); i++) {
                                deviceStatus = list.getJSONObject(i);
                                r = remoteDeviceMonitorService.selectByDeviceId(deviceStatus.getString("indexCode"));
                                if (!StringUtils.isNull(r) && r.getCode() == R.SUCCESS) {
                                    deviceInfo = r.getData();
                                    if (!StringUtils.isNull(deviceInfo)) {
                                        tblDeviceStatus = new TblDeviceStatus();
                                        tblDeviceStatus.setDeviceId(deviceInfo.getId());
                                        tblDeviceStatus.setTime(DateUtils.getNowDate());
                                        if(StringUtils.isNotNull(deviceStatus) && deviceStatus.containsKey("online") && StringUtils.isNotNull(deviceStatus.getString("online"))){
                                            switch (deviceStatus.getInteger("online")){
                                                case 0:
                                                    tblDeviceStatus.setStatus(0);
                                                    tblDeviceStatus.setStatusDesc("网络异常");
                                                    break;
                                                case 1:
                                                    tblDeviceStatus.setStatus(1);
                                                    tblDeviceStatus.setStatusDesc("正常");
                                                    break;
                                            }
                                        }else{
                                            tblDeviceStatus.setStatus(0);
                                            tblDeviceStatus.setStatusDesc("状态未知");
                                        }
                                        r1 = remoteDeviceMonitorService.updateHeartbeat(tblDeviceStatus);
                                        if (StringUtils.isNull(r1) || r1.getCode() == R.FAIL) {
                                            log.error(deviceStatus.getString("indexCode") + "设备状态更新失败");
                                        }
                                    } else {
                                        log.error(deviceStatus.getString("indexCode") + "设备编号未录入系统，请联系管理员处理");
                                    }
                                } else {
                                    log.error(deviceStatus.getString("indexCode") + "设备编号未录入系统，请联系管理员处理");
                                }
                            }
                        }
                    }else {
                        log.error("查询执法记录仪接口返回异常:"+data.getString("msg"));
                    }
                }
            }
        }
    }

    @Override
    public String getVideoUrl(String deviceId) {
        JSONObject params = new JSONObject();
        params.put("cameraIndexCode", deviceId);//监控点唯一标识
        params.put("protocol", "hls");//取流协议
        params.put("streamType", 0);//码流类型 0:主码 1:子码 2:第三码流
        params.put("transmode", 1);//传输协议（传输层协议），0:UDP 1:TCP
        params.put("streamform", "ps");//输出码流转封装格式
        String result = httpRequest("/api/video/v2/cameras/previewURLs", params.toJSONString());
        return result;
    }
}
