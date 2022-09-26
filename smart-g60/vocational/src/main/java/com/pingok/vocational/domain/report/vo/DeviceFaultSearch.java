package com.pingok.vocational.domain.report.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class DeviceFaultSearch {
    private Long id;
    private String faultType;
    private String faultId;
    private String faultDescription;
    private Integer registerType;
    private Integer status;
    private String registerTypeDesc;
    private String statusDesc;
    private String remark;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date faultTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date handleTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String handleUser;
    private String createUser;
    private String updateUser;
    private String deviceNum;
    private String deviceName;
    private String deviceId;
    private String deviceCategory;
    private String pileNo;
}
