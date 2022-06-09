package com.pingok.devicemonitor.domain.gantry;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author 
 * 门架设备基础信息表
 */
@Table(name="TBL_GANTRY_INFO")
@Data
public class TblGantryInfo implements Serializable {
    /**
     * 主键
     */
    @Id
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
    private Short deviceCategory;

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
    private Short warrantyPeriod;

    /**
     * 使用期限(月)
     */
    private Short serviceLife;

    /**
     * 管理方
     */
    private Short managementSide;

    /**
     * 使用方
     */
    private Short userSide;

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
    private Short fieldBelong;

    /**
     * 城建坐标
     */
    private String urbanConstrCoordin;

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
    private Short createUserId;

    /**
     * 更新用户ID
     */
    private Short updateUserId;

    private static final long serialVersionUID = 1L;
}