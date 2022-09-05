package com.pingok.external.domain.roadDoctor.vo;

import lombok.Data;
/**
 * 病害数据格式
 */
@Data
public class DiseaseData {
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
