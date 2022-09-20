package com.pingok.charge.domain.gantry.vo;

import lombok.Data;

@Data
public class GantryV2X {

    private String GantryID;            //收费门架ID
    private Integer OBUDelay;           //OBU延迟播报时间（秒）
    private String stakeNum;            //桩号
    private Integer direction;          //方向：1-上行；2-下行
    private Integer broadcastRange;     //向前播报范围
    private String eventType;           //事件播报类型
    private Long eventId;               //事件类型编号
    private Integer eventPosition;      //事件位置
    private Integer eventDiscount;      //事件距离
    private String eventInfo;           //事件消息内容
    private String reportBeginTime;     //播报开始时间，格式：yyyy-MM-ddThh:mm:ss
    private String reportEndTime;       //播报结束时间，格式：yyyy-MM-ddThh:mm:ss
    private String cryptoGraphicDigest; //消息的简短说明

    public GantryV2X() {
        broadcastRange = 0;
    }
}
