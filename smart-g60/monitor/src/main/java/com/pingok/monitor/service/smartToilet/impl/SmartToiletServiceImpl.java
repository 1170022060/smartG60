package com.pingok.monitor.service.smartToilet.impl;

import com.pingok.monitor.mapper.smartToilet.SmartToiletMapper;
import com.pingok.monitor.service.smartToilet.ISmartToiletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 智慧厕所 服务层处理
 *
 * @author qiumin
 */
@Service
public class SmartToiletServiceImpl implements ISmartToiletService {

    @Autowired
    private SmartToiletMapper smartToiletMapper;

    @Override
    public List<Map> findByFieldNum(String fieldNum,Date workDate) {
        List<Map> list = smartToiletMapper.findToiletInfoByFieldNum(fieldNum);
        for (Map m : list) {
            m.put("toiletHealth", smartToiletMapper.findToiletHealthBySerId(Long.parseLong(String.valueOf(m.get("id")))));
            m.put("toiletCubicle", smartToiletMapper.findToiletCubicleBySerId(Long.parseLong(String.valueOf(m.get("id")))));
            m.put("toiletSchedule",smartToiletMapper.getTodaySchedule(Long.parseLong(String.valueOf(m.get("id"))),workDate));
        }
        return list;
    }
}
