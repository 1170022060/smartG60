package com.pingok.monitor.domain.infoboard;

import lombok.Data;

@Data
public class SansiParkingPubInfo {
    private String devIp;
    private Integer devPos;         //设备位置：1-一级诱导屏；2-二级A区；3-二级BC区
    private Integer infoType;       //发布类型：1-数字（默认）；2-文字（暂不实现）
    //一级诱导屏
    private String text1;
    private Integer color1;         //颜色（0123-黑红绿黄）
    //二级诱导屏A区
    private String text2A_huoA;     //货A
    private Integer color2A_huoA;
    private String text2A_huoB;     //货B
    private Integer color2A_huoB;
    private String text2A_ke;       //客车
    private Integer color2A_ke;
    //二级诱导屏B、C区
    private String text2BC_huoB;    //货B
    private Integer color2BC_huoB;
    private String text2BC_ke;      //客车
    private Integer color2BC_ke;

    public SansiParkingPubInfo() {

    }
}
