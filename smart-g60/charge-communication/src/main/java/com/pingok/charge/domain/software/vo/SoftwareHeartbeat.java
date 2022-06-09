package com.pingok.charge.domain.software.vo;

import lombok.Data;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author qiumin
 * @version 1.0.0 2022-03-24
 */
@Data
public class SoftwareHeartbeat implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 软件编码
     */
    private Long num;

    /**
     * 软件名称
     */
    private String name;

    /**
     * 版本号
     */
    private String version;

    /**
     * ip
     */
    private String ip;

    /**
     * 心跳时间
     */
    private Date time;

    /**
     * 运行状态：1-正常 0-异常
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusCode;

    /**
     * 上传标志位：1-有上传业务 0-无上传业务
     */
    private Integer uploadFlag;

    /**
     * 下载标志位：1-有下载业务 0-无下载业务
     */
    private Integer downloadFlag;

    /**
     * 软件ID
     */
    private Long softwareId;

    @Transient
    private List<SoftwareDownloadStatus> downloadStatus;

    @Transient
    private List<SoftwareUploadStatus> uploadStatus;


}