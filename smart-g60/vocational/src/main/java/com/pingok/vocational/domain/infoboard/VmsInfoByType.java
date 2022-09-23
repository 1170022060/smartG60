package com.pingok.vocational.domain.infoboard;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Date;

@Getter
public class VmsInfoByType {

    private Long id;

    private String deviceName;

    private String deviceBrand;

    private String deviceModel;

    private String techPara;

    private String deviceIp;

    private Integer port;

    private String pileNo;

    private Integer direction;

    private String gps;

    private Integer deviceStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date statusTime;

    private String statusDesc;

    private String statusDetails;

    private Integer infoType;

    private String typeface;

    private String typefaceSize;

    private String color;

    private String pictureType;
}
