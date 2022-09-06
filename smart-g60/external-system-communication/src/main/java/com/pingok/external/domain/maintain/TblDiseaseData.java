package com.pingok.external.domain.maintain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 病害数据格式表
 */
@Data
@Table(name = "TBL_DISEASE_DATA")
public class TblDiseaseData {
    @Id
    private Long id;

    /**
     * 病害ID
     */
    private Long diseaseId;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date uploadTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
