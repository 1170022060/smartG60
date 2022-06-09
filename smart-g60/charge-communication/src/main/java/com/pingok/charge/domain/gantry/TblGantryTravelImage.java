package com.pingok.charge.domain.gantry;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * tbl_gantry_travelimage
 * 
 * @author bianj
 * @version 1.0.0 2022-05-19
 */
@Data
@Table(name = "tbl_gantry_travelimage")
public class TblGantryTravelImage implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1L;

    /** picId */
    @Id
    private String picId;

    /** gantryId */
    private String gantryId;

    /** gantryOrderNum */
    private Integer gantryOrderNum;

    /** ETC */
    private String gantryHex;

    /** picTime */
    private Date picTime;

    /** 1 */
    private Integer driveDir;

    /** cameraNum */
    private Integer cameraNum;

    /** hourBatchNo */
    private String hourBatchNo;

    /** 1- */
    private Integer shootPosition;

    /** laneNum */
    private String laneNum;

    /** vehiclePlate */
    private String vehiclePlate;

    /** vehicleSpeed */
    private Integer vehicleSpeed;

    /** ʶ */
    private Integer identifyType;

    /** vehicleModel */
    private String vehicleModel;

    /** vehicleColor */
    private String vehicleColor;

    /** ͼƬ */
    private Integer imageSize;

    /** licenseImageSize */
    private Integer licenseImageSize;

    /** binImageSize */
    private Integer binImageSize;

    /** 车牌可信度 */
    private String reliability;

    /** vehFeatureCode */
    private String vehFeatureCode;

    /** faceFeatureCode */
    private String faceFeatureCode;

    /** verifyCode */
    private String verifyCode;

    /** ƥ */
    private String tradeId;

    /** 0-δ */
    private Integer matchStatus;

    /** 0-δ */
    private Integer validStatus;

    /** 0-δ */
    private Integer dealStatus;

    /** 关联图片流水号 */
    private String relatedPicId;

    /** 全部关联图片流水号 */
    private String allRelatedPicId;

    /** stationDbtime */
    private Date stationDbtime;

    /** stationDealTime */
    private Date stationDealTime;

    /** stationValidTime */
    private Date stationValidTime;

    /** stationMatchTime */
    private Date stationMatchTime;

}