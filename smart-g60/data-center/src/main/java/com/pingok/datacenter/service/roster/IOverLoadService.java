package com.pingok.datacenter.service.roster;

import com.alibaba.fastjson.JSONObject;

/**
 * 超限车辆名单 业务层
 *
 * @author ruoyi
 */
public interface IOverLoadService {

    /**
     * 超限车辆更新
     * @param obj
     */
    void overLoad(JSONObject obj);
}
