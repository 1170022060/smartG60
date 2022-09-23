package com.pingok.gantry.domain.vo;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class GantryMonitorKey implements Serializable {

    /** 流水号=门架编号+批次号（2019082412）+批次号内序号（3位）            门架前端必填 */
    private String runStateId;

    /** 1-主            2-备 */
    private Integer computerOrder;
}
