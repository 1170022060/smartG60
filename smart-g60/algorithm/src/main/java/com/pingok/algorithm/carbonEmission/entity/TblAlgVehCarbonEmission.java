package com.pingok.algorithm.carbonEmission.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author
 * 车辆碳排放记录表
 */
@Table(name="TBL_ALG_VEH_CARBON_EMISSION")
@Data
@ApiModel(value="TblAlgVehCarbonEmission对象", description="车辆碳排放记录表")
public class TblAlgVehCarbonEmission {

    @ApiModelProperty(value = "主键id")
    @Id
    private Long id;

    @ApiModelProperty(value = "门架编号")
    private String gantryId;

    @ApiModelProperty(value = "车辆种类: 0:客车 1:货车")
    private Integer vehicleStyle;

    @ApiModelProperty(value = "车型: 1:一型客车, 2:二型客车, 3:三型客车, 4:四型客车, 11:一型货车, 12:二型货车, 13:三型货车, 14:四型货车, 15:五型货车, 16:六型货车")
    private Integer vehicleType;

    @ApiModelProperty(value = "车牌颜色: 0:蓝牌, 11:绿牌")
    private Integer vehicleColor;

    @ApiModelProperty(value = "碳排放量")
    private String carbonEmission;

    @ApiModelProperty(value = "碳排放时间 例:2022-05-05 12:00")
    private String carbonEmissionTime;

    @ApiModelProperty(value = "碳排放日期 例:2022-05-05")
    private String carbonEmissionDate;
}
