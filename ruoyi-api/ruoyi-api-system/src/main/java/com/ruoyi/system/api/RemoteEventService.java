package com.ruoyi.system.api;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.algorithm.LaneAvgSpeed;
import com.ruoyi.system.api.domain.event.TblEventRecord;
import com.ruoyi.system.api.factory.RemoteEventFallbackFactory;
import com.ruoyi.system.api.factory.RemoteGpsFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 事件服务
 *
 * @author qiumin
 */
@FeignClient(contextId = "remoteEventService", value = ServiceNameConstants.EVENT_SERVICE, fallbackFactory = RemoteEventFallbackFactory.class)
public interface RemoteEventService {


    @PostMapping("/eventControl")
    R add(@Validated @RequestBody TblEventRecord tblEventRecord);

    @PostMapping("/algorithm/getLaneAvgSpeed")
    R<List<LaneAvgSpeed>> getLaneAvgSpeed(@RequestBody JSONObject body);
}
