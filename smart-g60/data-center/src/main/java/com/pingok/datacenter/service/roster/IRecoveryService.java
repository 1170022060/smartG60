package com.pingok.datacenter.service.roster;

import com.alibaba.fastjson.JSONObject;

/**
 * 追缴名单 业务层
 *
 * @author ruoyi
 */
public interface IRecoveryService {

    /**
     * 追缴名单更新
     * @param obj
     */
    void recovery(JSONObject obj);
}
