package com.pingok.monitor.domain.infoboard;

import lombok.Data;

/**
 * @author
 * @time 2022/7/5 10:16
 */
@Data
public class VmsPubInfo {
    private Integer id;
    private String content;
    private String typeface;
    private String textSize;
    private String textColor;
    private String picId;

    public VmsPubInfo() {}
    public VmsPubInfo(String text, String font, String size, String color) {
        content = text;
        typeface = font;
        textSize = size;
        color = textColor;
    }
    public VmsPubInfo(String pId) {
        picId = pId;
    }
}