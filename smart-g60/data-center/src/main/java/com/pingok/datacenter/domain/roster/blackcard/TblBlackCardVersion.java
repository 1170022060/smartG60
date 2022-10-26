package com.pingok.datacenter.domain.roster.blackcard;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 状态名单版本表(TBL_BLACK_CARD_VERSION)
 *
 * @author bianj
 * @version 1.0.0 2022-09-13
 */
@Table(name = "TBL_BLACK_CARD_VERSION")
@Data
public class TblBlackCardVersion implements Serializable {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 版本号
     */
    private String version;
}
