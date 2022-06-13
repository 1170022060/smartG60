package com.pingok.algorithm.trafficStatus.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author
 * 交通状态临时表
 */
@Data
@ApiModel(value="TblAlgTrafficStatusVO对象", description="交通状态临时表")
public class TblAlgTrafficStatusVO {

    @ApiModelProperty(value = "门架编号")
    private String gantryId;

    @ApiModelProperty(value = "前一个门架编号")
    private String lastGantryId;

    @ApiModelProperty(value = "车牌")
    private String licensePlate;

    @ApiModelProperty(value = "经过当前门架时间 yyyy-MM-dd HH:mm:ss")
    private String passGantryTime;

    @ApiModelProperty(value = "经过前一门架时间 yyyy-MM-dd HH:mm:ss")
    private String passLastGantryTime;

    @ApiModelProperty(value = "行程时间(h)")
    private Double travelTime;
}
