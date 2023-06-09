package com.pingok.datacenter.domain.gantry;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * @time 2022/5/20 17:57
 */
@Data
@Table(name="TBL_GANTRY_SUM_TRAVELIMAGE")
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
