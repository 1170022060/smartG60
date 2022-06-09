package com.pingok.charge.domain.specialRecord;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 特情表(tbl_special_record)
 *
 * @author qiumin
 * @version 1.0.0 2022-04-11
 */
@Data
@Table(name = "tbl_special_record")
public class TblSpecialRecord implements Serializable {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Network+StationID+LaneID+yyyyMMddHHmmssfff
     */
    private String recordId;

    /**
     * 网络编号
     */
    private String netWork;

    /**
     * 收费站ID
     */
    private String stationId;

    /**
     * 车道ID
     */
    private String laneId;

    /**
     * 1:MTC入 2：MTC出 3：ETC入 4：ETC出
     */
    private Integer laneType;

    /**
     * 车道名称（圈圈号)
     */
    private String markName;

    /**
     * 工班日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date workDate;

    /**
     * 告警时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /**
     * 操作员ID
     */
    private Integer optId;

    /**
     * 操作员姓名
     */
    private String optName;

    /**
     * 工班号
     */
    private Integer shift;

    /**
     * 5:可忽视的，4:需要注意的，3:需要投屏，2:需要报修，1：严重事件
     */
    private Integer specialLevel;

    /**
     * 特情类型
     */
    private Integer type;

    /**
     * 特情信息
     */
    private String message;

    /**
     * 状态 0：未处理，1：已处理，2：忽略 ，3：已弹窗
     */
    private Integer status;

    /**
     * 窗口号
     */
    private Integer winId;

    /**
     * 上传中心标识 0：未上传；1：已上传
     */
    private Integer transStatus;

    private Integer optType;

    private String handleMessage;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 创建用户ID
     */
    private Long createUserId;

    /**
     * 更新用户ID
     */
    private Long updateUserId;

}