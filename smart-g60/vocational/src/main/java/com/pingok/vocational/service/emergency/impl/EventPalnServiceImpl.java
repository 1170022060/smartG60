package com.pingok.vocational.service.emergency.impl;

import com.alibaba.fastjson.JSONArray;
import com.pingok.vocational.domain.emergency.TblEventPaln;
import com.pingok.vocational.domain.event.TblEventRecord;
import com.pingok.vocational.mapper.emergency.TblEventPalnMapper;
import com.pingok.vocational.mapper.event.TblEventRecordMapper;
import com.pingok.vocational.service.emergency.IEventPalnService;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.GeoResult;
import org.springframework.data.geo.GeoResults;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 应急预案 服务层处理
 *
 * @author ruoyi
 */
@Service
public class EventPalnServiceImpl implements IEventPalnService {

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblEventPalnMapper tblEventPalnMapper;
    @Autowired
    private TblEventRecordMapper tblEventRecordMapper;
    @Autowired
    private RedisService redisService;


    @Override
    public List<TblEventPaln> findByEventType(Long id) {
        List<TblEventPaln> list = new ArrayList<>();
        TblEventRecord eventRecord = tblEventRecordMapper.selectByPrimaryKey(id);
        String[] gps = null;
        if (eventRecord.getLocationInterval() != null) {
            gps = eventRecord.getLocationInterval().split(",");
        }
        String eventType = eventRecord.getEventType();
        if (eventType != null) {
            List<TblEventPaln> eventPalns = tblEventPalnMapper.findByEventType(eventType);
            JSONArray jsonArray;
            int size;
            for (TblEventPaln e : eventPalns) {
                if (!e.getEventType().isEmpty()) {
                    e.setEventTypeList(JSONArray.parseArray(e.getEventType()));
                }
                if (!e.getPlanFunction().isEmpty()) {
                    jsonArray = JSONArray.parseArray(e.getPlanFunction());
                    if (!jsonArray.isEmpty()) {
                        size = jsonArray.size();
                        for (int i = 0; i < size; i++) {
                            if (jsonArray.getJSONObject(i).containsKey("type")) {
                                switch (jsonArray.getJSONObject(i).getInteger("type")) {
                                    case 4:
                                        if (gps != null) {
                                            jsonArray.getJSONObject(i).put("ids", getGeoIds("qbb", gps, jsonArray.getJSONObject(i).getDouble("range")));
                                        }
                                        break;
                                    case 5:
                                        if (gps != null) {
                                            jsonArray.getJSONObject(i).put("ids", getGeoIds("xsb", gps, jsonArray.getJSONObject(i).getDouble("range")));
                                        }
                                    case 6:
                                        if (gps != null) {
                                            jsonArray.getJSONObject(i).put("ids", getGeoIds("gantry", gps, jsonArray.getJSONObject(i).getDouble("range")));
                                        }
                                        break;
                                }
                            }
                        }
                    }
                    e.setPlanFunctionList(jsonArray);
                }
                list.add(e);
            }
        }
        return list;
    }

    private List<Long> getGeoIds(String key, String[] gps, Double range) {
        GeoResults geoResults = redisService.geoRadius(key, Double.valueOf(gps[0]), Double.valueOf(gps[1]), range);
        List<GeoResult> geoResultLis = geoResults.getContent();
        List<Long> ids = new ArrayList<>();
        for (GeoResult g : geoResultLis) {
            ids.add(Long.valueOf(g.getContent().toString()));
        }
        return ids;
    }

    @Override
    public void disableOrEnable(Long id, Integer status) {
        TblEventPaln tblEventPaln = tblEventPalnMapper.selectByPrimaryKey(id);
        tblEventPaln.setStatus(status);
        tblEventPalnMapper.updateByPrimaryKey(tblEventPaln);
    }

    @Override
    public List<Map> selectEventPalnList(String planTitle) {
        return tblEventPalnMapper.selectEventPalnList(planTitle);
    }

    @Override
    public String checkEventPalnNameUnique(TblEventPaln tblEventPaln) {
        Long id = StringUtils.isNull(tblEventPaln.getId()) ? -1L : tblEventPaln.getId();
        Example example = new Example(TblEventPaln.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andNotEqualTo("id", id);
        criteria.andEqualTo("planTitle", tblEventPaln.getPlanTitle());
        if (tblEventPalnMapper.selectOneByExample(example) != null) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public TblEventPaln findById(Long id) {
        TblEventPaln tblEventPaln = tblEventPalnMapper.selectByPrimaryKey(id);
        if (tblEventPaln.getEventType() != null) {
            tblEventPaln.setEventTypeList(JSONArray.parseArray(tblEventPaln.getEventType()));
        }
        if (tblEventPaln.getPlanFunction() != null) {
            tblEventPaln.setPlanFunctionList(JSONArray.parseArray(tblEventPaln.getPlanFunction()));
        }
        return tblEventPaln;
    }

    @Override
    public int insert(TblEventPaln tblEventPaln) {
        tblEventPaln.setId(remoteIdProducerService.nextId());
        tblEventPaln.setCreateTime(DateUtils.getNowDate());
        tblEventPaln.setCreateUserId(SecurityUtils.getUserId());
        tblEventPaln.setStatus(1);
        if (tblEventPaln.getPlanFunctionList() != null && tblEventPaln.getPlanFunctionList().size() > 0) {
            tblEventPaln.setPlanFunction(tblEventPaln.getPlanFunctionList().toJSONString());
        }
        if (tblEventPaln.getEventTypeList() != null && tblEventPaln.getEventTypeList().size() > 0) {
            tblEventPaln.setEventType(tblEventPaln.getEventTypeList().toJSONString());
        }
        return tblEventPalnMapper.insert(tblEventPaln);
    }

    @Override
    public int update(TblEventPaln tblEventPaln) {
        tblEventPaln.setUpdateTime(DateUtils.getNowDate());
        tblEventPaln.setUpdateUserId(SecurityUtils.getUserId());
        if (tblEventPaln.getPlanFunctionList() != null && tblEventPaln.getPlanFunctionList().size() > 0) {
            tblEventPaln.setPlanFunction(tblEventPaln.getPlanFunctionList().toJSONString());
        } else {
            tblEventPaln.setPlanFunction(null);
        }
        if (tblEventPaln.getEventTypeList() != null && tblEventPaln.getEventTypeList().size() > 0) {
            tblEventPaln.setEventType(tblEventPaln.getEventTypeList().toJSONString());
        } else {
            tblEventPaln.setEventType(null);
        }
        return tblEventPalnMapper.updateByPrimaryKey(tblEventPaln);
    }
}
