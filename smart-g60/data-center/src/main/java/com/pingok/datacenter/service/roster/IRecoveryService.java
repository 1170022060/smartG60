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

    /**
     * 下载追缴名单增量
     * @param version 版本号
     */
    void increment(String version);

    /**
     * 下载追缴名单全量
     * @param version 版本号
     */
    void all(String version);


    /**
     * 追缴名单全量入库
     * @param version 版本号
     */
    void unzipAll(String version);
}
