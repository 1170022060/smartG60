package com.pingok.datacenter.domain.roster.epidemic;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 中高风险地区车牌名单版本表(TBL_PREFIX_VERSION)
 *
 * @author bianj
 * @version 1.0.0 2022-09-13
 */
@Table(name = "TBL_PREFIX_VERSION")
@Data
public class TblPrefixVersion  implements Serializable {
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
