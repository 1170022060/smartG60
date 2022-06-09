package com.pingok.monitor.domain.lane.vo;

import com.pingok.monitor.domain.lane.TblLaneStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author qiumin
 * @version 1.0.0 2022-04-12
 */
@Data
public class LaneEnum implements Serializable {

    private List<Map> extLane;
    private List<Map> enLane;

}