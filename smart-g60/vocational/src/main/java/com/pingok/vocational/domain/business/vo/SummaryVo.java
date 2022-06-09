package com.pingok.vocational.domain.business.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SummaryVo {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enEndTime;

    private String enStationId;

    private Integer enLaneType;

    private String enVehPlate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exEndTime;

    private String exStationId;

    private Integer exLaneType;

    private String exVehPlate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enWorkDate;

    private String passId;

    private String enGid;

    private Integer enPassType;

    private Integer enShift;

    private String enCardId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exWorkDate;

    private String exGid;

    private Integer exPassType;

    private Integer exShift;

    private String exCardId;

    private String payWay;
}
