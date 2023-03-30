package com.pingok.datacenter.service.roster;

import com.alibaba.fastjson.JSONObject;

/**
 * 绿通名单 业务层
 *
 * @author ruoyi
 */
public interface ICarFlowService {

    /**
     * 绿通名单更新
     * @param obj
     */
    void carFlowStatistics(JSONObject obj);

}
