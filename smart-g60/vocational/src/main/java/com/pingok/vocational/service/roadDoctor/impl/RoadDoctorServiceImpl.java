package com.pingok.vocational.service.roadDoctor.impl;

import com.pingok.vocational.mapper.roadDoctor.TblRoadDoctorMapper;
import com.pingok.vocational.service.roadDoctor.IRoadDoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RoadDoctorServiceImpl implements IRoadDoctorService {

    @Autowired
    private TblRoadDoctorMapper tblRoadDoctorMapper;

    @Override
    public List<Map> list(String questName, String pZhuangHao, Date startTime, Date endTime) {
        return tblRoadDoctorMapper.list(questName, pZhuangHao, startTime, endTime);
    }
}
