package com.pingok.algorithm.predictCharge.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 收费区间流量记录
 */
@Data
@ApiModel(value="ChargeIntervalFlowRecordDto对象", description="收费区间流量记录")
public class ChargeIntervalFlowRecordDto {

    @ApiModelProperty(value = "收费区间编号")
    private String chargeIntervalId;

    @ApiModelProperty(value = "统计日期 yyyy-MM-dd")
    private String statisticsDate;

    @ApiModelProperty(value = "收费区间日流量")
    private Long flow;
}
