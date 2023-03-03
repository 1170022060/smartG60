package com.pingok.vocational.domain.report;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "TBL_VD_HISTORY_RECORD")
public class TblVdHistoryRecord implements Serializable {

    @Id
    private Long id;

    private String deviceId;

    private Integer direction;

    private Date collectTime;

    private Long volume;

    private Long speed;

    private BigDecimal occupy;

    private BigDecimal vh;

    private String pileNo;
}
