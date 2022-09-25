package com.pingok.gis.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * vd
 * 
 * @author bianj
 * @version 1.0.0 2022-09-25
 */
@Data
@Table(name = "vd")
public class Vd implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 783368420304914896L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /**
     * id
     */
    @Id
    private Integer id;

    /** type */
    @ApiModelProperty(value = "")
    private String type;

    /** name */
    @ApiModelProperty(value = "")
    private String name;

    /** code */
    @ApiModelProperty(value = "")
    private String code;

    /** direction */
    @ApiModelProperty(value = "")
    private Integer direction;

    /** status */
    @ApiModelProperty(value = "")
    private Integer status;

    /** geom */
    @ApiModelProperty(value = "")
    private String geom;

}