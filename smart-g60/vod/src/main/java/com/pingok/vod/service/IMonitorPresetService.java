package com.pingok.vod.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.vod.domain.TblMonitorPreset;

import java.util.List;

/**
 * 枪云监控预设 服务层
 *
 * @author qiumin
 */
public interface IMonitorPresetService {

    JSONArray getCameraStatus();

    JSONObject getVodCurtime(Long id);

    JSONObject vodControl(Long id,String type,Integer playSpeed,String seekTime);

    JSONObject stopVod(Long id);

    JSONObject startVod(Long id, String start, String end);

    JSONArray getRecordList(Long id, String startTime, String endTime);

    JSONObject ptzControl(Long id, String type, Integer param);

    void stopLive(List<Long> ids);

    JSONArray startLive(List<Long> ids);

    void bind(TblMonitorPreset tblMonitorPreset);

    List<Object> findByUserId();

    JSONArray getCameraList();
}
