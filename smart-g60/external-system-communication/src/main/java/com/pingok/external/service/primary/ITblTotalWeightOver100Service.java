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
     * 超限流量分布数据
     * @param result
     */
    void getTotalWeightOver100(JSONArray result);
}
