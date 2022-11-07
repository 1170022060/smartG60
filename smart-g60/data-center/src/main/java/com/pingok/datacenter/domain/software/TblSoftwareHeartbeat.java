package com.pingok.datacenter.domain.software;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 软件心跳表(TBL_SOFTWARE_HEARTBEAT)
 *
 * @author qiumin
 * @version 1.0.0 2022-03-24
 */
@Data
@Table(name = "TBL_SOFTWARE_HEARTBEAT")
public class TblSoftwareHeartbeat  implements Serializable {

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 软件编码
     */
    private String num;

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

    /**
     * 牌识上传及时率
     */
    private Double promptnessLpr;

    /**
     * 交易上传及时率
     */
    private Double promptnessTrans;

    @Transient
    private List<TblSoftwareDownloadStatus> downloadStatus;

    @Transient
    private List<TblSoftwareUploadStatus> uploadStatus;



}