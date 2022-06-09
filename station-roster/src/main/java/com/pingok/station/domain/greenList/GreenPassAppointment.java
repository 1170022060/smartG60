package com.pingok.station.domain.greenList;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class GreenPassAppointment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String Id;

    private Date ApointTime;

    private String Phone;

    private Integer VehicleClass;

    private String PlateNum;

    private Integer PlateColor;

    private String StartTransYMDH;

    private Date SubmitTime;

    private String StartDistrictId;

    private String EndDistrictId;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public Date getApointTime() {
        return ApointTime;
    }

    public void setApointTime(Date apointTime) {
        ApointTime = apointTime;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public Integer getVehicleClass() {
        return VehicleClass;
    }

    public void setVehicleClass(Integer vehicleClass) {
        VehicleClass = vehicleClass;
    }

    public String getPlateNum() {
        return PlateNum;
    }

    public void setPlateNum(String plateNum) {
        PlateNum = plateNum;
    }

    public Integer getPlateColor() {
        return PlateColor;
    }

    public void setPlateColor(Integer plateColor) {
        PlateColor = plateColor;
    }

    public String getStartTransYMDH() {
        return StartTransYMDH;
    }

    public void setStartTransYMDH(String startTransYMDH) {
        StartTransYMDH = startTransYMDH;
    }

    public Date getSubmitTime() {
        return SubmitTime;
    }

    public void setSubmitTime(Date submitTime) {
        SubmitTime = submitTime;
    }

    public String getStartDistrictId() {
        return StartDistrictId;
    }

    public void setStartDistrictId(String startDistrictId) {
        StartDistrictId = startDistrictId;
    }

    public String getEndDistrictId() {
        return EndDistrictId;
    }

    public void setEndDistrictId(String endDistrictId) {
        EndDistrictId = endDistrictId;
    }
}
