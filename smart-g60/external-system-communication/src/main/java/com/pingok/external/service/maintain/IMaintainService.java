package com.pingok.external.service.maintain;

import com.pingok.external.domain.maintain.vo.DiseaseDataVo;

import java.util.List;

public interface IMaintainService {
    void push(DiseaseDataVo diseaseDataVo);
    void addDiseaseProc(Long id,String status,String note);
    void addDiseasePic(Long id,String picType,String fileType);
    List<String> getBackOrderNums();
}
