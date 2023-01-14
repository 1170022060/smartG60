package com.pingok.vocational.service.emergency.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.vocational.domain.device.TblDeviceInfo;
import com.pingok.vocational.domain.device.TblGantryInfo;
import com.pingok.vocational.domain.emergency.TblEventPaln;
import com.pingok.vocational.domain.event.TblEventRecord;
import com.pingok.vocational.mapper.emergency.TblEmergencyGroupMapper;
import com.pingok.vocational.mapper.emergency.TblEventPalnMapper;
import com.pingok.vocational.mapper.event.TblEventRecordMapper;
import com.pingok.vocational.service.device.IGantryInfoService;
import com.pingok.vocational.service.device.TblDeviceInfoService;
import com.pingok.vocational.service.emergency.IEventPalnService;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.exception.ServiceException;
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

import java.util.*;
import java.util.regex.Pattern;

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

    @Autowired
    private TblDeviceInfoService tblDeviceInfoService;

    @Autowired
    private IGantryInfoService iGantryInfoService;

    @Autowired
    private TblEmergencyGroupMapper tblEmergencyGroupMapper;

    @Override
    public JSONArray deviceList(Long eventId, Long planId, Integer type) {
        JSONArray list = new JSONArray();
        TblEventRecord eventRecord = tblEventRecordMapper.selectByPrimaryKey(eventId);
        if (eventRecord == null) {
            throw new ServiceException("事件ID错误");
        }
        String eventPileNo = eventRecord.getPileNo();
        TblEventPaln eventPaln = tblEventPalnMapper.selectByPrimaryKey(planId);
        if (eventPaln == null) {
            throw new ServiceException("预案ID错误");
        }
        if (StringUtils.isNull(eventPaln.getPlanFunction())) {
            return null;
        }
        JSONArray planFunction = JSON.parseArray(eventPaln.getPlanFunction());
        Integer range = 0;
        for (int i = 0; i < planFunction.size(); i++) {
            if (type == planFunction.getJSONObject(i).getInteger("type") && planFunction.getJSONObject(i).containsKey("range")) {
                range = planFunction.getJSONObject(i).getInteger("range") * 1000;
            }
        }
        Integer deviceType = null;
        List<TblGantryInfo> gantryInfos = new ArrayList<>();
        List<TblDeviceInfo> deviceInfos = new ArrayList<>();
        switch (type) {
            case 4:
            case 5:
                deviceType = 9;
                deviceInfos = tblDeviceInfoService.selectByDeviceType(deviceType);
                break;
            case 6:
                gantryInfos = iGantryInfoService.selectAll();
                break;
            case 7:
                deviceType = 12;
                deviceInfos = tblDeviceInfoService.selectByDeviceType(deviceType);
                break;
        }
        JSONObject info;
        if (StringUtils.isNull(eventPileNo)) {
            for (TblDeviceInfo i : deviceInfos) {
                info = new JSONObject();
                info.put("deviceId", i.getDeviceId());
                info.put("deviceName", i.getDeviceName());
                info.put("pileNo", i.getPileNo());
                info.put("direction", i.getDirection() == 1 ? "上行" : "下行");
                list.add(info);
            }
            for (TblGantryInfo i : gantryInfos) {
                info = new JSONObject();
                info.put("deviceId", i.getDeviceId());
                info.put("deviceName", i.getDeviceName());
                info.put("pileNo", i.getPileNo());
                info.put("direction", i.getDirection() == 1 ? "上行" : "下行");
                list.add(info);
            }
        } else {
            Integer position = Integer.parseInt(Pattern.compile("[^0-9]").matcher(eventPileNo).replaceAll("").trim());
            Integer pileNo = 0;
            for (TblDeviceInfo i : deviceInfos) {
                pileNo = Integer.parseInt(Pattern.compile("[^0-9]").matcher(i.getPileNo()).replaceAll("").trim());
                switch (eventRecord.getDirection()) {
                    case "上行":
                    case "1":
                        if ((position - pileNo) >= 0 && (position - pileNo) <= range) {
                            info = new JSONObject();
                            info.put("deviceId", i.getDeviceId());
                            info.put("deviceName", i.getDeviceName());
                            info.put("pileNo", i.getPileNo());
                            info.put("direction", i.getDirection() == 1 ? "上行" : "下行");
                            list.add(info);
                        }
                        break;
                    case "下行":
                    case "2":
                        if ((position - pileNo) <= 0 && (position - pileNo) <= -range) {
                            info = new JSONObject();
                            info.put("deviceId", i.getDeviceId());
                            info.put("deviceName", i.getDeviceName());
                            info.put("pileNo", i.getPileNo());
                            info.put("direction", i.getDirection() == 1 ? "上行" : "下行");
                            list.add(info);
                        }
                        break;
                    case "双向":
                    case "3":
                        if (Math.abs(position - pileNo) <= range) {
                            info = new JSONObject();
                            info.put("deviceId", i.getDeviceId());
                            info.put("deviceName", i.getDeviceName());
                            info.put("pileNo", i.getPileNo());
                            info.put("direction", i.getDirection() == 1 ? "上行" : "下行");
                            list.add(info);
                        }
                        break;
                }
            }
            for (TblGantryInfo i : gantryInfos) {
                pileNo = Integer.parseInt(Pattern.compile("[^0-9]").matcher(i.getPileNo()).replaceAll("").trim());
                switch (eventRecord.getDirection()) {
                    case "上行":
                    case "1":
                        if ((position - pileNo) <= range) {
                            info = new JSONObject();
                            info.put("deviceId", i.getDeviceId());
                            info.put("deviceName", i.getDeviceName());
                            info.put("pileNo", i.getPileNo());
                            info.put("direction", i.getDirection() == 1 ? "上行" : "下行");
                            list.add(info);
                        }
                        break;
                    case "下行":
                    case "2":
                        if ((position - pileNo) <= -range) {
                            info = new JSONObject();
                            info.put("deviceId", i.getDeviceId());
                            info.put("deviceName", i.getDeviceName());
                            info.put("pileNo", i.getPileNo());
                            info.put("direction", i.getDirection() == 1 ? "上行" : "下行");
                            list.add(info);
                        }
                        break;
                    case "双向":
                    case "3":
                        if (Math.abs(position - pileNo) <= range) {
                            info = new JSONObject();
                            info.put("deviceId", i.getDeviceId());
                            info.put("deviceName", i.getDeviceName());
                            info.put("pileNo", i.getPileNo());
                            info.put("direction", i.getDirection() == 1 ? "上行" : "下行");
                            list.add(info);
                        }
                        break;
                }

            }
        }
        return list;
    }

    @Override
    public List<TblEventPaln> findByEventType(Long id) {
        List<TblEventPaln> list = new ArrayList<>();
        TblEventRecord eventRecord = tblEventRecordMapper.selectByPrimaryKey(id);
        String[] gps = null;
        if (eventRecord.getLocationInterval() != null) {
            gps = eventRecord.getLocationInterval().replace("[", "").replace("]", "").split(",");
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
                                        break;
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

    @Override
    public List<Map> selectPlanGroup(Integer suppliesType) {
        if (suppliesType == 4) {
            List<Map> groupArr = tblEventPalnMapper.selectGroup();

            List<Map> userArr = tblEmergencyGroupMapper.selectAllDeptUser();
            for (int i = 0; i < groupArr.size(); i++) {
                Iterator groupIter = groupArr.get(i).keySet().iterator();
                HashMap<String, String> map = (HashMap<String, String>) groupArr.get(i);
                while (groupIter.hasNext()) {
                    String groupArrKey = (String) groupIter.next();
                    String str = groupArr.get(i).get(groupArrKey).toString().replace("[", "").replace("]", "");
                    String[] strArr = str.split(",");
                    for (int n = 0; n < userArr.size(); n++) {
                        Iterator userIter = userArr.get(n).keySet().iterator();
                        while (userIter.hasNext()) {
                            String userArrKey = (String) userIter.next();
                            String userArrValue = userArr.get(n).get(userArrKey).toString();
                            if (groupArrKey.equals("groupLeader")) {
                                for (int j = 0; j < strArr.length; j++) {
                                    if (userArrKey.equals("userId")) {
                                        String temp = strArr[j];
                                        if (userArrValue.equals(temp)) {
                                            strArr[j] = (String) userArr.get(n).get("userName");
                                        }
                                    }
                                }
                                map.put("groupLeader", Arrays.toString(strArr).replace("[", "").replace("]", ""));
                            }
                            if (groupArrKey.equals("groupLeaderDep")) {
                                for (int j = 0; j < strArr.length; j++) {
                                    if (userArrKey.equals("userId")) {
                                        String temp = strArr[j];
                                        if (userArrValue.equals(temp)) {
                                            strArr[j] = (String) userArr.get(n).get("userName");
                                        }
                                    }
                                }
                                map.put("groupLeaderDep", Arrays.toString(strArr).replace("[", "").replace("]", ""));
                            }
                            if (groupArrKey.equals("groupMembers")) {
                                for (int j = 0; j < strArr.length; j++) {
                                    if (userArrKey.equals("userId")) {
                                        String temp = strArr[j];
                                        if (userArrValue.equals(temp)) {
                                            strArr[j] = (String) userArr.get(n).get("userName");
                                        }
                                    }
                                }
                                map.put("groupMembers", Arrays.toString(strArr).replace("[", "").replace("]", ""));
                            }
                        }
                    }
                }
                groupArr.set(i, map);
            }
            return groupArr;
        } else {
            return null;
        }
    }
}
