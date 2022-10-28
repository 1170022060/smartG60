package com.pingok.datacenter.domain.roster.epidemic;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 中高风险名单版本表(TBL_EPIDEMIC_VERSION)
 *
 * @author bianj
 * @version 1.0.0 2022-09-13
 */
@Table(name = "TBL_EPIDEMIC_VERSION")
@Data
public class TblEpidemicVersion implements Serializable {
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
