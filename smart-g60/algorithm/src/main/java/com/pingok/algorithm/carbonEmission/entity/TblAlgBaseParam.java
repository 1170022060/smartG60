package com.pingok.algorithm.carbonEmission.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author
 * 百公里油耗表
 */
@Table(name="TBL_ALG_BASE_PARAM")
@Data
@ApiModel(value="TblAlgorithmBaseParam对象", description="基础参数表")
public class TblAlgBaseParam {

    @ApiModelProperty(value = "主键id")
    @Id
    private Long id;

    @ApiModelProperty(value = "参数类型 0:计费里程 1:燃油车系数 2:能源车系数 3:燃油密度")
    private Integer paramType;

    @ApiModelProperty(value = "参数主键")
    private String paramKey;

    @ApiModelProperty(value = "参数值")
    private String paramValue;

}
