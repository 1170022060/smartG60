package com.pingok.monitor.service.pilotLight;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;
import org.springframework.web.multipart.MultipartFile;

public interface IPilotLightService {

    /**
     * 修改用户密码 V1
     * @param body
     * @return
     */
    boolean passwordV1(JSONObject body);
    /**
     * 修改用户密码 V2
     * @param body
     * @return
     */
    boolean passwordV2(JSONObject body);

    /**
     * 查看用户信息
     * @return
     */
    boolean getUserInfo();

    /**
     * 更新用户信息 V1
     * @param body
     * @return
     */
    boolean updateUserInfoV1(JSONObject body);
    /**
     * 更新用户信息 V2
     * @param body
     * @return
     */
    boolean updateUserInfoV2(JSONObject body);

    /**
     * 查询用户管辖的所有系统列表 V1
     * @param body
     * @return
     */
    boolean getRoadInfoV1(JSONObject body);
    /**
     * 查询用户管辖的所有系统列表 V2
     * @param body
     * @return
     */
    boolean getRoadInfoV2(JSONObject body);

    /**
     * 根据系统 id 查询系统
     * @param roadId
     * @return
     */
    boolean getRoadInfoById(JSONObject body);

    /**
     * 查询系统及系统下的设备列表 V1
     * @param body
     * @return
     */
    boolean getRoadDeviceInfoV1(JSONObject body);

    /**
     * 查询系统及系统下的设备列表 V2
     * @param body
     * @return
     */
    boolean getRoadDeviceInfoV2(JSONObject body);

    /**
     * 查询某个系统下的设备列表 V1
     * @param body
     * @return
     */
    boolean getOneRoadDeviceInfoV1(JSONObject body);

    /**
     * 查询某个系统下的设备列表 V2
     * @param body
     * @return
     */
    boolean getOneRoadDeviceInfoV2(JSONObject body);

    /**
     * 查询用户所管辖的设备列表
     * @param body
     * @return
     */
    boolean getDeviceInfo(JSONObject body);

    /**
     * 向某个设备发送命令 V1
     * @param body
     * @return
     */
    boolean sendCmdToDeviceV1(JSONObject body);
    /**
     * 向某个设备发送命令 V2
     * @param body
     * @return
     */
    boolean sendCmdToDeviceV2(JSONObject body);

    /**
     * 根据系统获取设备的当前数据 V1
     * @param body
     * @return
     */
    boolean getRtStatusV1(JSONObject body);
    /**
     * 根据系统获取设备的当前数据 V2
     * @param body
     * @return
     */
    boolean getRtStatusV2(JSONObject body);

    /**
     * 根据设备 id 获取设备的历史运行状态记录 V1
     * @param body
     * @return
     */
    boolean getRecStatusV1(JSONObject body);
    /**
     * 根据设备 id 获取设备的历史运行状态记录 V2
     * @param body
     * @return
     */
    boolean getRecStatusV2(JSONObject body);

    /**
     * 外接入能见度数值下发给设备
     * @param body
     * @return
     */
    boolean setVisibility(JSONObject body);

    /**
     * 固件列表——查询所有固件信息
     * @return
     */
    boolean getFirmware();

    /**
     * 固件列表——删除某个固件
     * @param body
     * @return
     */
    boolean delFirmware(JSONObject body);

    /**
     * 修改状态——获取手机验证码
     * @return
     */
    boolean getCode();

    /**
     * 修改状态——提交表单
     * @param body
     * @return
     */
    boolean updateFirmware(JSONObject body);

    /**
     * 添加固件——上传文件
     * @param body
     * @return
     */
    boolean uploadFile(JSONObject body);

    /**
     * 固件升级——查询某个设备详情
     * @param body
     * @return
     */
    boolean getFirmwareDevice(JSONObject body);

    /**
     * 固件升级——提交表单
     * @param body
     * @return
     */
    boolean firmwareUpgrade(JSONObject body);

    /**
     * 版本分布——根据地区查找
     * @param body
     * @return
     */
    boolean firmwareVerByArea(JSONObject body);
}
