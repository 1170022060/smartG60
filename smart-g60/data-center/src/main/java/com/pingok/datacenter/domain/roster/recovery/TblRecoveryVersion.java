package com.pingok.datacenter.domain.roster.recovery;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 追缴名单版本表(TBL_RECOVERY_VERSION)
 *
 * @author bianj
 * @version 1.0.0 2022-09-13
 */
@Table(name = "TBL_RECOVERY_VERSION")
@Data
public class TblRecoveryVersion implements Serializable {
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
