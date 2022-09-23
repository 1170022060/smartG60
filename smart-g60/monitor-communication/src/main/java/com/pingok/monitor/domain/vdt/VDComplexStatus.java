package com.pingok.monitor.domain.vdt;

import lombok.Data;

import java.util.List;

/**
 * @author
 * @time 2022/6/27 9:48
 */
@Data
public class VDComplexStatus {
    private Integer totalVolume;        //总流量
    private List<Integer> volumes;      //车型流量
    private List<Integer> speeds;       //车型速度
    private Integer avgSpeed;           //平均车速
    private Integer avgOccupy;          //平均时间占有率
    private Integer avgVehTimeHeadway;  //平均车头时距
}
