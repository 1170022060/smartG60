package com.pingok.external.domain.maintain.vo;

import lombok.Data;

import javax.persistence.Table;

/**
 * 病害数据格式
 */
@Data
public class DataVo {

    private String token;
    private String district;
    private String belongcom;
    private String reportby;
    private String facilitytype;
    private String sid;
    private String facilityname;
    private String startpoint;
    private String endpoint;
    private String referencestandard;
    private String address;
    private String direction;
    private String problemparts;
    private String problemtype;
    private String plevel;
    private Long reportdate;
    private Double lng;
    private Double lat;
    private String status;
    private Double mileage;
    private String lane;
    private String checktype;
    private String remark;
    private String picurl;
}
