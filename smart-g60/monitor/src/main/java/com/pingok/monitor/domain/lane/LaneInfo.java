package com.pingok.monitor.domain.lane;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Data
public class LaneInfo {
    private String laneName;
    private String laneHex;
//    private Integer status;
    List<Map> cameraArr;
}
