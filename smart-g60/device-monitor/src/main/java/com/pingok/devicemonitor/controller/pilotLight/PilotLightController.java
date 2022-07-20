package com.pingok.devicemonitor.controller.pilotLight;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author
 * @time 2022/5/11 17:28
 */
@Slf4j
@RestController
@RequestMapping("/pilotLight")
public class PilotLightController extends BaseController {

    @PostMapping("/user/password")
    public AjaxResult passwordV1(@RequestBody JSONObject body) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        body.put("kafkaType", "passwordV1");
        kafkaEnum.setData(JSON.toJSONString(body));
        return AjaxResult.success();
    }
    @PostMapping("/sys/user/update/password")
    public AjaxResult passwordV2(@RequestBody JSONObject body) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        body.put("kafkaType", "passwordV2");
        kafkaEnum.setData(JSON.toJSONString(body));
        return AjaxResult.success();
    }

    /*
    查看用户信息
     */
    @GetMapping("/user")
    public AjaxResult getUserInfo() {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "user");
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }

    /*
    修改用户信息
     */
    @PostMapping("/user")
    public AjaxResult updateUserInfoV1(@RequestBody JSONObject body) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        body.put("kafkaType", "updateUserV1");
        kafkaEnum.setData(JSON.toJSONString(body));
        return AjaxResult.success();
    }
    @PostMapping("/sys/user/update")
    public AjaxResult updateUserInfoV2(@RequestBody JSONObject body) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        body.put("kafkaType", "updateUserV2");
        kafkaEnum.setData(JSON.toJSONString(body));
        return AjaxResult.success();
    }

    /*
    查询用户管辖的系统
     */
    @GetMapping("/road")
    public AjaxResult getRoadInfoV1(@RequestParam("page") Integer page,
                                    @RequestParam("pageSize") Integer pageSize) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "roadV1");
        jo.put("page", page);
        jo.put("pageSize", pageSize);
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }
    @GetMapping("/road/v2")
    public AjaxResult getRoadInfoV2(@RequestParam("page") Integer page,
                                    @RequestParam("pageSize") Integer pageSize) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "roadV2");
        jo.put("page", page);
        jo.put("pageSize", pageSize);
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }

    /*
    根据系统id查询系统
     */
    @GetMapping("/road/{roadId}")
    public AjaxResult getRoadInfoById(@PathVariable Integer roadId) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "roadById");
        jo.put("roadId", roadId);
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }

    /*
    查询系统设备
     */
    @GetMapping("/roadList/deviceList")
    public AjaxResult getRoadDeviceInfoV1(@RequestParam("page") Integer page,
                                        @RequestParam("pageSize") Integer pageSize) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "deviceListV1");
        jo.put("page", page);
        jo.put("pageSize", pageSize);
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }
    @GetMapping("/sys/roadList/deviceList")
    public AjaxResult getRoadDeviceInfoV2(@RequestParam("page") Integer page,
                                          @RequestParam("pageSize") Integer pageSize) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "deviceListV2");
        jo.put("page", page);
        jo.put("pageSize", pageSize);
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }

    /*
    查询某个系统设备
     */
    @GetMapping("/road/{roadId}/device")
    public AjaxResult getOneRoadDeviceInfoV1(@PathVariable Integer roadId,
                                             @RequestParam("page") Integer page,
                                             @RequestParam("pageSize") Integer pageSize) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "oneDeviceListV1");
        jo.put("roadId", roadId);
        jo.put("page", page);
        jo.put("pageSize", pageSize);
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }
    @GetMapping("/road/device/show/list")
    public AjaxResult getOneRoadDeviceInfoV2(@RequestParam("roadId") Integer roadId) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "oneDeviceListV2");
        jo.put("roadId", roadId);
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }

    /*
    查询用户管辖的设备列表
     */
    @GetMapping("/device/list")
    public AjaxResult getDeviceInfo(@RequestParam("page") Integer page,
                                    @RequestParam("pageSize") Integer pageSize) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "userDeviceList");
        jo.put("page", page);
        jo.put("pageSize", pageSize);
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }

    /*
    发送指令
     */
    @PostMapping("/device/command/{deviceId}")
    public AjaxResult sendCmdToDeviceV1(@PathVariable String deviceId,
                                        @RequestBody JSONObject body) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        body.put("kafkaType", "commandSendV1");
        body.put("deviceId", deviceId);
        kafkaEnum.setData(JSON.toJSONString(body));
        return AjaxResult.success();
    }

    @PostMapping("/command/device/send")
    public AjaxResult sendCmdToDeviceV2(@RequestBody JSONObject body) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        body.put("kafkaType", "commandSendV2");
        kafkaEnum.setData(JSON.toJSONString(body));
        return AjaxResult.success();
    }

    /*
    获取设备当前数据
     */
    @GetMapping("/device/data/dynamic/{deviceId}/{localId}")
    public AjaxResult getRtStatusV1(@PathVariable String deviceId,
                                  @PathVariable String localId) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "runStatusV1");
        jo.put("deviceId", deviceId);
        jo.put("localId", localId);
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }
    @GetMapping("/fogArea/sys/get")
    public AjaxResult getRtStatusV2(@RequestParam("roadId") Integer roadId) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "runStatusV2");
        jo.put("roadId", roadId);
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }

    /*
    获取设备历史数据
     */
    @GetMapping("/road/device/data/dynamic")
    public AjaxResult getRecStatusV1(@RequestParam("deviceId") Integer deviceId,
                                     @RequestParam("localId") Integer localId,
                                     @RequestParam("specification") Integer specification,
                                     @RequestParam("page") Integer page,
                                     @RequestParam("pageSize") Integer pageSize,
                                     @RequestParam("startTime") String startTime,
                                     @RequestParam("endTime") String endTime) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "recStatusV1");
        jo.put("deviceId", deviceId);
        jo.put("localId", localId);
        jo.put("specification", specification);
        jo.put("page", page);
        jo.put("pageSize", pageSize);
        jo.put("startTime", startTime);
        jo.put("endTime", endTime);
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }
    @GetMapping("/edgeClusterStateDat")
    public AjaxResult getRecStatusV2(@RequestParam("deviceId") Integer deviceId,
                                     @RequestParam("day") Integer day) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "recStatusV2");
        jo.put("deviceId", deviceId);
        jo.put("day", day);
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }

    /*
    下发能见度
     */
    @GetMapping("/system/ext/vis")
    public AjaxResult setVisibility(@RequestParam("deviceId") Integer deviceId,
                                    @RequestParam("visibility") Integer visibility) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "visibility");
        jo.put("deviceId", deviceId);
        jo.put("visibility", visibility);
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }

    /*
    查询所有固件
     */
    @GetMapping("/firmware")
    public AjaxResult getFirmware() {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "getFirmware");
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }

    /*
    删除固件
     */
    @GetMapping("/firmware/del")
    public AjaxResult delFirmware(@RequestParam("id") Integer id) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "delFirmware");
        jo.put("id", id);
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }

    /*
    修改状态-获取手机验证码
     */
    @GetMapping("/firmware/state/verificationCode")
    public AjaxResult getCode() {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "getCode");
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }

    /*
    修改状态-提交表单
     */
    @PostMapping("/firmware")
    public AjaxResult updateFirmware(@RequestBody JSONObject body) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        body.put("kafkaType", "updateFirmware");
        kafkaEnum.setData(JSON.toJSONString(body));
        return AjaxResult.success();
    }

    /*
    添加固件-上传文件
     */
    @PostMapping("/firmware/uploadFile")
    public AjaxResult uploadFile(@RequestParam("uploadFile") MultipartFile file) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "uploadFile");
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }

    /*
    固件升级-查询设备详情
     */
    @GetMapping("/firmware/upgrade/device")
    public AjaxResult getFirmwareDevice(@RequestParam("deviceId") Integer deviceId) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "firmwareDevice");
        jo.put("deviceId", deviceId);
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }

    /*
    固件升级-提交表单
     */
    @PostMapping("/firmware/upgrade")
    public AjaxResult firmwareUpgrade(@RequestBody JSONObject body) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        body.put("kafkaType", "firmwareUpgrade");
        kafkaEnum.setData(JSON.toJSONString(body));
        return AjaxResult.success();
    }

    /*
    版本分布-根据地区查找
     */
    @GetMapping("/firmware/area")
    public AjaxResult firmwareVerByArea(@RequestParam("provice") String provice,
                                        @RequestParam("city") String city,
                                        @RequestParam("county") String county) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject jo = new JSONObject();
        jo.put("kafkaType", "firmwareArea");
        jo.put("provice", provice);
        jo.put("city", city);
        jo.put("county", county);
        kafkaEnum.setData(JSON.toJSONString(jo));
        return AjaxResult.success();
    }
}
