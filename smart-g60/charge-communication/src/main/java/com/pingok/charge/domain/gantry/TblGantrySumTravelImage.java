package com.pingok.charge.domain.gantry;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * tbl_gantry_sum_travelimage
 * 
 * @author bianj
 * @version 1.0.0 2022-05-19
 */
@Data
@Table(name="tbl_gantry_sum_travelimage")
public class TblGantrySumTravelImage implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1L;

    /** id */
    @Id
    private String collectId;


    /** gantryid */
    private String gantryid;

    /** YYYYMMDD */
    private String collectDate;

    /** collectHourBatch */
    private String collectHourBatch;

    /** vehicleDataCount */
    private Integer vehicleDataCount;

    /** vehiclePicCount */
    private Integer vehiclePicCount;


    /** computerOrder */
    private Integer computerOrder;


}