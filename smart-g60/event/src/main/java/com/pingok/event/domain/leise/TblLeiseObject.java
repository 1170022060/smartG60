package com.pingok.event.domain.leise;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * @time 2022/5/24 13:31
 */
@Table(name="TBL_LEISE_OBJECT")
@Data
public class TblLeiseObject implements Serializable {
    /**
     * 物体ID号码, ⽤于追踪
     */
    @Id
    private Long id;

    /**
     * 物体类型 (1: ⻋辆)
     */
    private Integer type;

    /**
     * 车牌
     */
    private String licenseNum;

    /**
     * 纵向速度（km/h）
     */
    private Float speedY;

    /**
     * 横向速度（km/h）
     */
    private Float speedX;

    /**
     * 纵向位置 (以感知安装点作为零点，m)
     */
    private Float positionY;

    /**
     * 横向位置 (以感知安装点作为零点，m)
     */
    private Float positionX;

    /**
     * ⽬标纬度值(WGS84坐标)
     */
    private Float positionLat;

    /**
     * ⽬标经度值(WGS84坐标)
     */
    private Float positionLon;

    /**
     * ⻋道 (最左侧为1⻋道, 往右依次递增)
     */
    private Integer lane;

    /**
     * 上报时间(UTC时间)
     */
    private Date time;

    private static final long serialVersionUID = 1L;
}

