package com.pingok.external.domain.primary;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 超限流量、超限率时间对比信息表(TBL_OL_FLOWANDRATE_INFO)
 * 
 * @author bianj
 * @version 1.0.0 2023-02-13
 */
@Data
@Table(name = "TBL_OL_FLOWANDRATE_INFO")
public class TblOlFlowandrateInfo implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -8067651718332516579L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private Long id;

    /** 站点名称(收费广 场) */
    private String name;

    /** 站id */
    private String nodeId;

    /** 超限流量 24 小时 分布 */
    private String data;

    /** 超限率 24 小时分 布 */
    private String rate;

    /** 日期 */
    private String time;


}