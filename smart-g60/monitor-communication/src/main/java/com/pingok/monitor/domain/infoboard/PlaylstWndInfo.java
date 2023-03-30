package com.pingok.monitor.domain.infoboard;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlaylstWndInfo {
    private Integer winX;
    private Integer winY;
    private Integer winW;
    private Integer winH;
    private Integer type;   //0-图片，1-文字
    private List<VmsPubInfo> items;

    public PlaylstWndInfo() {
        items = new ArrayList<>();
    }
}
