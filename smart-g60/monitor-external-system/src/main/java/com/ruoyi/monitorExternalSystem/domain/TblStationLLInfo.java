package com.ruoyi.monitorExternalSystem.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "TBL_STATION_LL_INFO")
public class TblStationLLInfo implements Serializable {

    /** id */
    @Id
    private Long id;

    /** 站点名称(收费广 场) */
    private String nodeName;

    /** 站点编号(收费广 场) */
    private String nodeId;

    /** 纬度 */
    private Long latitude;

    /** 经度 */
    private Long longitude;

    /** 节点类型 1 高速公路 2 非现场 */
    private Integer nodeType;
}
