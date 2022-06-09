package com.pingok.external.domain.bridge;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 桥梁采集仪信息表(TBL_BRIDGE_COLLECTION)
 *
 * @author qiumin
 * @version 1.0.0 2022-05-26
 */
@Data
@Table(name = "TBL_BRIDGE_COLLECTION")
public class TblBridgeCollection implements Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 6637023660084318924L;


    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 结构物id
     */
    private Long structureId;

    /**
     * 采集仪编号
     */
    private String acquisitionNo;

    /**
     * 通道数量
     */
    private Integer channelNum;

    /**
     * 设备状况 0-在线 1-离线
     */
    private Integer status;

    /**
     * 型号名称
     */
    private String modelName;

    /**
     * 型号编码
     */
    private String modelCode;

    /**
     * 数据是否第三方平台 0-否 1-是
     */
    private Integer isThirdParty;

    /**
     * 原理介绍
     */
    private String principle;

    /**
     * 设备图片
     */
    private String devicePicture;

    /**
     * 厂家名称
     */
    private String manufacturerName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}