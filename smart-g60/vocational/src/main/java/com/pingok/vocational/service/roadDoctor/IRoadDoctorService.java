package com.pingok.vocational.service.roadDoctor;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IRoadDoctorService {
    List<Map> list(String questName,String pZhuangHao,Date startTime,Date endTime);
}
