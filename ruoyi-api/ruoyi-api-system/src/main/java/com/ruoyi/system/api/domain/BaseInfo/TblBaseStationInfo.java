package com.ruoyi.system.api.domain.BaseInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 收费站基础信息表 TBL_BASE_STATION_INFO
 *
 * @author ruoyi
 */
@Data
public class TblBaseStationInfo {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    private Long id;

    /** 收费站编号 */
    private String recordNum;

    /** 版本号 */
    private String version;

    /** 网络编号*/
    private String netWork;

    /** 收费站id*/
    private String stationId;

    /** 收费站名称*/
    private String stationName;

    /** 收费站Node国标*/
    private String stationGb;

    /** 收费站Node十六进制*/
    private String stationHex;

    /** 桩号*/
    private String pileNo;

    /** 出入口类型*/
    private Integer gateType;

    /** 类型*/
    private Integer squareType;

    /** 坐标*/
    private String gps;

    /** 是否有治超*/
    private Integer overloadControl;

    /** 类型*/
    private Integer overloadControlSite;

    /** 所属路段*/
    private String roadBelong;

    /** 集中站主键ID */
    private Long adminStationId;

    /** 部门ID */
    private Long deptId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 创建用户ID */
    private Long createUserId;

    /** 更新用户ID */
    private Long updateUserId;

    /** 车道数*/
    private Integer laneCount;

    /** 与DAAS平台通信服务专用IP */
    private String ip;

    private String port;

}
