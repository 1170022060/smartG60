package com.pingok.external.domain.roadDoctor;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 道路病害类型表(TBL_ROAD_DISEASE_TYPE)
 * 
 * @author bianj
 * @version 1.0.0 2022-07-20
 */
@Data
@Table(name = "TBL_ROAD_DISEASE_TYPE")
public class TblRoadDiseaseType implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 8868082420474096873L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private Long id;

    /** 报告ID */
    private Long crId;

    /** 病害类型ID */
    private Long questId;

    /** 病害类型名 */
    private String questName;

    /** 当前类型下病害数量 */
    private Integer questAccount;

}