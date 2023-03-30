package com.pingok.monitor.service.parkingLot.impl;

import com.pingok.monitor.mapper.parkingLot.ParkingLotMapper;
import com.pingok.monitor.service.parkingLot.IParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 停车场 服务层处理
 *
 * @author qiumin
 */
@Service
public class ParkingLotServiceImpl implements IParkingLotService {

    @Autowired
    private ParkingLotMapper parkingLotMapper;

    @Override
    public List<Map> flowStatistics() {
        return parkingLotMapper.flowStatistics();
    }

    @Override
    public List<Map> findByFieldNum(String fieldNum) {
        List<Map> list = parkingLotMapper.findByFieldNum(fieldNum);
//        for (Map m : list) {
//            m.put("parkingTimeout", parkingLotMapper.findByParkingId(Long.parseLong(String.valueOf(m.get("id")))));
//        }
        return list;
    }

    @Override
    public List<Map> getTimeOutVeh(Long id) {
        return parkingLotMapper.findByParkingId(id);
    }
}
