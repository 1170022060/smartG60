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

    /**
     * 下载抢险救灾名单增量
     * @param version 版本号
     */
    void increment(String version);

    /**
     * 下载抢险救灾名单全量
     * @param version 版本号
     */
    void all(String version);


    /**
     * 抢险救灾名单全量入库
     * @param version 版本号
     */
    void unzipAll(String version);
}
