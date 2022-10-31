package com.pingok.datacenter.domain.roster.epidemic;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 中高风险地区名单表(TBL_EPIDEMIC_LIST_RECORD)
 * 
 * @author xia
 * @version 1.0.0 2022-07-20
 */
@Data
@Table(name = "TBL_EPIDEMIC_LIST_RECORD")
public class TblEpidemicListRecord implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 4462453608842553099L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 主键 */
    @Id
    private Long id;

    /** 收费站编码 */
    private String stationId;

    /** 收费站Hex码 */
    private String stationHex;

    /** 收费站名称 */
    private String stationName;

    /** 所在地级市 */
    private String regionName;

    /** 生效时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    /**
     * 创建或更新时间
     */
    @ApiModelProperty(value = "创建或更新时间")
    private Date updateTime;

    private Date dbTime;

    /**
     * 版本表主键Id
     */
    private Long versionId;
}