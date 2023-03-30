package com.pingok.monitor.domain.vdt;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Table(name = "TBL_VD_HISTORY_RECORD")
public class TblVdHistoryRecord implements Serializable {

    @Id
    private Long id;

    private String deviceId;

    private Integer direction;

    private String pileNo;

    private String collectTime;

    private Long volume;

    private Long speed;

    private Long occupy;

    private Long vh;
}
