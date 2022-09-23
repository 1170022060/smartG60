package com.pingok.algorithm.predictCharge.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author
 * 收益预测记录表
 */
@Table(name="TBL_ALG_PREDICT_CHARGE_RECORD")
@Data
@ApiModel(value="TblAlgPredictChargeRecord对象", description="收益预测记录表")
public class TblAlgPredictChargeRecord {

    @ApiModelProperty(value = "主键id")
    @Id
    private Long id;

    @ApiModelProperty(value = "收费区间编号")
    private String chargeIntervalId;

    @ApiModelProperty(value = "记录日期，例：2022-09-06")
    private String recordDate;

    @ApiModelProperty(value = "预测日车流量")
    private Integer preFlow;

    @ApiModelProperty(value = "预测理论日收益")
    private String preTheoryCharge;

    @ApiModelProperty(value = "预测实际日收益")
    private String preRealCharge;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
