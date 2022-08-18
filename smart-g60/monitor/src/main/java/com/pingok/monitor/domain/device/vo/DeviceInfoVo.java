package com.pingok.monitor.domain.device.vo;

import com.pingok.monitor.domain.device.TblReleaseRecord;
import lombok.Data;

@Data
public class DeviceInfoVo {
    private Long id;
    private String deviceId;
    private String deviceName;
    private String deviceBrand;
    private String deviceModel;
    private String deviceIp;
    private Integer port;
    private Integer direction;
    private String stationBelong;
    private String pileNo;
    private String gps;
    private String time;
    private Integer status;
    private String statusDesc;
    private TblReleaseRecord tblReleaseRecord;
}
