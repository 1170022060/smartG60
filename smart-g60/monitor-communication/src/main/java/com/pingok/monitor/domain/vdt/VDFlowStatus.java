package com.pingok.monitor.domain.vdt;

import lombok.Data;

/**
 * @author
 * @time 2022/6/30 16:11
 */
@Data
public class VDFlowStatus {
    private Integer totalVolume;        //总流量
    private Integer avgSpeed;           //平均车速
    private Integer avgOccupy;          //平均时间占有率
    private Integer avgVehTimeHeadway;  //平均车头时距
}
