package com.pingok.algorithm.carbonEmission.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author
 * 百公里油耗表
 */
@Table(name="TBL_ALG_OIL_CONSUME")
@Data
@ApiModel(value="TblAlgorithmOilConsume对象", description="百公里油耗表")
public class TblAlgOilConsume {

    @ApiModelProperty(value = "主键id")
    @Id
    private Integer id;

    @ApiModelProperty(value = "车型: 客Ⅰ, 客Ⅱ, 货Ⅰ, 货Ⅱ")
    private String vehicleType;

    @ApiModelProperty(value = "速度（km/h）")
    private String speed;

    @ApiModelProperty(value = "油耗（L/100km）")
    private String oilConsume;

}
