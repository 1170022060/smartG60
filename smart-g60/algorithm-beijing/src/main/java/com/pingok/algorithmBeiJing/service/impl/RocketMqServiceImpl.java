package com.pingok.algorithmBeiJing.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.algorithmBeiJing.domain.vo.RoadVo;
import com.pingok.algorithmBeiJing.mapper.GantryMapper;
import com.pingok.algorithmBeiJing.service.IRoadService;
import com.pingok.algorithmBeiJing.service.IRocketMqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @time 2022/5/20 12:23
 */
@Slf4j
@Service
public class RocketMqServiceImpl implements IRocketMqService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Autowired
    private IRoadService iRoadService;

    @Autowired
    private GantryMapper gantryMapper;

    @Override
    public void trajectoryData(String plate, String instanceId, String startTime, String endTime) {
        List<Map> list = gantryMapper.trajectoryData(plate, startTime, endTime);
        if (list != null && list.size() > 0) {
            JSONObject object = new JSONObject();
            object.put("plate", plate);
            object.put("start_time", startTime);
            object.put("end_time", endTime);
            JSONArray trajectory = new JSONArray();
            JSONArray array;
            for (Map m : list) {
                array = new JSONArray();
                array.add(m.get("gantryId") != null ? m.get("gantryId") : "");
                array.add(m.get("transTime") != null ? m.get("transTime") : "");
                array.add(m.get("lastGantryId") != null ? m.get("lastGantryId") : "");
                array.add(m.get("lastGantryTime") != null ? m.get("lastGantryTime") : "");
                array.add(m.get("gantryName") != null ? m.get("gantryName") : "");
                array.add(m.get("lastGantryName") != null ? m.get("lastGantryName") : "");
                trajectory.add(array);
            }
            object.put("trajectory", trajectory);
            rocketMQTemplate.syncSend("Trajectory_Data:" + instanceId, object, 10000);
        }
    }

    @Override
    public void gantryTransactionLog(String startTime, String endTime) {
        List<Map> gantrys = gantryMapper.gantrys(startTime, endTime);
        if (gantrys != null && gantrys.size() > 0) {
            JSONObject object = new JSONObject();
            object.put("gantry_ids", JSON.parseArray(JSON.toJSONString(gantrys)));

            JSONObject records = new JSONObject();
            JSONArray record;
            JSONArray array;
            List<Map> list;
            for (Map g : gantrys) {
                list = gantryMapper.gantryTransactionLog(g.get("id").toString(), startTime, endTime);
                record = new JSONArray();
                for (Map m : list) {
                    array = new JSONArray();
                    array.add(m.get("vehiclePlate") != null ? m.get("vehiclePlate") : "");
                    array.add(m.get("transTime") != null ? m.get("transTime") : "");
                    array.add(m.get("lastGantryId") != null ? m.get("lastGantryId") : "");
                    array.add(m.get("lastGantryTime") != null ? m.get("lastGantryTime") : "");
                    record.add(array);
                }
                records.put(g.get("id").toString(), record);
            }
            object.put("records", records);
            rocketMQTemplate.syncSend("Gantry_Transaction_Log:source", object, 10000);
        }
    }

    @Override
    public void sendRoads() {
        RoadVo roadVo = new RoadVo();
        roadVo.setRoads(iRoadService.selectAll());
        roadVo.setGantry(iRoadService.selectAllNode());
        JSONObject object = JSON.parseObject(JSON.toJSONString(roadVo));
        rocketMQTemplate.syncSend("Road_Network:source", object, 10000);
    }
}
