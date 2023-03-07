package com.pingok.monitor.domain.infoboard;

import lombok.Data;
import com.sansi.playlist.entry.*;

@Data
public class SanSiInfo {

    private String deviceId; //设备ID
    private Integer fcmsVersion;//协议版本： 1 旧 fcms 协议（对外 8 条） 2 新协议（暂时不支持）
    private String ipAddr;//设备 IP
    private Integer ipPort;//端口
    private Integer blockSize;//通信协议块大小(厂家提供)

    private Integer inAnimation;//FCMS 设备目前支持的特效值 ：0立即显示 、3上移 、4下移 、5左移 、6右移 、100清屏展示
    private Integer inAnimationSpeed;//入特效速度

    //显示屏显示色彩由多方面决定：首先确认显示屏支持的基色（单色屏、双色屏、还是全彩屏等）；
    // 再次颜色显示有以上参数（red/green/blue/alpha/amber）值共同起作用。
    private BaseColour baseColour;

    private String textPath;//文本内容，需要显示的文本内容
    private String fontSize;//字体大小，格式：xx,xx
    private String fontName;//字体名称，FCMS 设备取值范围：h,k,s,f 其它字体不支持
    private BaseColour fontColour;//字体颜色

    //此时间是单次播放总时间，但显示屏播放时是循环播放，所以在未切换播放表时显示屏会一直播放相应的播放项。
    private Integer totalTime;

    private Integer posX;//播放项显示点坐标 x，默认：0
    private Integer posY;//播放项显示点坐标 y，默认：0

    private String playName;//播放项名称
}


