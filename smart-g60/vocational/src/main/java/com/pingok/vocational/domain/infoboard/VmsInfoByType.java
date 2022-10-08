package com.pingok.vocational.domain.infoboard;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class VmsInfoByType {

    private Long id;

    private String deviceName;

    private String deviceBrand;

    private String deviceModel;

    private String techPara;

    private String deviceIp;

    private Integer port;

    private Integer slaveId;

    private String pileNo;

    private Integer direction;

    private String gps;

    private String protocol;

    private Integer width;

    private Integer high;

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

    private String recent5;

    private String publishContent;

    private String cameraId;
}
