package com.pingok.monitor.domain.lane.vo;

import com.pingok.monitor.domain.lane.LaneInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@Data
public class Tree {
    private String laneName;
    private List<LaneInfo> children;

    public Tree() {
        children = new ArrayList<>();
    }
}
