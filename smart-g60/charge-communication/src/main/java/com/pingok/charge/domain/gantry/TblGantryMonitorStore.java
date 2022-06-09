package com.pingok.charge.domain.gantry;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author
 * @time 2022/5/27 16:26
 */
@Data
@Table(name = "tbl_gantry_monitor_store")
public class TblGantryMonitorStore implements Serializable {

    private static final long serialVersionUID = 1L;

    private String chargeUnitId;
    private String heatVersion;
    private String gantryHeartbeatList;
    private String transProFitList;
    private String chargeUnitHeartbeatList;
    private String vehicleDetectorHeartbeatList;
    private String otherHeartbeatList;
    private String cameraHeartbeatList;
    private String RSUHeartbeatList;
    private String PSAMInfoList;
    private String antennalInfoList;
}