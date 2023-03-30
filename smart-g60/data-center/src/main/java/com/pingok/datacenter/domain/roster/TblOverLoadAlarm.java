package com.pingok.datacenter.domain.roster;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 超限车辆数据表(TBL_OVER_LOAD_ALARM)
 *
 * @author xia
 * @version 1.0.0 2022-07-20
 */
@Data
@Table(name = "TBL_OVER_LOAD_ALARM")
public class TblOverLoadAlarm implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    private Long id;

    public Integer searchResult;
    public Date checkTime;
    public String equipCode;
    public String vehicleNo;
    public Integer plateColor;
    public Integer vehicleType;
    public String uniqueId;
    public Integer speed;
    public Integer total;
    public Integer axles;
    public Integer limitWeight;
    public Integer overWeight;
    public Double overRate;
    public Integer isBulkVehicle;
    public Integer bulkOverWeight;
    public Double bulkOverRate;
    public String licNo;
    public Integer uploadStatus;

}
