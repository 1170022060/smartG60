package com.pingok.datacenter.service.roster;

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
    void blackCard(JSONObject obj);

    /**
     * 下载卡黑名单增量
     * @param version 版本号
     */
    void increment(String version);

    /**
     * 下载卡黑名单全量
     * @param version 版本号
     */
    void all(String version);

    /**
     * 卡黑名单全量解压入库
     * @param version 版本号
     */
    void unzipAll(String version);
}
