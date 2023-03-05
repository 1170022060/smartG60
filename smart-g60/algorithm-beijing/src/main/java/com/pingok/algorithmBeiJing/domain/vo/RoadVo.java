package com.pingok.algorithmBeiJing.domain.vo;

import com.pingok.algorithmBeiJing.domain.TblRoadInfo;
import com.pingok.algorithmBeiJing.domain.TblRoadNodesInfo;
import lombok.Data;

import java.util.List;

@Data
public class RoadVo {
    private List<TblRoadNodesInfo> gantry;
    private List<TblRoadInfo> roads;
}
