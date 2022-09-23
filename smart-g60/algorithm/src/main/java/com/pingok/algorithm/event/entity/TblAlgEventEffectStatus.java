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
 * 事件影响状态表
 */
@Table(name="TBL_ALG_EVENT_EFFECT_STATUS")
@Data
@ApiModel(value="TblAlgEventEffectStatus对象", description="事件影响状态表")
public class TblAlgEventEffectStatus {

    @ApiModelProperty(value = "主键id")
    @Id
    private Long id;

    @ApiModelProperty(value = "事件id")
    private Long eventId;

    @ApiModelProperty(value = "车道速度")
    private String lineSpeed;

    @ApiModelProperty(value = "采样时间")
    private String sampleTime;

    @ApiModelProperty(value = "影响状态标识 0:未产生影响 1:已产生影响 2:影响结束")
    private Integer effectFlg;

    @ApiModelProperty(value = "轻度拥堵时刻")
    private String mildCongTime;

    @ApiModelProperty(value = "中度拥堵时刻")
    private String medCongTime;

    @ApiModelProperty(value = "重度拥堵时刻")
    private String sevCongTime;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
