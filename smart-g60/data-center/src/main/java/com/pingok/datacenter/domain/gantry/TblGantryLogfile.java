package com.pingok.datacenter.domain.gantry;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * @time 2022/5/22 13:57
 */
@Table(name="TBL_GANTRY_LOGFILE")
@Data
public class TblGantryLogfile implements Serializable {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 门架编号
     */
    private String gantryId;

    /**
     * 设备编码
     */
    private String url;

    private static final long serialVersionUID = 1L;
}
