package com.pingok.monitor.service.videoEvent.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.config.HostConfig;
import com.pingok.monitor.domain.event.*;
import com.pingok.monitor.domain.event.vo.FaceInfoVo;
import com.pingok.monitor.mapper.event.*;
import com.pingok.monitor.service.videoEvent.IVideoEventService;
import com.pingok.monitor.service.videoEvent.IVideoService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.sign.Base64;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author
 * @time 2022/5/6 15:56
 */
@Slf4j
@Service
public class VideoEventServiceImpl implements IVideoEventService {

    @Autowired
    private TblEventFluxMapper tblEventFluxMapper;
    @Autowired
    private TblEventParkingEventMapper tblEventParkingEventMapper;
    @Autowired
    private TblEventPassengerFlowMapper tblEventPassengerFlowMapper;
    @Autowired
    private TblEventVehicleEventMapper tblEventVehicleEventMapper;
    @Autowired
    private TblEventPlateInfoMapper tblEventPlateInfoMapper;
    @Autowired
    private IVideoService iVideoService;


    @Async
    @Override
    public void updateFaceInfo(FaceInfoVo faceInfoVo) {
        log.info("人像信息上传------------------------------" + faceInfoVo.getUbiLogicId());
        faceInfoVo.setSzImg(updateImages(faceInfoVo.getSzImg()));
        faceInfoVo.setSzSmallImg(updateImages((faceInfoVo.getSzSmallImg())));
        log.info(JSON.toJSONString(faceInfoVo));
        int time = 3;
        while (true) {
            try {
                String post = HttpUtil.post(HostConfig.DASSHOST + "/event/eventControl/faceInfo", JSON.toJSONString(faceInfoVo));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        R ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(faceInfoVo.getUbiLogicId() + " 人像信息上传失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(faceInfoVo.getUbiLogicId() + " 人像信息上传失败：" + post);
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (Exception e) {
                log.error(faceInfoVo.getUbiLogicId() + " 人像信息上传失败：" + e.getMessage());
            }
            time += 3;
            if (time > 9) {
                return;
            }
        }
    }

    @Override
    public void fluxData(TblEventFlux tblEventFlux) {
        tblEventFluxMapper.insert(tblEventFlux);
    }


    @Override
    public void plateInfo(TblEventPlateInfo tblEventPlateInfo) {
        tblEventPlateInfoMapper.insert(tblEventPlateInfo);
    }


    @Override
    public void vehicleEvent(TblEventVehicleEvent tblEventVehicleEvent) {
        tblEventVehicleEventMapper.insert(tblEventVehicleEvent);
    }


    @Override
    public void passengerFlow(TblEventPassengerFlow tblEventPassengerFlow) {
        tblEventPassengerFlowMapper.insert(tblEventPassengerFlow);
    }


    @Override
    public void parkingEvent(TblEventParkingEvent tblEventParkingEvent) {
        tblEventParkingEventMapper.insert(tblEventParkingEvent);
    }


    @Override
    public void updateEventVideo(Long ubiLogicId) {
        String url = iVideoService.getEventVideoById(ubiLogicId, 2, 1);
        if (url != null) {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("ubiLogicId", ubiLogicId);
            paramMap.put("url", url);
            try {
                String post = HttpUtil.post(HostConfig.DASSHOST + "/event/eventControl/updateEventVideo", paramMap);
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        JSONObject ret = JSONObject.parseObject(post);
                        if (ret.containsKey("code") && ret.getInteger("code") != 200) {
                            log.error(ubiLogicId + " 事件视频上传失败：" + ret.getString("msg"));
                        }
                    } else {
                        log.error(ubiLogicId + " 事件视频上传状态未知");
                    }
                }
            } catch (Exception e) {
                log.error(ubiLogicId + " 事件视频上传失败：" + e.getMessage());
            }
        }
    }

    @Async
    @Override
    public void updateFluxData(TblEventFlux tblEventFlux) {
        int time = 3;
        while (true) {
            try {
                String post = HttpUtil.post(HostConfig.DASSHOST + "/event/eventControl/flux", JSON.toJSONString(tblEventFlux));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        R ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(tblEventFlux.getUbiLogicId() + " 流量统计上报失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(tblEventFlux.getUbiLogicId() + " 流量统计上报状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (Exception e) {
                log.error(tblEventFlux.getUbiLogicId() + " 流量统计上报失败：" + e.getMessage());
            }
            time += 3;
            if (time > 9) {
                return;
            }
        }
        tblEventFluxMapper.delete(tblEventFlux);
    }

    @Async
    @Override
    public void updatePlateInfo(TblEventPlateInfo tblEventPlateInfo) {
        int time = 3;
        while (true) {
            try {
                String post = HttpUtil.post(HostConfig.DASSHOST + "/event/eventControl/plateInfo", JSON.toJSONString(tblEventPlateInfo));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        R ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(tblEventPlateInfo.getUbiLogicId() + " 过车数据上报失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(tblEventPlateInfo.getUbiLogicId() + " 过车数据上报状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (Exception e) {
                log.error(tblEventPlateInfo.getUbiLogicId() + " 过车数据上报失败：" + e.getMessage());
            }
            time += 3;
            if (time > 9) {
                return;
            }
        }
        tblEventPlateInfoMapper.delete(tblEventPlateInfo);
    }

    @Async
    @Override
    public void updateVehicleEvent(TblEventVehicleEvent tblEventVehicleEvent) {
        int time = 3;
        String post;
        while (true) {
            try {
                post = HttpUtil.post(HostConfig.DASSHOST + "/event/eventControl/vehicleEvent", JSON.toJSONString(tblEventVehicleEvent));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        R ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(tblEventVehicleEvent.getUbiLogicId() + " 交通事件上报失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(tblEventVehicleEvent.getUbiLogicId() + " 交通事件上报状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (Exception e) {
                log.error(tblEventVehicleEvent.getUbiLogicId() + " 交通事件上报失败：" + e.getMessage());
            }
            time += 3;
            if (time > 9) {
                return;
            }
        }

        tblEventVehicleEventMapper.delete(tblEventVehicleEvent);

        List<Integer> list = Arrays.asList(5, 6, 14, 15, 10016, 31, 32, 34, 35, 36, 40, 41, 37);
        if (!list.contains(tblEventVehicleEvent.getUiEventType())) {
//            try {
//                Thread.sleep(120 * 1000);
//                updateEventVideo(tblEventVehicleEvent.getUbiLogicId());
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
            updateEventVideo(tblEventVehicleEvent.getUbiLogicId());
        }
    }

    @Async
    @Override
    public void updatePassengerFlow(TblEventPassengerFlow tblEventPassengerFlow) {
        int time = 3;
        while (true) {
            try {
                String post = HttpUtil.post(HostConfig.DASSHOST + "/event/eventControl/passengerFlow", JSON.toJSONString(tblEventPassengerFlow));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        R ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(tblEventPassengerFlow.getUiType() + " 客流量上报失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(tblEventPassengerFlow.getUiType() + " 客流量上报状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (Exception e) {
                log.error(tblEventPassengerFlow.getUiType() + " 客流量上报失败：" + e.getMessage());
            }
            time += 3;
            if (time > 9) {
                return;
            }
        }
        tblEventPassengerFlowMapper.delete(tblEventPassengerFlow);
    }

    @Async
    @Override
    public void updateParkingEvent(TblEventParkingEvent tblEventParkingEvent) {
        int time = 3;
        while (true) {
            try {
                String post = HttpUtil.post(HostConfig.DASSHOST + "/event/eventControl/parkingEvent", JSON.toJSONString(tblEventParkingEvent));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        R ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(tblEventParkingEvent.getUbiLogicId() + " 货车检查事件上报失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(tblEventParkingEvent.getUbiLogicId() + " 货车检查事件上报状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (Exception e) {
                log.error(tblEventParkingEvent.getUbiLogicId() + " 货车检查事件上报失败：" + e.getMessage());
            }
            time += 3;
            if (time > 9) {
                return;
            }
        }
        tblEventParkingEventMapper.delete(tblEventParkingEvent);
    }


    private String updateImages(String images) {
        String url = null;
        byte[] bytes = Base64.decode(images);
        HttpResponse httpResponse = HttpRequest.post(HostConfig.DASSHOST + "/ruoyi-file/upload")
                .form("file", bytes, ContentType.APPLICATION_OCTET_STREAM.toString())
                .execute();
        String post = httpResponse.body();
        if (!StringUtils.isEmpty(post)) {
            if (post.startsWith("{")) {
                JSONObject ret = JSONObject.parseObject(post);
                if (ret.containsKey("code") && ret.getInteger("code") == 200) {
                    url = ret.getJSONObject("data").getString("url");
                } else {
                    log.error(" 图片上传失败：" + ret.getString("msg"));
                }
            } else {
                log.error(" 图片上传失败：" + post);
            }
        } else {
            log.error(" 图片上传失败：" + post);
        }
        return url;
    }

}
