package com.pingok.station.domain.bulkList;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class BulkRecord  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @Id
    private Integer autoID;

    /**
     * 大件运输许可证号
     */
    private String certNo;

    /**
     * 途径省份编码
     */
    private String provinces;

    /**
     * 入口收费站编号
     */
    private String enStationId;

    /**
     * 入口收费站名称
     */
    private String enStationName;

    /**
     * 出口收费站编号
     */
    private String exStationId;

    /**
     * 出口收费站名称
     */
    private String exStationName;

    /**
     * 牵引车号牌/临时行驶车辆号牌
     */
    private String tractorVehicleId;

    /**
     * 挂车号牌/临时行驶车辆号牌
     */
    private String trailerVehicleId;

    /**
     * 起始通行日期
     */
    private Date startPassDate;

    /**
     * 结束通行日期
     */
    private Date endPassDate;

    /**
     * 承运单位名称
     */
    private String carriorUnit;

    /**
     * 货物名称
     */
    private String goodsInfo;

    /**
     * 车货总质量
     */
    private Integer vehicleweight;

    /**
     * 车货总体外廓尺寸-长度（车货总长度）
     */
    private Integer vehiclelength;

    /**
     * 车货总体外廓尺寸-宽度（车货总宽度）
     */
    private Integer width;

    /**
     * 车货总体外廓尺寸-高度（车货总高度）
     */
    private Integer height;

    /**
     * 轴数
     */
    private Integer alexCount;

    /**
     * 轮胎数
     */
    private Integer tyleCount;

    /**
     * 各车轴轴荷
     */
    private String alexsLoad;

    /**
     * 通行路线
     */
    private String roads;

    /**
     * 通行次数
     */
    private Integer passCount;

    /**
     * 通行路线说明
     */
    private String descript;

    /**
     * 发证单位
     */
    private String orgUnit;

    /**
     * 发证日期
     */
    private String certificationDate;

    /**
     * 上传标志
     */
    private Integer UploadStationFlag;

    public Integer getAutoID() {
        return autoID;
    }

    public void setAutoID(Integer autoID) {
        this.autoID = autoID;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getProvinces() {
        return provinces;
    }

    public void setProvinces(String provinces) {
        this.provinces = provinces;
    }

    public String getEnStationId() {
        return enStationId;
    }

    public void setEnStationId(String enStationId) {
        this.enStationId = enStationId;
    }

    public String getEnStationName() {
        return enStationName;
    }

    public void setEnStationName(String enStationName) {
        this.enStationName = enStationName;
    }

    public String getExStationId() {
        return exStationId;
    }

    public void setExStationId(String exStationId) {
        this.exStationId = exStationId;
    }

    public String getExStationName() {
        return exStationName;
    }

    public void setExStationName(String exStationName) {
        this.exStationName = exStationName;
    }

    public String getTractorVehicleId() {
        return tractorVehicleId;
    }

    public void setTractorVehicleId(String tractorVehicleId) {
        this.tractorVehicleId = tractorVehicleId;
    }

    public String getTrailerVehicleId() {
        return trailerVehicleId;
    }

    public void setTrailerVehicleId(String trailerVehicleId) {
        this.trailerVehicleId = trailerVehicleId;
    }

    public Date getStartPassDate() {
        return startPassDate;
    }

    public void setStartPassDate(Date startPassDate) {
        this.startPassDate = startPassDate;
    }

    public Date getEndPassDate() {
        return endPassDate;
    }

    public void setEndPassDate(Date endPassDate) {
        this.endPassDate = endPassDate;
    }

    public String getCarriorUnit() {
        return carriorUnit;
    }

    public void setCarriorUnit(String carriorUnit) {
        this.carriorUnit = carriorUnit;
    }

    public String getGoodsInfo() {
        return goodsInfo;
    }

    public void setGoodsInfo(String goodsInfo) {
        this.goodsInfo = goodsInfo;
    }

    public Integer getVehicleweight() {
        return vehicleweight;
    }

    public void setVehicleweight(Integer vehicleweight) {
        this.vehicleweight = vehicleweight;
    }

    public Integer getVehiclelength() {
        return vehiclelength;
    }

    public void setVehiclelength(Integer vehiclelength) {
        this.vehiclelength = vehiclelength;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getAlexCount() {
        return alexCount;
    }

    public void setAlexCount(Integer alexCount) {
        this.alexCount = alexCount;
    }

    public Integer getTyleCount() {
        return tyleCount;
    }

    public void setTyleCount(Integer tyleCount) {
        this.tyleCount = tyleCount;
    }

    public String getAlexsLoad() {
        return alexsLoad;
    }

    public void setAlexsLoad(String alexsLoad) {
        this.alexsLoad = alexsLoad;
    }

    public String getRoads() {
        return roads;
    }

    public void setRoads(String roads) {
        this.roads = roads;
    }

    public Integer getPassCount() {
        return passCount;
    }

    public void setPassCount(Integer passCount) {
        this.passCount = passCount;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getOrgUnit() {
        return orgUnit;
    }

    public void setOrgUnit(String orgUnit) {
        this.orgUnit = orgUnit;
    }

    public String getCertificationDate() {
        return certificationDate;
    }

    public void setCertificationDate(String certificationDate) {
        this.certificationDate = certificationDate;
    }

    public Integer getUploadStationFlag() {
        return UploadStationFlag;
    }

    public void setUploadStationFlag(Integer uploadStationFlag) {
        UploadStationFlag = uploadStationFlag;
    }
}
