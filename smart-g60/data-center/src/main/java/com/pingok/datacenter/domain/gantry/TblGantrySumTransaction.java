package com.pingok.datacenter.domain.gantry;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author
 * @time 2022/5/20 17:57
 */
@Data
@Table(name="tbl_gantry_sum_transaction")
public class TblGantrySumTransaction implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1L;

    /** ETC */
    @Id
    private String collectId;

    /** gantryid */
    private String gantryId;

    /** 控制器序号 */
    private Integer computerOrder;

    /** YYYYMMDD */
    private String collectDate;

    /** collectHourBatch */
    private String collectHourBatch;

    /** batchCount */
    private Integer batchCount;

    /** etcTypeCount */
    private String etcTypeCount;

    /** 0- */
    private String etcClassCount;

    /** etcSuccessCount */
    private Integer etcSuccessCount;

    /** etcSuccessFee */
    private Long etcSuccessFee;

    /** etcFailCount */
    private Integer etcFailCount;


    /** cpcTypeCount */
    private String cpcTypeCount;

    /** 0- */
    private String cpcClassCount;

    /** cpcSuccessCount */
    private Integer cpcSuccessCount;

    /** cpcSuccessFee */
    private Long cpcSuccessFee;

    /** cpcFailCount */
    private Integer cpcFailCount;




}
