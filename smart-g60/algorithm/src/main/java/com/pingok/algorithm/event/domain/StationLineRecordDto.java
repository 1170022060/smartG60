package com.pingok.algorithm.event.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 桩号车道记录
 */
@Data
@ApiModel(value="StationLineRecordDto对象", description="桩号车道记录")
public class StationLineRecordDto {

    @ApiModelProperty(value = "起始桩号")
    private String startStationNum;

    @ApiModelProperty(value = "截止桩号")
    private String endStationNum;

    @ApiModelProperty(value = "车道号")
    private String lineNum;

    @ApiModelProperty(value = "车道类型 0:查询车道 1:相邻车道")
    private Integer lineType;

    @ApiModelProperty(value = "车道速度")
    private String lineSpeed;

    @ApiModelProperty(value = "限速")
    private Integer limitSpeed;

    @ApiModelProperty(value = "是否产生影响 0:未产生 1:产生")
    private Integer isEffect;
}
