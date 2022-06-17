package com.pingok.external.service.amap.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.config.AutoNaviMapConfig;
import com.pingok.external.domain.amap.TblAutoNaviMapRecord;
import com.pingok.external.mapper.amap.TblAutoNaviMapRecordMapper;
import com.pingok.external.service.amap.IAutoNaviMapService;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.SignUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
public class AutoNaviMapServiceImpl implements IAutoNaviMapService {


    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblAutoNaviMapRecordMapper tblAutoNaviMapRecordMapper;

    @Override
    public void eventPublish(TblAutoNaviMapRecord autoNaviMapRecord) {
        Map<String, Object> param = new LinkedHashMap<>();
        param.put("adcode", AutoNaviMapConfig.ADCODE);
        param.put("clientKey", AutoNaviMapConfig.CLIENTKEY);
        param.put("timestamp", DateUtils.getNowShortTimestamp());
        param.put("sourceId", AutoNaviMapConfig.SOURCEID);
        param.put("id", autoNaviMapRecord.getId());
        param.put("stateFlag", 0);
        param.put("type", autoNaviMapRecord.getType());
        param.put("locType", 2);
        param.put("roadName", "G60沪杭高速");
        param.put("locs", autoNaviMapRecord.getLocs());
        param.put("startDate", autoNaviMapRecord.getStartDate());
        param.put("desc", autoNaviMapRecord.getDesc());
        param.put("callback", AutoNaviMapConfig.CALLBACK + "amap/callback");
        String digest = "";
        for (String key : param.keySet()) {
            digest += param.get(key).toString();
        }
        digest = SignUtils.HmacSHA256(digest, AutoNaviMapConfig.SECRET);
        param.put("digest", digest);

        String r = HttpUtil.get(AutoNaviMapConfig.HOST, param);
        if (!StringUtils.isEmpty(r)) {
            if (r.startsWith("{")) {
                JSONObject ret = JSONObject.parseObject(r);
                if (ret.containsKey("code") && ret.getString("code").equals("0")) {
                    Example example = new Example(TblAutoNaviMapRecord.class);
                    example.createCriteria()
                            .andEqualTo("id", autoNaviMapRecord.getId());
                    autoNaviMapRecord = tblAutoNaviMapRecordMapper.selectOneByExample(example);
                    if (autoNaviMapRecord == null) {
                        autoNaviMapRecord = JSON.parseObject(param.toString(), TblAutoNaviMapRecord.class);
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
    public void eventRelieve(Long id) {
        Map<String, Object> param = new LinkedHashMap<>();
        param.put("adcode", AutoNaviMapConfig.ADCODE);
        param.put("clientKey", AutoNaviMapConfig.CLIENTKEY);
        param.put("timestamp", DateUtils.getNowShortTimestamp());
        param.put("sourceId", AutoNaviMapConfig.SOURCEID);
        param.put("id", id);
        param.put("stateFlag", 2);
        String digest = "";
        for (String key : param.keySet()) {
            digest += param.get(key).toString();
        }
        digest = SignUtils.HmacSHA256(digest, AutoNaviMapConfig.SECRET);
        param.put("digest", digest);

        String r = HttpUtil.get(AutoNaviMapConfig.HOST, param);
        if (!StringUtils.isEmpty(r)) {
            if (r.startsWith("{")) {
                JSONObject ret = JSONObject.parseObject(r);
                if (ret.containsKey("code") && ret.getString("code").equals("0")) {
                    log.info(id + "----高德地图事件解除成功----");
                } else {
                    log.error("高德地图推送事件失败，错误信息：" + ret.getString("errmsg"));
                    throw new ServiceException("高德地图推送事件失败，错误信息：" + ret.getString("errmsg"));
                }
            }
        }
    }
}
