package com.pingok.charge.domain.software.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author qiumin
 * @version 1.0.0 2022-03-24
 */
@Data
public class SoftwareUploadStatus implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 心跳ID
     */
    private Long heartbeatId;

    /**
     * 数据类型：1-ETC数据 2-MTC数据 3-其他数据
     */
    private Integer type;

    /**
     * 上传目标：1-部中心 2-省中心 3-路段中心
     */
    private Integer target;

    /**
     * 上传状态：1-正常 0-异常
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusCode;

    /**
     * 上传失败数据条数
     */
    private Integer failed;
    /**
     * 1-入口 2-出口
     */
    private Integer orientation;

    /**
     * 上传时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

}