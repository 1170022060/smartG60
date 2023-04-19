package com.pingok.vod.domain;

import com.ruoyi.common.core.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

@Data
public class DeviceDto implements Serializable {
    /**
     * 路段id
     */
    private Integer roadId;
    /**
     * 桩号
     */
    private String pileNo;
    /**
     * 设备id
     */
    private Long id;
    /**
     * 视频地址
     */
    private String url;
    /** 方向：1.上行 2.下行 */

    private Byte direction;
}
