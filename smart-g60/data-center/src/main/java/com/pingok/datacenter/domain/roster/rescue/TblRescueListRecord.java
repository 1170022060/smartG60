package com.pingok.datacenter.domain.roster.rescue;

import com.ruoyi.common.core.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 抢险救灾名单记录表 TBL_RESCUE_LIST_RECORD
 *
 * @author ruoyi
 */
@Data
@Table(name = "TBL_RESCUE_LIST_RECORD")
public class TblRescueListRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    private Long versionId;

    /** id */
    private String rescueId;

    /** 数据类型（1-预约类，2-名单类） */
    private Integer dataType;

    /** 优惠类型，前1-2位优免名单大类，3-6位保留（默认为0000）010000 - 疫苗车辆020000 – 应急车辆 */
    private String appointType;

    /** 优惠说明 */
    private String discountInfo;

    /** 处理方（1-收费站2-出口省中心（或收费站）3-通行省中心） */
    private Integer handleType;

    /** 核验方（1-无需核验2-收费站3-出口省中心4-通行省中心） */
    private Integer checkType;

    /** 预约时间 */
    private Date appointTime;

    /** 车辆状态标识 */
    private String vehicleSign;

    /** 车道处理（1-一级处理2-9预留） */
    private Integer laneHandle;

    /** 车辆识别方式（1-通过ETC卡编号确定车辆2-通过车牌编号确定车辆3-通过ETC卡编号或车牌号确定车辆4-通过ETC卡编号且车牌号确定车辆） */
    private Integer vehicleIdentifyType;

    /** ETC卡编号 */
    private String cardId;

    /** 车辆车牌号码+颜色（车牌号码+间隔符+车牌颜色间隔符：“_”车牌颜色2位数字:0-蓝色，1-黄色，2-黑色，3-白色，4- 渐变绿色5- 黄绿双拼色6- 蓝白渐变色7- 临时牌照11-绿色12-红色例：京A12345_1） */
    private String vehicleId;

    /** 车牌颜色 */
    private Integer vehiclePlateColor;

    /** 增量操作（1-新增2-变更3-删除） */
    private Integer operation;

    /** 优惠方式（1-按优惠折扣2-按优惠金额3-按优惠定额4-其他） */
    private Integer discountType;

    /** 优惠折扣 */
    private Float discount;

    /** 优惠金额 */
    private Integer discountAmount;

    /** 优惠定额 */
    private Integer discountQuota;

    /** 行程开始时间 */
    private Date startTime;

    /** 行程结束时间 */
    private Date endTime;

    /** 优惠省份（多个省份以“|”分隔） */
    private String provinceIds;

    /** 入口收费站名称 */
    private String enStation;

    /** 出口收费站名称 */
    private String exStation;

    /**
     * 创建或更新时间
     */
    @ApiModelProperty(value = "创建或更新时间")
    private Date updateTime;
}
