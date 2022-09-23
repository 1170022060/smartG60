package com.pingok.algorithm.event.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author
 * 事件基础表
 */
@Table(name="TBL_ALG_EVENT")
@Data
@ApiModel(value="TblAlgEvent对象", description="事件基础表")
public class TblAlgEvent {

    @ApiModelProperty(value = "主键id")
    @Id
    private Long id;

    @ApiModelProperty(value = "桩号")
    private String stationNum;

    @ApiModelProperty(value = "车流方向 0:上行 1:下行")
    private Integer trafficDirection;

    @ApiModelProperty(value = "车道号")
    private String lineNum;

    @ApiModelProperty(value = "车道限速")
    private Integer limitSpeed;

    @ApiModelProperty(value = "事件类型 0:既定事件 1:突发事件")
    private Integer eventType;

    @ApiModelProperty(value = "事件状态 0:未开始 1:执行中 2:已结束")
    private Integer status;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "预计结束时间")
    private String endTime;

    @ApiModelProperty(value = "到达结束时间后，车道速度连续上升次数")
    private Integer riseNum;

    @ApiModelProperty(value = "到达结束时间后，采样总次数")
    private Integer sampleNum;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
