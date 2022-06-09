package com.pingok.vod.service;


import com.alibaba.fastjson.JSONArray;

/**
 * 设备心跳 服务层
 *
 * @author qiumin
 */
public interface IDeviceHeartbeatService {

    void heartbeat(JSONArray heartbeat);

}
