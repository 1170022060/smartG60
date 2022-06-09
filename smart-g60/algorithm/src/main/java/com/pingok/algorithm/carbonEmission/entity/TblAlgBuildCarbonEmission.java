package com.pingok.algorithm.carbonEmission.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author
 * 碳排放记录表
 */
@Table(name="TBL_ALG_BUILD_CARBON_EMISSION")
@Data
@ApiModel(value="TblAlgBuildCarbonEmission对象", description="建筑碳排放记录表")
public class TblAlgBuildCarbonEmission {

    @ApiModelProperty(value = "主键id")
    @Id
    private Long id;

    @ApiModelProperty(value = "建筑电能年消耗量(单位/a)")
    private String electricityConsume;

    @ApiModelProperty(value = "建筑绿地碳汇系统年减碳量(kgCO2/a)")
    private String greenReduceCarbon;

    @ApiModelProperty(value = "建筑设计寿命(a)")
    private String buildDesignLife;

    @ApiModelProperty(value = "建筑面积(a2)")
    private String buildArea;

    @ApiModelProperty(value = "碳排放量")
    private String carbonEmission;

    @ApiModelProperty(value = "碳排放年份 例:2022")
    private String carbonEmissionYear;
}
