package com.pingok.datacenter.service.opt;

import com.alibaba.fastjson.JSONObject;

/**
 * 排班当岗 业务层
 *
 * @author ruoyi
 */
public interface IOptWorkDetailService {

    /**
     * 抢险救灾名单更新
     * @param obj
     */
    void optWorkDetail(JSONObject obj);
}
