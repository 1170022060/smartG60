package com.pingok.datacenter.domain.roster.rescue.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class RescueVo implements Serializable {
    private Date appointTime;
    private String appointType;
    private String cardId;
    private Integer checkType;
    private Integer dataType;
    private Float discount;
    private Integer discountAmount;
    private String discountInfo;
    private Integer discountQuota;
    private Integer discountType;
    private String enStation;
    private Date endTime;
    private String exStation;
    private Integer handleType;
    private String id;
    private Integer laneHandle;
    private Integer operation;
    private String provinceIds;
    private Date startTime;
    private String vehicleId;
    private Integer vehicleIdentifyType;
    private String vehicleSign;
}
