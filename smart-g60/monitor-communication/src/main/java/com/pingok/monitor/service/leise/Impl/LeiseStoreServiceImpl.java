package com.pingok.monitor.service.leise.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.domain.leise.TblLeiseEvent;
import com.pingok.monitor.domain.leise.TblLeiseObject;
import com.pingok.monitor.mapper.leise.TblLeiseEventMapper;
import com.pingok.monitor.mapper.leise.TblLeiseObjectMapper;
import com.pingok.monitor.service.leise.ILeiseStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 * @time 2022/5/25 10:55
 */
@Slf4j
@Service
public class LeiseStoreServiceImpl implements ILeiseStoreService {

    @Autowired
    private TblLeiseObjectMapper tblLeiseObjectMapper;
    @Autowired
    private TblLeiseEventMapper tblLeiseEventMapper;

    @Override
    public void saveObject(JSONObject objectData) {
        try {
            TblLeiseObject tblLeiseObject = JSON.parseObject(objectData.toJSONString(), TblLeiseObject.class);
            tblLeiseObjectMapper.insert(tblLeiseObject);
        }catch (Exception e){
            log.error("暂存雷摄数据异常：" + objectData.toJSONString());
        }
    }

    @Override
    public void saveEvent(JSONObject eventData) {
        try {
            TblLeiseEvent tblLeiseEvent = JSON.parseObject(eventData.toJSONString(), TblLeiseEvent.class);
            tblLeiseEventMapper.insert(tblLeiseEvent);
        }catch (Exception e){
            log.error("暂存雷摄数据异常：" + eventData.toJSONString());
        }
    }
}
