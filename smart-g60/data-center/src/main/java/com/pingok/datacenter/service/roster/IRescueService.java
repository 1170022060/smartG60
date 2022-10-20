package com.pingok.datacenter.service.roster;

import com.alibaba.fastjson.JSONObject;
/**
 * 抢险救灾名单 业务层
 *
 * @author ruoyi
 */
public interface IRescueService {

    /**
     * 抢险救灾名单更新
     * @param obj
     */
    void rescue(JSONObject obj);
}
