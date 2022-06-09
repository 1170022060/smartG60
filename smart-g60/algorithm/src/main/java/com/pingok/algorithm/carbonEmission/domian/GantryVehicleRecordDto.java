package com.pingok.algorithm.carbonEmission.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门架过车记录
 */
@Data
@ApiModel(value="GantryVehicleRecordDto对象", description="门架过车记录")
public class GantryVehicleRecordDto {

    @ApiModelProperty(value = "门架编号")
    private String gantryId;

    @ApiModelProperty(value = "车辆种类: 0:客车 1:货车")
    private Integer vehicleStyle;

    @ApiModelProperty(value = "车型: 1:一型客车, 2:二型客车, 3:三型客车, 4:四型客车, 11:一型货车, 12:二型货车, 13:三型货车, 14:四型货车, 15:五型货车, 16:六型货车")
    private Integer vehicleType;

    @ApiModelProperty(value = "车牌颜色: 0:蓝牌, 11:绿牌")
    private Integer vehicleColor;

    @ApiModelProperty(value = "车牌")
    private String licensePlate;

    @ApiModelProperty(value = "经过时间 yyyy-MM-dd HH:mm:ss")
    private String passTime;

    @ApiModelProperty(value = "与下一门架距离")
    private String nextGantryDistance;

    @ApiModelProperty(value = "车辆平均速度")
    private Double vehicleAverageSpeed;

    @ApiModelProperty(value = "门架平均速度")
    private Double gantryAverageSpeed;

    @ApiModelProperty(value = "门架流量")
    private Long gantryFlow;
}
