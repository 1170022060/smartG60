package com.pingok.datacenter.service.roster;

import com.alibaba.fastjson.JSONObject;

/**
 * 最小费率名单 业务层
 *
 * @author ruoyi
 */
public interface IRateService  {

    /**
     * 最小费率更新
     * @param obj
     */
    void rate(JSONObject obj);

    /**
     * 下载最小费率
     */
    void rateDownload();
}
