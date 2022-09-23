package com.pingok.monitor.service.videoEvent.impl;

import com.alibaba.fastjson.JSON;
import com.pingok.monitor.config.AliYunConfig;
import com.pingok.monitor.service.videoEvent.IVideoService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteFileService;
import com.ruoyi.system.api.domain.SysFile;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author
 * @time 2022/5/6 15:56
 */
@Slf4j
@Service
public class VideoServiceImpl implements IVideoService {

    @Autowired
    private RemoteFileService remoteFileService;


    @Override
    public Boolean eventUpdate(Long ubiLogicId, Integer uiState, String szRemark, String szUser) {
        OkHttpClient client = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("uiState", uiState.toString())
                .add("szRemark", szRemark)
                .add("szUser", szUser)
                .build();
        final Request request = new Request.Builder()
                .url(AliYunConfig.VIDEOHOST + "/api/event/update/" + ubiLogicId)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if (response.code() == 200) {
                R r = JSON.parseObject(response.body().toString(), R.class);
                if (r.getCode() == R.SUCCESS) {
                    return true;
                } else {
                    log.error(ubiLogicId + "---事件确认失败---" + r.getMsg());
                    return false;
                }
            } else {
                log.error(ubiLogicId + "---事件确认失败---" + response.message());
                return false;
            }
        } catch (IOException e) {
            log.error(ubiLogicId + "---事件确认失败---" + e.getMessage());
            return false;
        }
    }

    @Override
    public String getImgById(Long imgId) {
        String url = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(AliYunConfig.VIDEOHOST + "/fileInfo/get-img-by-id/" + imgId)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if (response.code() == 200) {
                byte[] bytes = response.body().bytes();
                InputStream inputStream = new ByteArrayInputStream(bytes);
                MultipartFile file = new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
                R<SysFile> r = remoteFileService.upload(file);
                if (r.getCode() == R.SUCCESS) {
                    url = r.getData().getUrl();
                } else {
                    log.error(imgId + "---图片保存失败---" + r.getMsg());
                }
            } else {
                log.error(imgId + "---图片下载失败---" + response.message());
            }
        } catch (IOException e) {
            log.error(imgId + "---图片下载失败---" + e.getMessage());
        }
        return url;
    }

    @Override
    public String getEventVideoById(Long ubiLogicId, Integer uiEventType, Integer uiVideoType) {
        String url = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(AliYunConfig.VIDEOHOST + "/api/get-event-video-by-id/" + ubiLogicId + "?uiEventType=" + uiEventType + "&uiVideoType=" + uiVideoType)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            if (response.code() == 200) {
                byte[] bytes = response.body().bytes();
                InputStream inputStream = new ByteArrayInputStream(bytes);
                MultipartFile file = new MockMultipartFile(ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);
                R<SysFile> r = remoteFileService.upload(file);
                if (r.getCode() == R.SUCCESS) {
                    url = r.getData().getUrl();
                } else {
                    log.error(ubiLogicId + "---事件视频保存失败---" + r.getMsg());
                }
            } else {
                log.error(ubiLogicId + "---事件视频下载失败---" + response.message());
            }

        } catch (IOException e) {
            log.error(ubiLogicId + "---事件视频下载失败---" + e.getMessage());
        }
        return url;
    }
}
