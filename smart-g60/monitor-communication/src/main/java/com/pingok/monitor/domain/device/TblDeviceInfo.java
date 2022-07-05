package com.pingok.monitor.domain.device;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 设备基础信息表
 */
@Data
public class TblDeviceInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 项目名称
     */
    private String itemName;

    /**
     * 立项时间
     */
    private Date itemListTime;

    /**
     * 设备类目
     */
    private Long deviceCategory;

    /**
     * 设备编码
     */
    private String deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 设备品牌
     */
    private String deviceBrand;

    /**
     * 设备型号
     */
    private String deviceModel;

    /**
     * 技术参数
     */
    private String techPara;

    /**
     * 厂商
     */
    private String manufacturer;

    /**
     * 购买日期
     */
    private Date purchaseDate;

    /**
     * 安装日期
     */
    private Date installDate;

    /**
     * 质保期限(月)
     */
    private Long warrantyPeriod;

    /**
     * 使用期限(月)
     */
    private Long serviceLife;

    /**
     * 管理方
     */
    private Long managementSide;

    /**
     * 使用方
     */
    private Long userSide;

    /**
     * 行政区域
     */
    private String adminArea;

    /**
     * 起始路名
     */
    private String startRoad;

    /**
     * 所属场地
     */
    private Long fieldBelong;

    /**
     * 城建坐标
     */
    private String urbanConstrCoordin;

    /**
     * 设备IP
     */
    private String deviceIp;

    /**
     * 管理IP
     */
    private String manageIp;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 桩号
     */
    private String pileNo;

    /**
     * 设备状态
     */
    private Short status;

    /**
     * 设备照片
     */
    private String devicePhoto;

    /**
     * 养护标准
     */
    private String maintainStandard;

    /**
     * 所属路段 上海标准Hex码
     */
    private String roadBelong;

    /**
     * 所属站 上海标准Hex码
     */
    private String stationBelong;

    /**
     * 方向：1.上行 2.下行
     */
    private Short direction;

    /**
     * 监控级别：1.中心 2.集中站3.收费站
     */
    private Short monitorLevel;

    /**
     * GPS位置
     */
    private String gps;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建用户ID
     */
    private Long createUserId;

    /**
     * 更新用户ID
     */
    private Long updateUserId;

    /**
     * 是否可控（1-可控；0-不可控）
     */
    private Long isControl;

    /**
     * 设备协议
     */
    private String protocol;

    /**
     * 从站id
     */
    private Integer slaveId;

    private static final long serialVersionUID = 1L;
}