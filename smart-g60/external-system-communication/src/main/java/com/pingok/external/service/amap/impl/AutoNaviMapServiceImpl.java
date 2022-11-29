package com.pingok.external.service.amap.impl;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.config.AutoNaviMapConfig;
import com.pingok.external.domain.amap.TblAutoNaviMapRecord;
import com.pingok.external.mapper.amap.TblAutoNaviMapRecordMapper;
import com.pingok.external.service.amap.IAutoNaviMapService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.HttpUtils;
import com.ruoyi.common.core.utils.SignUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Service
public class AutoNaviMapServiceImpl implements IAutoNaviMapService {


    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblAutoNaviMapRecordMapper tblAutoNaviMapRecordMapper;

    @Override
    public void eventPublish(TblAutoNaviMapRecord autoNaviMapRecord) {
        Map<String, String> param = new TreeMap<>();
        param.put("adcode", AutoNaviMapConfig.ADCODE);
        param.put("clientKey", AutoNaviMapConfig.CLIENTKEY);
        param.put("timestamp", DateUtils.getNowShortTimestamp().toString());
        param.put("sourceId", AutoNaviMapConfig.SOURCEID);
        param.put("id", autoNaviMapRecord.getId().toString());
        param.put("stateFlag", "0");
        param.put("type", autoNaviMapRecord.getEventType().toString());
        param.put("locType", "1");
        param.put("roadName", "G60沪昆高速");
        param.put("locs", autoNaviMapRecord.getLocs());
        param.put("direction", autoNaviMapRecord.getDirection());
        param.put("startDate", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, autoNaviMapRecord.getStartDate()));
        param.put("desc", autoNaviMapRecord.getEventDesc());
        param.put("callback", AutoNaviMapConfig.CALLBACK + "/external-system/amap/callback");
        String digest = "";
        for (String key : param.keySet()) {
            digest += param.get(key).toString();
        }
        digest = SignUtils.HmacSHA256(digest, AutoNaviMapConfig.SECRET);
        param.put("digest", digest);

        String r = null;
        try {
            r = EntityUtils.toString(HttpUtils.doGet(AutoNaviMapConfig.HOST, null, null, param).getEntity(), "utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (!StringUtils.isEmpty(r)) {
            if (r.startsWith("{")) {
                JSONObject ret = JSONObject.parseObject(r);
                if (ret.containsKey("code") && ret.getString("code").equals("0")) {
                    Example example = new Example(TblAutoNaviMapRecord.class);
                    example.createCriteria()
                            .andEqualTo("id", autoNaviMapRecord.getId());
                    autoNaviMapRecord = tblAutoNaviMapRecordMapper.selectOneByExample(example);
                    if (autoNaviMapRecord == null) {
                        autoNaviMapRecord.setId(remoteIdProducerService.nextId());
                        autoNaviMapRecord.setStatus(0);
                        autoNaviMapRecord.setCreateTime(DateUtils.getNowDate());
                        autoNaviMapRecord.setCreateUserId(SecurityUtils.getUserId());
                        tblAutoNaviMapRecordMapper.insert(autoNaviMapRecord);
                    }
                } else {
                    log.error("高德地图推送事件失败，错误信息：" + ret.getString("errmsg"));
                    throw new ServiceException("高德地图推送事件失败，错误信息：" + ret.getString("errmsg"));
                }
            } else {
                log.error("高德地图推送事件失败，错误信息：" + r);
                throw new ServiceException("高德地图推送事件失败，错误信息：" + r);
            }
        }
    }

    @Override
    public void callback(String sourceid, Long id, Integer status) {
        Example example = new Example(TblAutoNaviMapRecord.class);
        example.createCriteria()
                .andEqualTo("id", id);
        TblAutoNaviMapRecord autoNaviMapRecord = tblAutoNaviMapRecordMapper.selectOneByExample(example);
        if (autoNaviMapRecord != null) {
            autoNaviMapRecord.setStatus(status);
            autoNaviMapRecord.setUpdateTime(DateUtils.getNowDate());
            tblAutoNaviMapRecordMapper.updateByPrimaryKey(autoNaviMapRecord);
        }
    }

    @Override
    public void eventRelieve(TblAutoNaviMapRecord autoNaviMapRecord) {
        Map<String, String> param = new TreeMap<>();
        param.put("adcode", AutoNaviMapConfig.ADCODE);
        param.put("clientKey", AutoNaviMapConfig.CLIENTKEY);
        param.put("timestamp", DateUtils.getNowShortTimestamp().toString());
        param.put("sourceId", AutoNaviMapConfig.SOURCEID);
        param.put("id", autoNaviMapRecord.getId().toString());
        param.put("stateFlag", "2");
        param.put("type", autoNaviMapRecord.getEventType().toString());
        param.put("locType", "1");
        param.put("roadName", "G60沪昆高速");
        param.put("locs", autoNaviMapRecord.getLocs());
        param.put("direction", autoNaviMapRecord.getDirection());
        param.put("startDate", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, autoNaviMapRecord.getStartDate()));
        param.put("desc", autoNaviMapRecord.getEventDesc());
        String digest = "";
        for (String key : param.keySet()) {
            digest += param.get(key).toString();
        }
        digest = SignUtils.HmacSHA256(digest, AutoNaviMapConfig.SECRET);
        param.put("digest", digest);

        String r = null;
        try {
            r = EntityUtils.toString(HttpUtils.doGet(AutoNaviMapConfig.HOST, null, null, param).getEntity(), "utf-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (!StringUtils.isEmpty(r)) {
            if (r.startsWith("{")) {
                JSONObject ret = JSONObject.parseObject(r);
                if (ret.containsKey("code") && ret.getString("code").equals("0")) {
                    log.info(autoNaviMapRecord.getId() + "----高德地图事件解除成功----");
                } else {
                    log.error("高德地图推送事件失败，错误信息：" + ret.getString("errmsg"));
                    throw new ServiceException("高德地图推送事件失败，错误信息：" + ret.getString("errmsg"));
                }
            }
        }
    }
}
