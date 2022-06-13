package com.pingok.algorithm.trafficStatus.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author
 * 平均速度与交通状态对应关系表
 */
@Table(name="TBL_ALG_TRAFFIC_STATUS_SPEED")
@Data
@ApiModel(value="TblAlgTrafficStatusSpeed对象", description="平均速度与交通状态对应关系表")
public class TblAlgTrafficStatusSpeed {

    @ApiModelProperty(value = "主键id")
    @Id
    private Long id;

    @ApiModelProperty(value = "限速(km/h)")
    private Integer limitSpeed;

    @ApiModelProperty(value = "最小速度(km/h)")
    private Integer minSpeed;

    @ApiModelProperty(value = "最大速度(km/h)")
    private Integer maxSpeed;

    @ApiModelProperty(value = "交通状态 0:畅通 1:轻度拥堵 2:中度拥堵 3:严重拥堵")
    private Integer trafficStatus;
}
