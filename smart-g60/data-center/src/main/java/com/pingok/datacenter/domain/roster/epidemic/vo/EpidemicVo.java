package com.pingok.datacenter.domain.roster.epidemic.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class EpidemicVo {
    /** 收费站编码 */
    private String stationId;

    /** 收费站Hex码 */
    private String stationHex;

    /** 收费站名称 */
    private String stationName;

    /** 所在地级市 */
    private String regionName;

    /** 生效时间 */
    private Date effective;
}
