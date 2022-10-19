package com.pingok.vocational.domain.parkingLot.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class AreaVo {
    private Integer areaId;
    private String areaName;
    private Integer dailyTotal;
    private Integer actualFlow;
    private Integer peakFlow;
    private Integer avgFlow;
    private Integer hourFlow;
    private Integer noMask;
//    private String deviceIds;
    ArrayList deviceIds = new ArrayList();
//    List<Integer> deviceIds = new Arrays();
    public AreaVo(Integer areaId, String areaName) {
        this.areaId = areaId;
        this.areaName = areaName;
    }

}
