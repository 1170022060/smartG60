package com.pingok.algorithmBeiJing.service.impl;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.algorithmBeiJing.domain.vo.RoadVo;
import com.pingok.algorithmBeiJing.mapper.GantryMapper;
import com.pingok.algorithmBeiJing.service.IRoadService;
import com.pingok.algorithmBeiJing.service.IRocketMqService;
import com.ruoyi.common.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        LocalDateTime start = LocalDateTimeUtil.of(Long.valueOf(startTime));
        LocalDateTime end = LocalDateTimeUtil.of(Long.valueOf(endTime));
        startTime = start.format(DateTimeFormatter.ISO_LOCAL_DATE);
        endTime = end.format(DateTimeFormatter.ISO_LOCAL_DATE);
        String year = String.valueOf(start.getYear());
        List<Map> list = gantryMapper.trajectoryData(year, plate, startTime, endTime);
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
        String year = DateUtils.getYear(DateUtils.parseDate(startTime));
        List<Map> gantrys = gantryMapper.gantrys();
        if (gantrys != null && !gantrys.isEmpty()) {
            JSONObject object = new JSONObject();
            object.put("gantry_ids", JSON.parseArray(JSON.toJSONString(gantrys)));

            JSONObject records = new JSONObject();
            JSONArray record;
            JSONArray array;
            JSONObject vehicle;
            List<Map> list;
            Integer coachOrTruck;
            Boolean special;
            for (Map g : gantrys) {
                list = gantryMapper.gantryTransactionLog(year, g.get("id").toString(), startTime, endTime);
                record = new JSONArray();
                for (Map m : list) {
                    array = new JSONArray();
                    array.add(m.get("vehiclePlate") != null ? m.get("vehiclePlate") : "");
                    array.add(m.get("transTime") != null ? m.get("transTime") : "");
                    array.add(m.get("lastGantryId") != null ? m.get("lastGantryId") : "");
                    array.add(m.get("lastGantryTime") != null ? m.get("lastGantryTime") : "");
                    array.add(m.get("fee") != null ? m.get("fee") : "");
                    vehicle = new JSONObject();
                    vehicle.put("plate_color", m.get("vehicleColor"));
                    switch (Integer.parseInt(m.get("vehicleType").toString())) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            coachOrTruck = 0;
                            special = false;
                            break;
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                            coachOrTruck = 1;
                            special = false;
                            break;
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 26:
                            coachOrTruck = 1;
                            special = true;
                            break;
                        default:
                            coachOrTruck = 0;
                            special = false;
                            break;
                    }
                    vehicle.put("coach_or_truck", coachOrTruck);
                    vehicle.put("special", special);
                    array.add(vehicle);
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
