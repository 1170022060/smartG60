package com.pingok.external.service.primary;

import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

public interface ITblTotalWeightOver100Service {
    /**
     * 开始发起kafka消息
     */
    void collect();

    /**
     * 按治超站统计车货总重大于 100 吨超限量数据
     * @param result
     */
    void getTotalWeightOver100(JSONArray result);
}
