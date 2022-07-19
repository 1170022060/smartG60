package com.pingok.gantry.domain.entity.charge;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * tbl_gantry_travelimage
 * 
 * @author bianj
 * @version 1.0.0 2021-12-13
 */
@Getter
@Setter
@Table(name = "tbl_gantry_travelimage")
public class TblGantryTravelimage implements Serializable {

    private static final long serialVersionUID = 1L;

    /** picId */
    @Id
    private String picId;

    private String gantryId;

    private Integer gantryOrderNum;

    /**
     * ETC
     */
    private String gantryHex;

    private Date picTime;

    /**
     * 1
     */
    private Integer driveDir;

    private Integer cameraNum;

    private String hourBatchNo;

    /**
     * 1-
     */
    private Integer shootPosition;

    private String laneNum;

    private String vehiclePlate;

    private Integer vehicleSpeed;

    /**
     * ʶ
     */
    private Integer identifyType;

    private String vehicleModel;

    private String vehicleColor;

    /**
     * ͼƬ
     */
    private Integer imageSize;

    private Integer licenseImageSize;

    private Integer binImageSize;

    /**
     * 车牌可信度
     */
    private String reliability;

    private String vehFeatureCode;

    private String faceFeatureCode;

    private String verifyCode;

    /**
     * ƥ
     */
    private String tradeId;

    /**
     * 0-δ
     */
    private Integer matchStatus;

    /**
     * 0-δ
     */
    private Integer validStatus;

    /**
     * 0-δ
     */
    private Integer dealStatus;

    /**
     * 关联图片流水号
     */
    private String relatedPicId;

    /**
     * 全部关联图片流水号
     */
    private String allRelatedPicId;

    private Date stationDBTime;

    private Date stationDealTime;

    private Date stationValidTime;

    private Date stationMatchTime;

    private Integer uploadFlag;

    private Date uploadTime;

    private Integer lnduceFlag;

    private Date lnduceTime;

    /**
     * 关联车头/车尾流水号
     */
    private String cognateSerial;

    /**
     * 全部关联流水号，多个用"|"分隔
     */
    private String allCognateSerial;

    private Integer MUploadFlag;

    private Date MUploadTime;

    private String chargeUnitId;

    private String transDate;

    private String versionInfo;

    private String spare1;

    private String spare2;

    private String spare3;

    private String spare4;

    private Long spare5;

    private Long spare6;

    private Long spare7;

    private String spare8;

    /** type */
    private Integer type;

    /** ruploadflag */
    private String ruploadflag;

    /** ruploadtime */
    private Date ruploadtime;

    /** cuploadflag */
    private String cuploadflag;

    /** cuploadtime */
    private Date cuploadtime;

    /** rsuMatchResult */
    private Integer rsuMatchResult;

    /** rsuMatchTransId */
    private String rsuMatchTransId;

    /** rsuMatchReliability */
    private Integer rsuMatchReliability;

}