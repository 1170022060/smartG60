package com.pingok.vod.service.video.impl;

import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;
import com.pingok.vod.config.Video4Config;
import com.pingok.vod.config.Video5Config;
import com.pingok.vod.config.Video6Config;
import com.pingok.vod.service.video.IVideoService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lal
 */
@Service
public class VideoServiceImpl implements IVideoService {
    /**
     * 能力开放平台的网站路径
     */

    private static final String ARTEMIS_PATH = "/artemis";

    @Override
    public String getVideoPreviewUrl(String deviceId,String protocol)  {
        ArtemisConfig ArtemisConfig = new ArtemisConfig();

        if (protocol.equals("172.37.4") ){
            ArtemisConfig.setHost(Video4Config.HOST);
            ArtemisConfig.setAppKey(Video4Config.AK);
            ArtemisConfig.setAppSecret(Video4Config.SK);
        }else if(protocol.equals("172.37.5") ) {
            ArtemisConfig.setHost(Video5Config.HOST);
            ArtemisConfig.setAppKey(Video5Config.AK);
            ArtemisConfig.setAppSecret(Video5Config.SK);
        }else if (protocol.equals("172.37.6")){
            ArtemisConfig.setHost(Video6Config.HOST);
            ArtemisConfig.setAppKey(Video6Config.AK);
            ArtemisConfig.setAppSecret(Video6Config.SK);
        }

        final String getCamsApi = ARTEMIS_PATH + "/api/video/v2/cameras/previewURLs";
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getCamsApi);
            }
        };

        String contentType = "application/json";

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("cameraIndexCode", deviceId);//监控点唯一标识
        jsonBody.put("protocol", "hls");//取流协议
        jsonBody.put("streamType", 0);//码流类型 0:主码 1:子码 2:第三码流
        jsonBody.put("transmode", 1);//传输协议（传输层协议），0:UDP 1:TCP
        jsonBody.put("streamform", "ps");//输出码流转封装格式
        String body = jsonBody.toJSONString();
        String result = null;
        try {
//            result = ArtemisHttpUtil.doPostStringArtemis(ArtemisConfig,path, body, null, null, contentType, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public String getCamerasPlayBackUrl(String deviceId, String protocol, String beginTime, String endTime) {
        ArtemisConfig ArtemisConfig = new ArtemisConfig();

        if (protocol.equals("172.37.4") ){
            ArtemisConfig.setHost(Video4Config.HOST);
            ArtemisConfig.setAppKey(Video4Config.AK);
            ArtemisConfig.setAppSecret(Video4Config.SK);
        }else if(protocol.equals("172.37.5") ) {
            ArtemisConfig.setHost(Video5Config.HOST);
            ArtemisConfig.setAppKey(Video5Config.AK);
            ArtemisConfig.setAppSecret(Video5Config.SK);
        }else if (protocol.equals("172.37.6")){
            ArtemisConfig.setHost(Video6Config.HOST);
            ArtemisConfig.setAppKey(Video6Config.AK);
            ArtemisConfig.setAppSecret(Video6Config.SK);
        }

        final String getCamsApi = ARTEMIS_PATH +"/api/video/v2/cameras/playbackURLs";


        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", getCamsApi);
            }
        };

        String contentType = "application/json";

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("cameraIndexCode", deviceId);//监控点唯一标识
//        jsonBody.put("protocol", "");//取流协议
        jsonBody.put("beginTime", beginTime);//录像开始时间
        jsonBody.put("endTime", endTime);//录像结束时间
        String body = jsonBody.toJSONString();
        String result = null;
        try {
//            result = ArtemisHttpUtil.doPostStringArtemis(ArtemisConfig,path, body, null, null, contentType, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
