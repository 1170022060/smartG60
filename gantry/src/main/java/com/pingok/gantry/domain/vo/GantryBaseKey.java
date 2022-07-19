package com.pingok.gantry.domain.vo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class GantryBaseKey implements Serializable {

    /** 基础信息流水号=收费门架编号+批次号+批次内流水号（3位） */
    @Column(name = "GantryBaseId")
    private String gantryBaseId;

    /** 1-主机；2-备机 */
    @Column(name = "ComputerOrder")
    private Integer computerOrder;
}
