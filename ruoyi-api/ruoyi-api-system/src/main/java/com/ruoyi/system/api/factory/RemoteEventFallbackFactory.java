package com.ruoyi.system.api.factory;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteEventService;
import com.ruoyi.system.api.domain.algorithm.LaneAvgSpeed;
import com.ruoyi.system.api.domain.event.TblEventRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 事件服务降级处理
 *
 * @author qiumin
 */
@Component
public class RemoteEventFallbackFactory implements FallbackFactory<RemoteEventService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteEventFallbackFactory.class);

    @Override
    public RemoteEventService create(Throwable throwable) {
        log.error("事件服务调用失败:{}", throwable.getMessage());
        return new RemoteEventService() {
            @Override
            public R<TblEventRecord> selectByEventTypeAndPileNo(String eventType, String pileNo) {
                return null;
            }

            @Override
            public R add(TblEventRecord tblEventRecord) {
                return null;
            }

            @Override
            public R edit(TblEventRecord tblEventRecord) {
                return null;
            }

            @Override
            public R<List<LaneAvgSpeed>> getLaneAvgSpeed(JSONObject body) {
                return null;
            }
        };

    }
}
