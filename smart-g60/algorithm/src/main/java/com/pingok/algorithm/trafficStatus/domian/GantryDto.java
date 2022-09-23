package com.pingok.algorithm.trafficStatus.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门架信息表
 */
@Data
@ApiModel(value="GantryDto对象", description="门架信息表")
public class GantryDto {

    @ApiModelProperty(value = "门架编号(收费站编号)")
    private String gantryId;

    @ApiModelProperty(value = "车道数")
    private Integer laneCount;

    @ApiModelProperty(value = "方向 0:上行 1:下行")
    private Integer direction;

    @ApiModelProperty(value = "与前一门架(收费站)距离")
    private String lastGantryDistance;

    @ApiModelProperty(value = "类型 0:门架 1:收费站")
    private Integer type;

    @ApiModelProperty(value = "限速km/h")
    private Integer limitSpeed;
}
