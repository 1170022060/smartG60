package com.pingok.datacenter.domain.roster.rescue;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 抢险救灾名单版本表(TBL_RESCUE_VERSION)
 *
 * @author bianj
 * @version 1.0.0 2022-09-13
 */
@Table(name = "TBL_RESCUE_VERSION")
@Data
public class TblRescueVersion  implements Serializable {
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
