package com.pingok.algorithm.predictCharge.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据处理结果
 */
@Data
@ApiModel(value="DealResult对象", description="数据处理结果")
public class DealResult {

    @ApiModelProperty(value = "收费区间第一个相同星期车流量")
    private long firstFlow;

    @ApiModelProperty(value = "收费区间最后一个相同星期车流量")
    private long lastFlow;

    @ApiModelProperty(value = "收费区间相同星期周数")
    private int weeks;

}
