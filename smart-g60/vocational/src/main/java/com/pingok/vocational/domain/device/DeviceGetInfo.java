package com.pingok.vocational.domain.device;


import javax.validation.constraints.Size;

/**
 * 设备显示信息
 *
 * @author ruoyi
 */
public class DeviceGetInfo {
    /** 项目类目 */
    private String deviceCategory;

    /** 设备编码 */
    private String deviceId;

    /** 设备名称 */
    private String deviceName;

    /** 安装地点 */
    private String installLocation;

    /** 所属路段 */
    private String roadBelong;

    /** 所属站 */
    private String stationBelong;

    /** 所属车道 */
    private String laneBelong;

    /** 设备IP */
    private String deviceIp;

    public String getDeviceCategory()
    {
        return deviceCategory;
    }

    public void setDeviceCategory(String deviceCategory) { this.deviceCategory = deviceCategory; }

    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    public void setDeviceName(String deviceName) { this.deviceName = deviceName; }

    public String getInstallLocation() { return installLocation; }

    public void setInstallLocation(String installLocation) { this.installLocation = installLocation; }

    public String getRoadBelong()
    {
        return roadBelong;
    }

    public void setRoadBelong(String roadBelong) { this.roadBelong = roadBelong; }

    public String getStationBelong()
    {
        return stationBelong;
    }

    public void setStationBelong(String stationBelong) { this.stationBelong = stationBelong; }

    public String getLaneBelong()
    {
        return laneBelong;
    }

    public void setLaneBelong(String laneBelong) { this.laneBelong = laneBelong; }

    public String getDeviceIp()
    {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) { this.deviceIp = deviceIp; }
}
