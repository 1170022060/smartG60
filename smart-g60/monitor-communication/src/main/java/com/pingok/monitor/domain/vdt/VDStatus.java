package com.pingok.monitor.domain.vdt;

import lombok.Data;

import java.util.List;

/**
 * @author
 * @time 2022/6/27 19:28
 */
@Data
public class VDStatus {
    private String time;                                //采集时间
    private List<VDComplexStatus> complexStatusList;    //复合检测单元
    private List<VDFlowStatus> flowStatusList;          //流量检测单元
}
