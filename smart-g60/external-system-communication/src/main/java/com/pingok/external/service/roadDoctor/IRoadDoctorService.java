package com.pingok.external.service.roadDoctor;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IRoadDoctorService {
    void updateDisease();

    List<Map> list(String questName, String pZhuangHao, Date startTime, Date endTime);

}
