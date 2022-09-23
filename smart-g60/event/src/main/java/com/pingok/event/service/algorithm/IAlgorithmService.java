package com.pingok.event.service.algorithm;

import com.pingok.event.domain.algorithm.LaneAvgSpeed;

import java.util.List;

public interface IAlgorithmService {
    List<LaneAvgSpeed> getLaneAvgSpeed(String mileage, String laneNo, Integer dir);
}
