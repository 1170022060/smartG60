package com.pingok.monitor.domain.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Record {
    private List<Map> gantryRecord;
    private List<Map> sectionRecord;
}
