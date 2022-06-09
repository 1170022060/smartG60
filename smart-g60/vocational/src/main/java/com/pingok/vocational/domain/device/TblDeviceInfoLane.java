package com.pingok.vocational.domain.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 车道设备基础信息表 TBL_DEVICE_INFO_LANE
 *
 * @author ruoyi
 */
@Table(name = "TBL_DEVICE_INFO_LANE")
public class TblDeviceInfoLane {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private Long itemName;

    /** 立项时间 */
    @Excel(name = "立项时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date itemListTime;

    /** 项目类目 */
    @Excel(name = "项目类目")
    private Long deviceCategory;

    /** 设备编码 */
    @Excel(name = "设备编码")
    private String deviceId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 设备品牌 */
    @Excel(name = "设备品牌")
    private String deviceBrand;

    /** 设备型号 */
    @Excel(name = "设备型号")
    private String deviceModel;

    /** 技术参数 */
    @Excel(name = "技术参数")
    private String techPara;

    /** 厂商 */
    @Excel(name = "厂商")
    private String manufacturer;

    /** 购买日期 */
    @Excel(name = "购买日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date purchaseDate;

    /** 购买日期 */
    @Excel(name = "安装日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date installDate;

    /** 质保期限(月) */
    @Excel(name = "质保期限(月)")
    private Integer warrantyPeriod;

    /** 使用期限(月) */
    @Excel(name = "使用期限(月)")
    private Integer serviceLife;

    /** 管理方 */
    @Excel(name = "管理方")
    private Long managementSide;

    /** 使用方 */
    @Excel(name = "使用方")
    private Long userSide;

    /** 行政区域 */
    @Excel(name = "行政区域")
    private String adminArea;

    /** 起始路名 */
    @Excel(name = "起始路名")
    private String startRoad;

    /** 安装地点 */
    @Excel(name = "安装地点")
    private Long fieldBelong;

    /** 城建坐标 */
    @Excel(name = "城建坐标")
    private String urbanConstrCoordin;

    /** 设备IP */
    @Excel(name = "设备IP")
    private String deviceIp;

    /** 管理IP */
    @Excel(name = "管理IP")
    private String manageIp;

    /** 端口号 */
    @Excel(name = "端口号")
    private Integer port;

    /** 桩号 */
    @Excel(name = "桩号")
    private String pileNo;

    /** 设备状态 */
    @Excel(name = "设备状态")
    private Integer status;

    /** 设备照片 */
    @Excel(name = "设备照片")
    private String devicePhoto;

    /** 养护标准 */
    @Excel(name = "养护标准")
    private String maintainStandard;

    /** 所属路段 */
    @Excel(name = "所属路段")
    private String roadBelong;

    /** 所属站 */
    @Excel(name = "所属站")
    private String stationBelong;

    /** 所属车道 */
    @Excel(name = "所属车道")
    private String laneBelong;

    /** 方向：1.上行 2.下行 */
    @Excel(name = "方向",readConverterExp = "1=上行,2=下行")
    private Integer direction;

    /** 监控级别：1.中心 2.集中站 3.收费站 */
    @Excel(name = "监控级别",readConverterExp = "1=中心,2=集中站,3=收费站")
    private Integer monitorLevel;

    /** GPS位置 */
    @Excel(name = "GPS位置")
    private String gps;

    /** 创建时间 */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 创建用户ID */
    @Excel(name = "创建用户工号")
    private Long createUserId;

    /** 更新用户ID */
    @Excel(name = "更新用户ID")
    private Long updateUserId;

    /** 备注*/
    @Excel(name = "备注")
    private String remark;


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getItemName()
    {
        return itemName;
    }

    public void setItemName(Long itemName)
    {
        this.itemName = itemName;
    }

    public Date getItemListTime()
    {
        return itemListTime;
    }

    public void setItemListTime(Date itemListTime)
    {
        this.itemListTime = itemListTime;
    }

    public Long getDeviceCategory()
    {
        return deviceCategory;
    }

    public void setDeviceCategory(Long deviceCategory) { this.deviceCategory = deviceCategory; }

    @Size(min = 0, max = 100, message = "设备编码长度不能超过100个字符")
    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    @Size(min = 0, max = 255, message = "设备名称长度不能超过255个字符")
    public String getDeviceName()
    {
        return deviceName;
    }

    public void setDeviceName(String deviceName)
    {
        this.deviceName = deviceName;
    }

    @Size(min = 0, max = 255, message = "设备品牌长度不能超过255个字符")
    public String getDeviceBrand()
    {
        return deviceBrand;
    }

    public void setDeviceBrand(String deviceBrand)
    {
        this.deviceBrand = deviceBrand;
    }

    @Size(min = 0, max = 255, message = "设备型号长度不能超过255个字符")
    public String getDeviceModel()
    {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel)
    {
        this.deviceModel = deviceModel;
    }

    @Size(min = 0, max = 255, message = "技术参数长度不能超过255个字符")
    public String getTechPara()
    {
        return techPara;
    }

    public void setTechPara(String techPara)
    {
        this.techPara = techPara;
    }

    @Size(min = 0, max = 255, message = "厂商长度不能超过255个字符")
    public String getManufacturer()
    {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }

    public Date getPurchaseDate()
    {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate)
    {
        this.purchaseDate = purchaseDate;
    }

    public Date getInstallDate()
    {
        return installDate;
    }

    public void setInstallDate(Date installDate) { this.installDate = installDate; }

    public Integer getWarrantyPeriod()
    {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(Integer warrantyPeriod) { this.warrantyPeriod = warrantyPeriod; }

    public Integer getServiceLife()
    {
        return serviceLife;
    }

    public void setServiceLife(Integer serviceLife) { this.serviceLife = serviceLife; }

    public Long getManagementSide()
    {
        return managementSide;
    }

    public void setManagementSide(Long managementSide) { this.managementSide = managementSide; }

    public Long getUserSide()
    {
        return userSide;
    }

    public void setUserSide(Long userSide) { this.userSide = userSide; }

    @Size(min = 0, max = 50, message = "行政区域长度不能超过50个字符")
    public String getAdminArea()
    {
        return adminArea;
    }

    public void setAdminArea(String adminArea) { this.adminArea = adminArea; }

    @Size(min = 0, max = 100, message = "起始路名长度不能超过100个字符")
    public String getStartRoad()
    {
        return startRoad;
    }

    public void setStartRoad(String startRoad) { this.startRoad = startRoad; }

    public Long getFieldBelong() { return fieldBelong; }

    public void setFieldBelong(Long fieldBelong) { this.fieldBelong = fieldBelong; }

    @Size(min = 0, max = 30, message = "城建坐标长度不能超过30个字符")
    public String getUrbanConstrCoordin()
    {
        return urbanConstrCoordin;
    }

    public void setUrbanConstrCoordin(String urbanConstrCoordin) { this.urbanConstrCoordin = urbanConstrCoordin; }

    @Size(min = 0, max = 20, message = "设备IP不能超过20个字符")
    public String getDeviceIp()
    {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) { this.deviceIp = deviceIp; }

    @Size(min = 0, max = 20, message = "管理IP不能超过20个字符")
    public String getManageIp()
    {
        return manageIp;
    }

    public void setManageIp(String manageIp) { this.manageIp = manageIp; }

    public Integer getPort()
    {
        return port;
    }

    public void setPort(Integer port) { this.port = port; }

    @Size(min = 0, max = 20, message = "桩号不能超过20个字符")
    public String getPileNo()
    {
        return pileNo;
    }

    public void setPileNo(String pileNo) { this.pileNo = pileNo; }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status) { this.status = status; }

    @Size(min = 0, max = 255, message = "设备照片不能超过255个字符")
    public String getDevicePhoto()
    {
        return devicePhoto;
    }

    public void setDevicePhoto(String devicePhoto) { this.devicePhoto = devicePhoto; }

    @Size(min = 0, max = 255, message = "养护标准不能超过255个字符")
    public String getMaintainStandard()
    {
        return maintainStandard;
    }

    public void setMaintainStandard(String maintainStandard) { this.maintainStandard = maintainStandard; }

    @Size(min = 0, max = 2, message = "所属路段不能超过2个字符")
    public String getRoadBelong()
    {
        return roadBelong;
    }

    public void setRoadBelong(String roadBelong) { this.roadBelong = roadBelong; }

    @Size(min = 0, max = 4, message = "所属站不能超过4个字符")
    public String getStationBelong()
    {
        return stationBelong;
    }

    public void setStationBelong(String stationBelong) { this.stationBelong = stationBelong; }

    @Size(min = 0, max = 6, message = "所属车道不能超过6个字符")
    public String getLaneBelong()
    {
        return laneBelong;
    }

    public void setLaneBelong(String laneBelong) { this.laneBelong = laneBelong; }

    public Integer getDirection()
    {
        return direction;
    }

    public void setDirection(Integer direction) { this.direction = direction; }

    public Integer getMonitorLevel()
    {
        return monitorLevel;
    }

    public void setMonitorLevel(Integer monitorLevel) { this.monitorLevel = monitorLevel; }

    @Size(min = 0, max = 100, message = "GPS位置不能超过100个字符")
    public String getGps()
    {
        return gps;
    }

    public void setGps(String gps) { this.gps = gps; }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }

    public Long getCreateUserId()
    {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) { this.createUserId = createUserId; }

    public Long getUpdateUserId()
    {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) { this.updateUserId = updateUserId; }

    @Size(min = 0, max = 255, message = "备注长度不能超过255个字符")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("itemName",getItemName())
                .append("itemListTime",getItemListTime())
                .append("deviceCategory",getDeviceCategory())
                .append("deviceId",getDeviceId())
                .append("deviceName",getDeviceName())
                .append("deviceBrand",getDeviceBrand())
                .append("deviceModel",getDeviceModel())
                .append("techPara",getTechPara())
                .append("manufacturer",getManufacturer())
                .append("purchaseDate",getPurchaseDate())
                .append("installDate",getInstallDate())
                .append("warrantyPeriod", getWarrantyPeriod())
                .append("serviceLife",getServiceLife())
                .append("managementSide", getManagementSide())
                .append("userSide",getUserSide())
                .append("adminArea",getAdminArea())
                .append("startRoad",getStartRoad())
                .append("fieldBelong",getFieldBelong())
                .append("urbanConstrCoordin",getUrbanConstrCoordin())
                .append("deviceIp",getDeviceIp())
                .append("manageIp",getManageIp())
                .append("port",  getPort())
                .append("pileNo", getPileNo())
                .append("status", getStatus())
                .append("devicePhoto",getDevicePhoto())
                .append("maintainStandard",getMaintainStandard())
                .append("roadBelong",getRoadBelong())
                .append("stationBelong",getStationBelong())
                .append("laneBelong",getLaneBelong())
                .append("direction",getDirection())
                .append("monitorLevel",getMonitorLevel())
                .append("gps",getGps())
                .append("createTime",getCreateTime())
                .append("updateTime",getUpdateTime())
                .append("createUserId",getCreateUserId())
                .append("updateUserId",getUpdateUserId())
                .append("remark", getRemark())
                .toString();
    }
}
