package com.pingok.gis.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * light
 *
 * @author bianj
 * @version 1.0.0 2022-09-25
 */
@Data
@Table(name = "light")
public class Light implements Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 4043461579359790771L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /**
     * id
     */
    @Id
    private Integer id;

    /**
     * type
     */
    @ApiModelProperty(value = "")
    private String type;

    /**
     * name
     */
    @ApiModelProperty(value = "")
    private String name;

    /**
     * code
     */
    @ApiModelProperty(value = "")
    private String code;

    /**
     * direction
     */
    @ApiModelProperty(value = "")
    private Integer direction;

    /**
     * status
     */
    @ApiModelProperty(value = "")
    private Integer status;

    /**
     * geom
     */
    @ApiModelProperty(value = "")
    private String geom;

}