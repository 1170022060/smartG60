package com.pingok.vocational.domain.infoboard;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VmsInfoByTypeList {

    private Integer id;

    private String label;

    private Integer level;

    private Integer deviceCnt;

    private Long faultCnt;

    private List<VmsInfoByType> vmsList;

    public VmsInfoByTypeList() {
        level = 1;
        vmsList = new ArrayList<>();
    }
}
