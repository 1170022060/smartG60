package com.pingok.charge.domain.gantry;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author
 * @time 2022/5/27 16:24
 */
@Data
@Table(name = "tbl_gantry_baseinfo_store")
public class TblGantryBaseInfoStore implements Serializable {

    private static final long serialVersionUID = 1L;

    private String chargeUnitId;
    private String infoVersion;
    private String gantryInfoList;
    private String chargeUnitInfoList;
    private String cameraInfoList;
    private String RSUInfoList;
    private String vehicleDetectorInfoList;
    private String vehicleCountCollect;
    private String otherInfoList;
}
