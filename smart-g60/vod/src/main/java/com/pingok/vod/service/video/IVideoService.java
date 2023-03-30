package com.pingok.vod.service.video;

import java.util.Date;

/**
 * @author lal
 */
public interface IVideoService {
    String getVideoPreviewUrl(String deviceId,String protocol) ;
    String getCamerasPlayBackUrl(String deviceId, String protocol, String beginTime,String endTime);
}
