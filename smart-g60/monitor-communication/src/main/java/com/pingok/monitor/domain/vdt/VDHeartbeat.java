package com.pingok.monitor.domain.vdt;

import com.ruoyi.common.core.utils.DateUtils;
import lombok.Data;

@Data
public class VDHeartbeat {
//    private Long deviceId;
//    private Integer status;
//    private String statusDesc;
//    private String time;
    private String detailsStatus;

    public VDHeartbeat(String detailsStatus) {
//        this.deviceId = deviceId;
//        this.status = status;
//        this.statusDesc = desc;
//        this.time = DateUtils.getTime();
        this.detailsStatus = detailsStatus;
    }
}
