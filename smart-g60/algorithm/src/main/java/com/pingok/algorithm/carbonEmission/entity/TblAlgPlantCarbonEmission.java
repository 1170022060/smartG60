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
@Table(name="TBL_ALG_PLANT_CARBON_EMISSION")
@Data
@ApiModel(value="TblAlgPlantCarbonEmission对象", description="植物碳排放记录表")
public class TblAlgPlantCarbonEmission {

    @ApiModelProperty(value = "主键id")
    @Id
    private Long id;

    @ApiModelProperty(value = "植物覆盖面积")
    private String plantArea;

    @ApiModelProperty(value = "碳排放量")
    private String carbonEmission;

    @ApiModelProperty(value = "碳排放年份 例:2022")
    private String carbonEmissionYear;
}
