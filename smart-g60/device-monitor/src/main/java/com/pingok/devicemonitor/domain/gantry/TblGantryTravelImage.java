package com.pingok.devicemonitor.domain.gantry;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author
 * @time 2022/5/20 17:59
 */
@Data
@Table(name = "TBL_GANTRY_TRAVELIMAGE")
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

    /** 关联车头/车尾流水号 */
    private String cognateSerial;

    /** 全部关联流水号，多个用"|"分隔 */
    private String allCognateSerial;

    /** chargeUnitId */
    private String chargeUnitId;

    /** transDate */
    private String transDate;

    /** versionInfo */
    private String versionInfo;

    /** spare1 */
    private String spare1;

    /** spare2 */
    private String spare2;

    /** spare3 */
    private String spare3;

    /** spare4 */
    private String spare4;

    /** spare5 */
    private Long spare5;

    /** spare6 */
    private Long spare6;

    /** spare7 */
    private Long spare7;

    /** spare8 */
    private String spare8;

    /** type */
    private Integer type;

    /** rsuMatchResult */
    private Integer rsuMatchResult;

    /** rsuMatchTransId */
    private String rsuMatchTransId;

    /** rsuMatchReliability */
    private Integer rsuMatchReliability;
}
