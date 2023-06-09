package com.pingok.vod.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.vod.config.VodConfig;
import com.pingok.vod.domain.TblDeviceInfo;
import com.pingok.vod.domain.TblMonitorPreset;
import com.pingok.vod.mapper.TblMonitorPresetMapper;
import com.pingok.vod.service.IDeviceInfoService;
import com.pingok.vod.service.IMonitorPresetService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.IdUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import lombok.extern.slf4j.Slf4j;
import org.bytedeco.javacpp.avcodec;
import org.bytedeco.javacv.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * 设备信息 服务层处理
 *
 * @author qiumin
 */
@Slf4j
@Service
public class MonitorPresetServiceImpl implements IMonitorPresetService {


    @Autowired
    private TblMonitorPresetMapper tblMonitorPresetMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private IDeviceInfoService iDeviceInfoService;

    @Override
    public void downloadVod(HttpServletResponse response,  String url) {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(url);
        FFmpegFrameRecorder recorder = null;
        File outFile = null;
        try {
            grabber.start();
            Frame frame = grabber.grabFrame();
            if (frame != null) {
                String filePath = "/root/vod/" + DateUtils.getNowTimestampLong() + ".flv";
                outFile = new File(filePath);
                if (!outFile.isFile()) {
                    try {
                        outFile.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                // 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
                recorder = new FFmpegFrameRecorder(filePath, 1080, 1440, 1);
                recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);// 直播流格式
                recorder.setFormat("flv");// 录制的视频格式
                recorder.setFrameRate(25);// 帧数
                //百度翻译的比特率，默认400000，但是我400000贼模糊，调成800000比较合适
                recorder.setVideoBitrate(800000);
                recorder.start();
                while ((frame != null)) {
                    recorder.record(frame);// 录制
                    frame = grabber.grabFrame();// 获取下一帧
                }
                recorder.record(frame);
                // 停止录制
                recorder.stop();
                grabber.stop();
            }
        } catch (FrameGrabber.Exception e) {
            e.printStackTrace();
        } catch (FrameRecorder.Exception e) {
            e.printStackTrace();
        } finally {
            if (null != grabber) {
                try {
                    grabber.stop();
                } catch (FrameGrabber.Exception e) {
                    e.printStackTrace();
                }
            }
            if (recorder != null) {
                try {
                    recorder.stop();
                } catch (FrameRecorder.Exception e) {
                    e.printStackTrace();
                }
            }
            if (outFile != null) {
//                outFile.delete();
            }
        }
    }


    @Override
    public JSONArray getCameraStatus() {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("req_type", "get_camera_status_request");
        HashMap<String, Object> retHeader = new HashMap<>();
        retHeader.put("guid", IdUtils.fastUUID());
        retHeader.put("user", VodConfig.USER);
        retHeader.put("pwd", VodConfig.PWD);
        paramMap.put("req_header", retHeader);
        String post = HttpUtil.post(VodConfig.HOST, JSON.toJSONString(paramMap));
        JSONArray cameraStatus = null;
        JSONObject ret;
        if (!StringUtils.isEmpty(post)) {
            ret = JSONObject.parseObject(post);
            if (ret.getJSONObject("ret_header").containsKey("code") && ret.getJSONObject("ret_header").getString("code").equals("0")) {
                cameraStatus = ret.getJSONObject("ret_body").getJSONArray("camera_status_list");
            } else {
                throw new ServiceException(ret.getJSONObject("ret_header").getString("msg"));
            }
        }
        return cameraStatus;
    }

    @Override
    public JSONObject getVodCurtime(Long id) {
        JSONObject r = new JSONObject();
        HashMap<String, Object> paramMap;
        HashMap<String, Object> retHeader;
        HashMap<String, Object> retBody;
        paramMap = new HashMap<>();
        retHeader = new HashMap<>();
        retBody = new HashMap<>();
        paramMap.put("req_type", "get_vod_curtime_request");
        retHeader.put("guid", IdUtils.fastUUID());
        retHeader.put("user", VodConfig.USER);
        retHeader.put("pwd", VodConfig.PWD);
        paramMap.put("req_header", retHeader);
        retBody.put("session_id", id);
        paramMap.put("req_body", retBody);
        String post = HttpUtil.post(VodConfig.HOST, JSON.toJSONString(paramMap));
        if (!StringUtils.isEmpty(post)) {
            JSONObject req = JSONObject.parseObject(post);
            r.put("code", req.getJSONObject("ret_header").getIntValue("code"));
            r.put("msg", req.getJSONObject("ret_header").getString("msg"));
            if (req.getJSONObject("ret_header").containsKey("code") && req.getJSONObject("ret_header").getString("code").equals("0")) {
                r.put("curTime", req.getJSONObject("ret_body").getString("session_id"));
            }
        }
        return r;
    }

    @Override
    public JSONObject vodControl(Long id, String type, String playSpeed, String seekTime) {
        JSONObject r = new JSONObject();
        HashMap<String, Object> paramMap;
        HashMap<String, Object> retHeader;
        HashMap<String, Object> retBody;
        paramMap = new HashMap<>();
        retHeader = new HashMap<>();
        retBody = new HashMap<>();
        paramMap.put("req_type", "vod_control_request");
        retHeader.put("guid", IdUtils.fastUUID());
        retHeader.put("user", VodConfig.USER);
        retHeader.put("pwd", VodConfig.PWD);
        paramMap.put("req_header", retHeader);
        retBody.put("session_id", id);
        retBody.put("control_type", type);
        if (StringUtils.isNotNull(playSpeed)) {
            retBody.put("play_speed", playSpeed);
        }
        if (StringUtils.isNotNull(seekTime)) {
            retBody.put("seek_time", seekTime);
        }
        paramMap.put("req_body", retBody);
        String post = HttpUtil.post(VodConfig.HOST, JSON.toJSONString(paramMap));
        if (!StringUtils.isEmpty(post)) {
            log.info("vodControl----------" + post);
            JSONObject req = JSONObject.parseObject(post);
            r.put("code", req.getJSONObject("ret_header").getIntValue("code"));
            r.put("msg", req.getJSONObject("ret_header").getString("msg"));
        }
        return r;
    }

    @Override
    public JSONObject stopVod(Long id) {
        JSONObject r = new JSONObject();
        HashMap<String, Object> paramMap;
        HashMap<String, Object> retHeader;
        HashMap<String, Object> retBody;
        paramMap = new HashMap<>();
        retHeader = new HashMap<>();
        retBody = new HashMap<>();
        paramMap.put("req_type", "stop_vod_request");
        retHeader.put("guid", IdUtils.fastUUID());
        retHeader.put("user", VodConfig.USER);
        retHeader.put("pwd", VodConfig.PWD);
        paramMap.put("req_header", retHeader);
        retBody.put("session_id", id);
        paramMap.put("req_body", retBody);
        String post = HttpUtil.post(VodConfig.HOST, JSON.toJSONString(paramMap));
        if (!StringUtils.isEmpty(post)) {
            JSONObject req = JSONObject.parseObject(post);
            r.put("code", req.getJSONObject("ret_header").getIntValue("code"));
            r.put("msg", req.getJSONObject("ret_header").getString("msg"));
        }
        return r;
    }

    @Override
    public JSONObject startVod(Long id, String start, String end) {
        JSONObject r = new JSONObject();
        HashMap<String, Object> paramMap;
        HashMap<String, Object> retHeader;
        HashMap<String, Object> retBody;
        paramMap = new HashMap<>();
        retHeader = new HashMap<>();
        retBody = new HashMap<>();
        paramMap.put("req_type", "start_vod_request");
        retHeader.put("guid", IdUtils.fastUUID());
        retHeader.put("user", VodConfig.USER);
        retHeader.put("pwd", VodConfig.PWD);
        paramMap.put("req_header", retHeader);
        retBody.put("camera_id", id);
        retBody.put("start_time", start);
        retBody.put("end_time", end);
        paramMap.put("req_body", retBody);
        String post = HttpUtil.post(VodConfig.HOST, JSON.toJSONString(paramMap));
        if (!StringUtils.isEmpty(post)) {
            JSONObject req = JSONObject.parseObject(post);
            r.put("code", req.getJSONObject("ret_header").getIntValue("code"));
            r.put("msg", req.getJSONObject("ret_header").getString("msg"));
            if (req.getJSONObject("ret_header").containsKey("code") && req.getJSONObject("ret_header").getString("code").equals("0")) {
                r.put("sessionId", req.getJSONObject("ret_body").getString("session_id"));
                r.put("url", req.getJSONObject("ret_body").getString("vod_flv_url"));
            }
        }
        return r;
    }

    @Override
    public JSONArray getRecordList(Long id, String startTime, String endTime) {
        JSONArray r = new JSONArray();
        HashMap<String, Object> paramMap;
        HashMap<String, Object> retHeader;
        HashMap<String, Object> retBody;
        paramMap = new HashMap<>();
        retHeader = new HashMap<>();
        retBody = new HashMap<>();
        paramMap.put("req_type", "get_record_list_request");
        retHeader.put("guid", IdUtils.fastUUID());
        retHeader.put("user", VodConfig.USER);
        retHeader.put("pwd", VodConfig.PWD);
        paramMap.put("req_header", retHeader);
        retBody.put("camera_id", id);
        retBody.put("start_time", startTime);
        retBody.put("end_time", endTime);
        paramMap.put("req_body", retBody);
        String post = HttpUtil.post(VodConfig.HOST, JSON.toJSONString(paramMap));
        if (!StringUtils.isEmpty(post)) {
            JSONObject req = JSONObject.parseObject(post);
            if (req.getJSONObject("ret_header").containsKey("code") && req.getJSONObject("ret_header").getString("code").equals("0")) {
                r = req.getJSONObject("ret_body").getJSONArray("record_list");
            }
        }
        return r;
    }

    @Override
    public JSONObject ptzControl(Long id, String type, String param) {
        JSONObject r = new JSONObject();
        HashMap<String, Object> paramMap;
        HashMap<String, Object> retHeader;
        HashMap<String, Object> retBody;
        paramMap = new HashMap<>();
        retHeader = new HashMap<>();
        retBody = new HashMap<>();
        paramMap.put("req_type", "ptz_control_request");
        retHeader.put("guid", IdUtils.fastUUID());
        retHeader.put("user", VodConfig.USER);
        retHeader.put("pwd", VodConfig.PWD);
        paramMap.put("req_header", retHeader);
        retBody.put("camera_id", id);
        retBody.put("ptz_control_type", type);
        retBody.put("ptz_param1", param);
//        retBody.put("ptz_param2", "0");
//        retBody.put("ptz_param3", "0");
//        retBody.put("ptz_param4", "0");
        paramMap.put("req_body", retBody);
        log.info(JSON.toJSONString(paramMap));
        String post = HttpUtil.post(VodConfig.HOST, JSON.toJSONString(paramMap));
        if (!StringUtils.isEmpty(post)) {
            log.info("ptzControl----------" + post);
            JSONObject req = JSONObject.parseObject(post);
            r.put("code", req.getJSONObject("ret_header").getIntValue("code"));
            r.put("msg", req.getJSONObject("ret_header").getString("msg"));
        }
        return r;
    }

    @Override
    public void stopLive(List<Long> ids) {
        HashMap<String, Object> paramMap;
        HashMap<String, Object> retHeader;
        HashMap<String, Object> retBody;
        String post = null;
        JSONObject req;
        TblDeviceInfo tblDeviceInfo;
        for (Long o : ids) {
            paramMap = new HashMap<>();
            retHeader = new HashMap<>();
            retBody = new HashMap<>();
            paramMap.put("req_type", "stop_live_request");
            retHeader.put("guid", IdUtils.fastUUID());
            retHeader.put("user", VodConfig.USER);
            retHeader.put("pwd", VodConfig.PWD);
            paramMap.put("req_header", retHeader);
            retBody.put("camera_id", o);
            paramMap.put("req_body", retBody);
            post = HttpUtil.post(VodConfig.HOST, JSON.toJSONString(paramMap));
            if (!StringUtils.isEmpty(post)) {
                req = JSONObject.parseObject(post);
                if (!req.getJSONObject("ret_header").containsKey("code") || !req.getJSONObject("ret_header").getString("code").equals("0")) {
                    log.error("设备编号为：" + o + " 的相机结束实时视频失败，错误：" + req.getJSONObject("ret_header").getString("msg"));
                }
            }
        }
    }

    @Override
    public JSONArray startLive(List<Long> ids) {
        JSONArray liveUrls = new JSONArray();
        JSONObject liveUrl;
        HashMap<String, Object> paramMap;
        HashMap<String, Object> retHeader;
        HashMap<String, Object> retBody;
        String post = null;
        JSONObject req;
        JSONObject reqBody;
        TblDeviceInfo tblDeviceInfo;
        for (Long cameraId : ids) {
            paramMap = new HashMap<>();
            retHeader = new HashMap<>();
            retBody = new HashMap<>();
            paramMap.put("req_type", "start_live_request");
            retHeader.put("guid", IdUtils.fastUUID());
            retHeader.put("user", VodConfig.USER);
            retHeader.put("pwd", VodConfig.PWD);
            paramMap.put("req_header", retHeader);
            retBody.put("camera_id", cameraId);
            paramMap.put("req_body", retBody);
            post = HttpUtil.post(VodConfig.HOST, JSON.toJSONString(paramMap));
            if (!StringUtils.isEmpty(post)) {
                req = JSONObject.parseObject(post);
                if (req.getJSONObject("ret_header").containsKey("code") && req.getJSONObject("ret_header").getString("code").equals("0")) {
                    reqBody = req.getJSONObject("ret_body");
                    liveUrl = new JSONObject();
                    liveUrl.put("id", cameraId);
                    liveUrl.put("url", reqBody.getString("live_flv_url"));
                    liveUrls.add(liveUrl);
                    ptzControl(cameraId, "gotopreset", "4");
                } else {
                    log.error("设备编号为：" + cameraId + " 的相机开始实时视频失败，错误：" + req.getJSONObject("ret_header").getString("msg"));
                }
            }
        }
        return liveUrls;
    }

    @Override
    public void bind(TblMonitorPreset tblMonitorPreset) {
        Example example = new Example(TblMonitorPreset.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", SecurityUtils.getUserId());
        List<TblMonitorPreset> tblMonitorPresets = tblMonitorPresetMapper.selectByExample(example);
        if (tblMonitorPresets != null && tblMonitorPresets.size() > 0) {
            for (TblMonitorPreset m : tblMonitorPresets) {
                tblMonitorPresetMapper.delete(m);
            }
        }
        if (tblMonitorPreset.getIds() != null && tblMonitorPreset.getIds().size() > 0) {
            List<Long> deviceIds = tblMonitorPreset.getIds();
            TblMonitorPreset m;
            for (Long id : deviceIds) {
                m = new TblMonitorPreset();
                m.setId(remoteIdProducerService.nextId());
                m.setUserId(SecurityUtils.getUserId());
                m.setDeviceId(id);
                m.setCreateTime(DateUtils.getNowDate());
                tblMonitorPresetMapper.insert(m);
            }
        }
    }

    @Override
    public List<Object> findByUserId() {
        return tblMonitorPresetMapper.findByUserId(SecurityUtils.getUserId());
    }

    @Override
    public JSONArray getCameraList() {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("req_type", "get_camera_request");
        HashMap<String, Object> retHeader = new HashMap<>();
        retHeader.put("guid", IdUtils.fastUUID());
        retHeader.put("user", VodConfig.USER);
        retHeader.put("pwd", VodConfig.PWD);
        paramMap.put("req_header", retHeader);
        String post = HttpUtil.post(VodConfig.HOST, JSON.toJSONString(paramMap));
        JSONArray cameraList = null;
        JSONObject ret;
        if (!StringUtils.isEmpty(post)) {
            ret = JSONObject.parseObject(post);
            if (ret.getJSONObject("ret_header").containsKey("code") && ret.getJSONObject("ret_header").getString("code").equals("0")) {
                cameraList = ret.getJSONObject("ret_body").getJSONArray("camera_list");

            }
        }
        return cameraList;
    }

    @Override
    public void streamAlive(List<Long> ids) {
        HashMap<String, Object> paramMap;
        HashMap<String, Object> retHeader;
        HashMap<String, Object> retBody;
        String post;
        JSONObject req;
        for (Long id : ids) {
            paramMap = new HashMap<>();
            retHeader = new HashMap<>();
            retBody = new HashMap<>();
            paramMap.put("req_type", "stream_alive_request");
            retHeader.put("guid", IdUtils.fastUUID());
            retHeader.put("user", VodConfig.USER);
            retHeader.put("pwd", VodConfig.PWD);
            paramMap.put("req_header", retHeader);
            retBody.put("id", id);
            paramMap.put("req_body", retBody);
            post = HttpUtil.post(VodConfig.HOST, JSON.toJSONString(paramMap));
            if (!StringUtils.isEmpty(post)) {
                req = JSONObject.parseObject(post);
                if (!req.getJSONObject("ret_header").containsKey("code") || !req.getJSONObject("ret_header").getString("code").equals("0")) {
                    log.error("设备编号为：" + id + " 的相机视频码流保活失败，错误：" + req.getJSONObject("ret_header").getString("msg"));
                }
            }
        }
    }
}
