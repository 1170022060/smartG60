package com.pingok.algorithm.predictCharge.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 收费区间表
 */
@Data
@ApiModel(value="ChargeIntervalDto对象", description="收费区间表")
public class ChargeIntervalDto {

    @ApiModelProperty(value = "收费区间编号")
    private String chargeIntervalId;

    @ApiModelProperty(value = "收费区间常数")
    private double constant;
}
