package com.pingok.station.domain.suspectList.vo;

import lombok.Data;

import java.util.Date;

@Data
public class SuspectVo {

    /**
     * 车牌
     */
    private String vehicleId;

    /**
     * 原因
     */
    private String reason;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 生效时间
     */
    private Date effective;

    /**
     * 版本号
     */
    private String version;
}
