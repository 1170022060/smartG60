package com.pingok.station.domain.emergList;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class EmgAppend implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long TransId;

    private String FVer;

    private String Id;

    private Integer DataType;

    private String AppointType;

    private String DiscountInfo;

    private Integer HandleType;

    private Integer CheckType;

    private Date AppointTime;

    private String VehicleSign;

    private Integer LaneHandle;

    private Integer VehicleIdentifyType;

    private String CardId;

    private String VehiclePlateId;

    private Integer VehiclePlateColor;

    private Integer Operation;

    private Integer DiscountType;

    private Float Discount;

    private Integer DiscountAmount;

    private Integer DiscountQuota;

    private Date StartTime;

    private Date EndTime;

    private String ProvinceIds;

    private String EnStation;

    private String ExStation;

    public Long getTransId() {
        return TransId;
    }

    public void setTransId(Long transId) {
        TransId = transId;
    }

    public String getFVer() {
        return FVer;
    }

    public void setFVer(String FVer) {
        this.FVer = FVer;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Integer getDataType() {
        return DataType;
    }

    public void setDataType(Integer dataType) {
        DataType = dataType;
    }

    public String getAppointType() {
        return AppointType;
    }

    public void setAppointType(String appointType) {
        AppointType = appointType;
    }

    public String getDiscountInfo() {
        return DiscountInfo;
    }

    public void setDiscountInfo(String discountInfo) {
        DiscountInfo = discountInfo;
    }

    public Integer getHandleType() {
        return HandleType;
    }

    public void setHandleType(Integer handleType) {
        HandleType = handleType;
    }

    public Integer getCheckType() {
        return CheckType;
    }

    public void setCheckType(Integer checkType) {
        CheckType = checkType;
    }

    public Date getAppointTime() {
        return AppointTime;
    }

    public void setAppointTime(Date appointTime) {
        AppointTime = appointTime;
    }

    public String getVehicleSign() {
        return VehicleSign;
    }

    public void setVehicleSign(String vehicleSign) {
        VehicleSign = vehicleSign;
    }

    public Integer getLaneHandle() {
        return LaneHandle;
    }

    public void setLaneHandle(Integer laneHandle) {
        LaneHandle = laneHandle;
    }

    public Integer getVehicleIdentifyType() {
        return VehicleIdentifyType;
    }

    public void setVehicleIdentifyType(Integer vehicleIdentifyType) {
        VehicleIdentifyType = vehicleIdentifyType;
    }

    public String getCardId() {
        return CardId;
    }

    public void setCardId(String cardId) {
        CardId = cardId;
    }

    public String getVehiclePlateId() {
        return VehiclePlateId;
    }

    public void setVehiclePlateId(String vehiclePlateId) {
        VehiclePlateId = vehiclePlateId;
    }

    public Integer getVehiclePlateColor() {
        return VehiclePlateColor;
    }

    public void setVehiclePlateColor(Integer vehiclePlateColor) {
        VehiclePlateColor = vehiclePlateColor;
    }

    public Integer getOperation() {
        return Operation;
    }

    public void setOperation(Integer operation) {
        Operation = operation;
    }

    public Integer getDiscountType() {
        return DiscountType;
    }

    public void setDiscountType(Integer discountType) {
        DiscountType = discountType;
    }

    public Float getDiscount() {
        return Discount;
    }

    public void setDiscount(Float discount) {
        Discount = discount;
    }

    public Integer getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        DiscountAmount = discountAmount;
    }

    public Integer getDiscountQuota() {
        return DiscountQuota;
    }

    public void setDiscountQuota(Integer discountQuota) {
        DiscountQuota = discountQuota;
    }

    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) {
        StartTime = startTime;
    }

    public Date getEndTime() {
        return EndTime;
    }

    public void setEndTime(Date endTime) {
        EndTime = endTime;
    }

    public String getProvinceIds() {
        return ProvinceIds;
    }

    public void setProvinceIds(String provinceIds) {
        ProvinceIds = provinceIds;
    }

    public String getEnStation() {
        return EnStation;
    }

    public void setEnStation(String enStation) {
        EnStation = enStation;
    }

    public String getExStation() {
        return ExStation;
    }

    public void setExStation(String exStation) {
        ExStation = exStation;
    }
}
