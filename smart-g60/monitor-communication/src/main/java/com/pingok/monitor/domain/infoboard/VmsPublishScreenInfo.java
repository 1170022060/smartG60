package com.pingok.monitor.domain.infoboard;

import lombok.Data;

/** 情报板单屏发布内容
 * @author
 * @time 2022/5/2 8:51
 */
@Data
public class VmsPublishScreenInfo {
    private String text;
    private String font;
    private String fontColor;
    private String fontSize;
    private String picId;
    private String picType;
}
