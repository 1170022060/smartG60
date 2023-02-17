package com.pingok.external.domain.primary;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 超限重量占比对比信息表(TBL_OL_WEIGHTANDRATE_INFO)
 * 
 * @author bianj
 * @version 1.0.0 2023-02-13
 */
@Data
@Table(name = "TBL_OL_WEIGHTANDRATE_INFO")
public class TblOlWeightandrateInfo implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -8192202043181761229L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private Long id;

    /** 站Id */
    private String nodeId;

    /** 超限总量 */
    private Long overLoadFlow;

    /** 总流量 */
    private Long totalFlow;

    /** 超限 0-5t 流量 */
    private Long overLoadTonflow0to5;

    /** 超限 5-10t 流量*/
    private Long overloadtonflow5to10;

    /** 超限 10-30t 流量 */
    private Long overLoadTonflow10to30;

    /** 超限 30-55t 流量 */
    private Long overLoadTonflow30to55;

    /** 超限 55-100t 流量 */
    private Long overLoadTonflow55to100;

    /** 超限 100t 以上流量 */
    private Long overLoadTonflow100;

    /** 超限率 0-50%流量 */
    private Long overLoadTonrate0to50;

    /** 超限率 50%以上流量 */
    private Long overLoadTonerate50;

}