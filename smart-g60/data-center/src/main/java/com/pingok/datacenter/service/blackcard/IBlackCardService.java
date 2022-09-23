package com.pingok.datacenter.service.blackcard;

import com.alibaba.fastjson.JSONObject;

/**
 * 模拟清分 业务层
 *
 * @author ruoyi
 */

public interface IBlackCardService {
    /**
     * 状态名单更新
     * @param obj
     */
    void blackcard(JSONObject obj);
}
