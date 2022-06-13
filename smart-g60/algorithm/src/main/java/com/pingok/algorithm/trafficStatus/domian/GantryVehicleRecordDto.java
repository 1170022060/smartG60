package com.pingok.algorithm.trafficStatus.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 门架过车记录
 */
@Data
@ApiModel(value="GantryVehicleRecordDto对象", description="门架过车记录")
public class GantryVehicleRecordDto {

    @ApiModelProperty(value = "门架编号")
    private String gantryId;

    @ApiModelProperty(value = "车牌")
    private String licensePlate;

    @ApiModelProperty(value = "经过时间 yyyy-MM-dd HH:mm:ss")
    private String passTime;
}
