package com.pingok.algorithm.trafficStatus.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author
 * 交通状态记录表
 */
@Table(name="TBL_ALG_TRAFFIC_STATUS")
@Data
@ApiModel(value="TblAlgTrafficStatus对象", description="交通状态记录表")
public class TblAlgTrafficStatus {

    @ApiModelProperty(value = "主键id")
    @Id
    private Long id;

    @ApiModelProperty(value = "门架编号")
    private String gantryId;

    @ApiModelProperty(value = "前一个门架编号")
    private String lastGantryId;

    @ApiModelProperty(value = "类型 0:门架（计算门架之间数据） 1:收费站（计算门架收费站之间数据）")
    private Integer type;

    @ApiModelProperty(value = "行程时间(h)")
    private String travelTime;

    @ApiModelProperty(value = "平均速度(km/h)")
    private String averageSpeed;

    @ApiModelProperty(value = "交通状态 0:畅通 1:轻度拥堵 2:中度拥堵 3:严重拥堵")
    private Integer trafficStatus;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
