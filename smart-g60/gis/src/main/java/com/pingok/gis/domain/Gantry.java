package com.pingok.gis.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * gantry
 * 
 * @author bianj
 * @version 1.0.0 2022-09-25
 */
@Data
@Table(name = "gantry")
public class Gantry implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 8190178395389687868L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private Integer id;

    /** name */
    @ApiModelProperty(value = "")
    private String name;

    /** code */
    @ApiModelProperty(value = "")
    private String code;

    /** status */
    @ApiModelProperty(value = "")
    private Integer status;

    /** geom */
    @ApiModelProperty(value = "")
    private String geom;

    /** direction */
    @ApiModelProperty(value = "")
    private Integer direction;

    /** type */
    @ApiModelProperty(value = "")
    private String type;

}