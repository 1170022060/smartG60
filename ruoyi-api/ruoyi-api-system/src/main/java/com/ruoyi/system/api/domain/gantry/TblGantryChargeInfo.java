package com.ruoyi.system.api.domain.gantry;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
public class TblGantryChargeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String chargingUnitId;

    private String chargingUnitName;

    private Integer nature;     //路径性质：1-经营性；2-还贷性

    private Integer direction;  //方向

    private Integer mileage;

    private Double startLongitude;

    private Double startLatitude;

    private String startPileNo;

    private String endPileNo;

    private Double endLongitude;

    private Double endLatitude;

//    private String coincidenceGB;   //重合收费公路编号

    private Integer limitId;        //省界标识：0-非省界 1-省界

    private Integer status;

    private Integer chargingUnitType;   //1-收费区间；2-算法区间（带收费站）

    private Integer limitSpeed;         //限速 km/h

    private Double constant;   //理论日收益计算因子
}
