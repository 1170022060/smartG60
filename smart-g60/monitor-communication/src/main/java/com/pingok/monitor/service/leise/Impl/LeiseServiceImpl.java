package com.pingok.monitor.service.leise.Impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.domain.leise.TblLeiseEvent;
import com.pingok.monitor.domain.leise.TblLeiseObject;
import com.pingok.monitor.mapper.leise.TblLeiseEventMapper;
import com.pingok.monitor.mapper.leise.TblLeiseObjectMapper;
import com.pingok.monitor.service.leise.ILeiseService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @author
 * @time 2022/5/24 10:38
 */
@Slf4j
@Service
public class LeiseServiceImpl implements ILeiseService {
    @Value("${daas.host}")
    private String host;
//    private String host = "localhost:9307";

    @Autowired
    private TblLeiseObjectMapper tblLeiseObjectMapper;
    @Autowired
    private TblLeiseEventMapper tblLeiseEventMapper;

    @Async
    @Override
    public Boolean handleObject(JSONObject objectData) {
        Boolean ret = false;
        String data = objectData.toJSONString();
        TblLeiseObject tblLeiseObject = JSONObject.parseObject(data, TblLeiseObject.class);
        if(tblLeiseObject != null) {
            try{
                if(send("object", data)) {
                    Example example = new Example(TblLeiseObject.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("id", tblLeiseObject.getId());
                    tblLeiseObjectMapper.deleteByExample(example);
                    ret = true;
                }
            }catch (Exception e){
                log.error("雷摄请求处理异常：" + data);
            }
        }
        return ret;
    }

    @Async
    @Override
    public Boolean handleEvent(JSONObject eventData) {
        Boolean ret = false;
        String data = eventData.toJSONString();
        TblLeiseEvent tblLeiseEvent = JSONObject.parseObject(data, TblLeiseEvent.class);
        if(tblLeiseEvent != null) {
            try{
                if(send("event", data)) {
                    Example example = new Example(TblLeiseObject.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("id", tblLeiseEvent.getId());
                    tblLeiseEventMapper.deleteByExample(example);
                    ret = true;
                }
            }catch (Exception e){
                log.error("雷摄请求处理异常：" + data);
            }
        }
        return ret;
    }

    Boolean send(String type, String data) {
        String post = "";
        R ret = null;
        int[] sleep = new int[] { 1,3,6 };
        int i = 0;
        String url = host + "/leise/" + type;
        while (i < sleep.length) {
            try {
                post = HttpUtil.post(url, data);
                if (!StringUtils.isEmpty(post)) {
                    ret = JSON.parseObject(post, R.class);
                    if (R.FAIL == ret.getCode()) {
                        log.error(data + "雷摄数据转发失败：" + ret.getMsg());
                    } else {
                        break;
                    }
                    Thread.sleep(sleep[i++] * 1000);
                }
            } catch (Exception e) {
                log.error(data + "雷摄数据转发异常：" + e.getMessage());
            }
        }
        return R.SUCCESS == ret.getCode() ? true : false;
    }
}
