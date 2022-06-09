package com.pingok.monitor.domain.leise;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * @time 2022/5/24 10:30
 */
@Data
@Table(name = "tbl_leise_event")
public class TblLeiseEvent implements Serializable {
    /**
     * 事件id号码, ⽤于追溯
     */
    @Id
    private Long id;

    /**
     * 事件类型 (1: 超速 2:低速/拥堵 3:异常变道 4:异常停⻋ 5:⻋ 距过近 6:碰撞预警 7:其他异常事件(包含抛撒物, ⾏⼈等异 常事件)) 8:匝道并⼊碰撞预警
     */
    private Integer type;

    /**
     * 事件发⽣所在⻋道
     */
    private Integer lane;

    /**
     * 感知单元id号码
     */
    private String deviceId;

    /**
     * 车牌
     */
    private String licenseNum;

    /**
     * 上报时间(UTC时间)
     */
    private Date time;

    /**
     * 横向位置 (以感知安装点作为零点)
     */
    private Float positionX;

    /**
     * 纵向位置 (以感知安装点作为零点)
     */
    private Float positionY;

    /**
     * ⽬标纬度值(WGS84坐标)
     */
    private Float positionLat;

    /**
     * ⽬标经度值(WGS84坐标)
     */
    private Float positionLon;

    /**
     * 事件发⽣前后的录像
     */
    private String urlVideo;

    /**
     * 事件发⽣时的图片
     */
    private String urlPicture;

    private static final long serialVersionUID = 1L;
}
