package com.pingok.vocational.domain.roster;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 中高风险地区车牌前缀名单表(TBL_PREFIX_LIST_RECORD)
 * 
 * @author xia
 * @version 1.0.0 2022-08-01
 */
@Table(name = "TBL_PREFIX_LIST_RECORD")
public class TblPrefixListRecord implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -1220330911984645597L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 主键ID */
    @Id
    private Long id;

    /** 车牌前缀 例:"京A" */
    private String prefix;

    /** 生效时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 版本号 */
    private String version;

    /* This code was generated by TableGo tools, mark 1 end. */

    /* This code was generated by TableGo tools, mark 2 begin. */

    /**
     * 获取主键
     * 
     * @return 主键
     */
    public Long getId() {
        return this.id;
    }

    /**
     * 设置主键
     * 
     * @param id
     *          主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取车牌前缀 例:"京A"
     * 
     * @return 车牌前缀 例
     */
    public String getPrefix() {
        return this.prefix;
    }

    /**
     * 设置车牌前缀 例:"京A"
     * 
     * @param prefix
     *          车牌前缀 例
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * 获取生效时间
     * 
     * @return 生效时间
     */
    public Date getStartTime() {
        return this.startTime;
    }

    /**
     * 设置生效时间
     * 
     * @param startTime
     *          生效时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取版本号
     * 
     * @return 版本号
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * 设置版本号
     * 
     * @param version
     *          版本号
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /* This code was generated by TableGo tools, mark 2 end. */
}