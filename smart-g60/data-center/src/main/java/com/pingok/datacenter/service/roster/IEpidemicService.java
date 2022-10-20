package com.pingok.datacenter.service.roster;

import com.alibaba.fastjson.JSONObject;

/**
 * 中高风险名单 业务层
 *
 * @author ruoyi
 */
public interface IEpidemicService {

    /**
     * 中高风险名单更新
     * @param obj
     */
    void epidemic(JSONObject obj);

    /**
     * 中高风险车牌名单更新
     * @param obj
     */
    void epidemicPrefix(JSONObject obj);
}
