package com.pingok.vocational.domain.blackCard;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "TBL_BLACK_CARD_LOG")
public class TblBlackCardLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String mediaId;

    private String issuerId;

    private String insertTime;

    private Integer type;

    private Integer status;

    private String creationTime;

    private String version;

    private Date updateTime;

    private Integer mediaType;

    private String applyTime;
}
