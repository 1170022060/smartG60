package com.pingok.vocational.domain.parkingLot.vo;

import lombok.Data;

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
    public AreaVo(Integer areaId, String areaName) {
        this.areaId = areaId;
        this.areaName = areaName;
    }
}
