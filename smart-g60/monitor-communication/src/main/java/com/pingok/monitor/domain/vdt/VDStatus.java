package com.pingok.monitor.domain.vdt;

import lombok.Data;

import java.util.List;

/**
 * @author
 * @time 2022/6/27 19:28
 */
@Data
public class VDStatus {
    private String devId;   // WB1、VD1
    private int direction;  // 1-上行；2-下行
    private String pileNo;  // 桩号
    private String time;    // 采集时间
    private Integer totalVolume;        //总流量
    private Integer avgSpeed;           //平均车速
    private Integer avgOccupy;          //平均时间占有率
    private Integer avgVehTimeHeadway;  //平均车头时距
//    private List<VDComplexStatus> complexStatusList;    //复合检测单元
//    private List<VDFlowStatus> flowStatusList;          //流量检测单元

    public VDStatus(String dId, int dir, String pn) {
        devId = dId;
        direction = dir;
        pileNo = pn;
    }
}
