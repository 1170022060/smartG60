package com.pingok.external.service.roadDoctor;

import com.pingok.external.domain.roadDoctor.vo.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IRoadDoctorService {
    void updateDisease();

    List<Map> list(String questName, String pZhuangHao, Date startTime, Date endTime);
    int push(Long id);
    LoginVo login();
    String addDisease(Long id,String token);
    void addDiseaseProc(StatusVo statusVo);
    void addDiseasePic(PictureVo pictureVo);
    List<String> getBackOrderNums(String token);
}
