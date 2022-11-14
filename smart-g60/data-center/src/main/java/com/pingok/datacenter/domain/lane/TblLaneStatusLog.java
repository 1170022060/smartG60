package com.pingok.datacenter.domain.lane;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 车道状态(TBL_LANE_STATUS_LOG)
 *
 * @author qiumin
 * @version 1.0.0 2022-04-12
 */
@Data
@Table(name = "TBL_LANE_STATUS_LOG")
public class TblLaneStatusLog implements Serializable {


    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 车道ID
     */
    private String laneId;

    /**
     * 车道Node十六进制
     */
    private String laneHex;

    /**
     * 心跳时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /**
     * 操作员ID
     */
    private Long optId;

    /**
     * 操作员姓名
     */
    private String optName;

    /**
     * 刷卡数
     */
    private Integer cardCnt;

    /**
     * 发票打印数量
     */
    private Integer receiptTimes;

    /**
     * 当前班次 1：白班 ，2：夜班
     */
    private Integer shift;

    /**
     * 软件版本
     */
    private String softVer;

    /**
     * 当前班次过车数量（以生成流水为准）
     */
    private Integer passTimes;

    /**
     * 当前班次Cpc卡过车数量（以生成流水为准）
     */
    private Integer cpcTimes;

    /**
     * 当前班次Rsu过车数量（以生成流水为准）
     */
    private Integer rsuTimes;

    /**
     * 当前班次特情过车数量（以生成流水为准）
     */
    private Integer specTimes;

    /**
     * 车道状态 0: 关 ， 1：开
     */
    private Integer status;

    /**
     * 1:MTC  2：ETC  3:混合
     */
    private Integer laneType;

    /**
     * 数据磁盘可用空间，单位 Gb
     */
    private Integer disks;

    /**
     * 车道工控机当前Cpu使用率 百分比
     */
    private BigDecimal cpu;

    /**
     * 车道工控机当前内存使用率 百分比
     */
    private BigDecimal memory;

    /**
     * 未上传流水数量
     */
    private Integer errorTrans;

    /**
     * 未上传牌识流水数量
     */
    private Integer errorLprTrans;

    /**
     * 车牌识别状态 0：正常 1：异常
     */
    private Integer lprStatus;

    /**
     * 摄像机状态 0：正常 1：异常
     */
    private Integer cameraStatus;

    /**
     * 治超状态 0：正常 1：异常
     */
    private Integer overLoadStatus;

    /**
     * 网络状态 0：正常 1：异常
     */
    private Integer netStatus;

    /**
     * Psam卡号
     */
    private String psamId;

    /**
     * 操作员信息最后更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date optInfoUpdateTime;

    /**
     * 节假日免费信息最后更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date freeInfoUpdateTime;

    /**
     * 牵引车名单信息最后更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date tractorBlackVer;

    /**
     * 线上版本最小费率的版本号
     */
    private Integer currVersion;

    /**
     * 线上版本最小费率的启用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date currStartTime;

    /**
     * 下一版本的最小费率的版本号
     */
    private Integer nextVersion;

    /**
     * 下一版本的最小费率的启用时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date nextVerStartTime;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


    /**
     * 1-入口 2-出口
     */
    private Integer orientation;

    /**
     * 天线状态 0：正常 1：异常
     */
    private Integer rsuStatus;

    /**
     * 车道工控机状态 0：正常 1：异常
     */
    private Integer ipcStatus;
}