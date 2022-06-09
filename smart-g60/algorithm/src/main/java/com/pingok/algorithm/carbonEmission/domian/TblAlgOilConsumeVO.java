package com.pingok.algorithm.carbonEmission.domian;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author
 * 百公里油耗表
 */
@Data
@ApiModel(value="TblAlgorithmOilConsume对象", description="百公里油耗表")
public class TblAlgOilConsumeVO {

    @ApiModelProperty(value = "速度（km/h）")
    private Double V;

    @ApiModelProperty(value = "油耗（L/100km）")
    private Double F;

    public Double getV() {
        return V;
    }

    public void setV(Double v) {
        V = v;
    }

    public Double getF() {
        return F;
    }

    public void setF(Double f) {
        F = f;
    }
}
