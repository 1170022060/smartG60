package com.pingok.vocational.domain.blackCard;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "TBL_BALCK_CARD_STATION_USED")
public class TblBlackCardStationUsed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String stationHex;

    private String obuVersion;

    private Date applyTime;

    private Date createTime;

    private String cpcVersion;
}
