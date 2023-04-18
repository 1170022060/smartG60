package com.pingok.vod.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.vod.domain.TblMonitorPreset;
import com.pingok.vod.service.IDeviceHeartbeatService;
import com.pingok.vod.service.IDeviceInfoService;
import com.pingok.vod.service.IMonitorPresetService;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 视频 信息操作处理
 *
 * @author qiumin
 */
@RestController
@Slf4j
@RequestMapping("/vod")
public class VodController extends BaseController {

    @Autowired
    private IDeviceInfoService iDeviceInfoService;
    @Autowired
    private IMonitorPresetService iMonitorPresetService;
    @Autowired
    private IDeviceHeartbeatService iDeviceHeartbeatService;

    @GetMapping("/downloadVod")
    public AjaxResult downloadVod(HttpServletResponse response, String url) {
        if (StringUtils.isEmpty(url)) {
            return AjaxResult.error("url不能为空");
        }
        iMonitorPresetService.downloadVod(response, url);
        return AjaxResult.success();
    }

    @PostMapping("/heartbeat")
    public AjaxResult heartbeat() {
        JSONArray array = iMonitorPresetService.getCameraStatus();
        iDeviceHeartbeatService.heartbeat(array);
        return AjaxResult.success();
    }

    //    @RequiresPermissions("vod:vod:getVodCurtime")
//    @Log(title = "视频服务", businessType = BusinessType.OTHER)
    @GetMapping("/getVodCurtime")
    public AjaxResult getVodCurtime(@RequestParam Long id) {
        JSONObject r = iMonitorPresetService.getVodCurtime(id);
        if (r.getString("code").equals("0")) {
            r.remove("curTime");
            return AjaxResult.success(r);
        } else {
            return AjaxResult.error(r.getString("msg"));
        }
    }

    //    @RequiresPermissions("vod:vod:vodControl")
//    @Log(title = "视频服务", businessType = BusinessType.OTHER)
    @GetMapping("/vodControl")
    public AjaxResult vodControl(@RequestParam Long id, @RequestParam String type, String playSpeed, String seekTime) {
        JSONObject r = iMonitorPresetService.vodControl(id, type, playSpeed, seekTime);
        if (r.getString("code").equals("0")) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error(r.getString("msg"));
        }
    }

    //    @RequiresPermissions("vod:vod:stopVod")
//    @Log(title = "视频服务", businessType = BusinessType.OTHER)
    @GetMapping("/stopVod")
    public AjaxResult stopVod(@RequestParam Long id) {
        JSONObject r = iMonitorPresetService.stopVod(id);
        if (r.getString("code").equals("0")) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error(r.getString("msg"));
        }
    }

    //    @RequiresPermissions("vod:vod:startVod")
//    @Log(title = "视频服务", businessType = BusinessType.OTHER)
    @GetMapping("/startVod")
    public AjaxResult startVod(@RequestParam Long id, @RequestParam String start, @RequestParam String end) {
        JSONObject r = iMonitorPresetService.startVod(id, start, end);
        if (r.getString("code").equals("0")) {
            r.remove("code");
            r.remove("msg");
            return AjaxResult.success(r);
        } else {
            return AjaxResult.error(r.getString("msg"));
        }
    }

    //    @RequiresPermissions("vod:vod:getRecordList")
//    @Log(title = "视频服务", businessType = BusinessType.OTHER)
    @GetMapping("/getRecordList")
    public AjaxResult getRecordList(@RequestParam Long id, @RequestParam String startTime, @RequestParam String endTime) {
        return AjaxResult.success(iMonitorPresetService.getRecordList(id, startTime, endTime));
    }

    //    @RequiresPermissions("vod:vod:ptzControl")
//    @Log(title = "视频服务", businessType = BusinessType.OTHER)
    @GetMapping("/ptzControl")
    public AjaxResult ptzControl(@RequestParam Long id, @RequestParam String type, @RequestParam String param) {
        JSONObject r = iMonitorPresetService.ptzControl(id, type, param);
        if (r.getString("code").equals("0")) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error(r.getString("msg"));
        }
    }

    @PutMapping("/streamAlive")
    public AjaxResult streamAlive(@Validated @RequestBody TblMonitorPreset tblMonitorPreset) {
        iMonitorPresetService.streamAlive(tblMonitorPreset.getIds());
        return AjaxResult.success();
    }

    //    @RequiresPermissions("vod:vod:stopLive")
//    @Log(title = "视频服务", businessType = BusinessType.OTHER)
    @PostMapping("/stopLive")
    public AjaxResult stopLive(@Validated @RequestBody TblMonitorPreset tblMonitorPreset) {
        iMonitorPresetService.stopLive(tblMonitorPreset.getIds());
        return AjaxResult.success();
    }

    //    @RequiresPermissions("vod:vod:startLive")
//    @Log(title = "视频服务", businessType = BusinessType.OTHER)
    @PostMapping("/startLive")
    public AjaxResult startLive(@Validated @RequestBody TblMonitorPreset tblMonitorPreset) {
        return AjaxResult.success(iMonitorPresetService.startLive(tblMonitorPreset.getIds()));
    }

    //    @RequiresPermissions("vod:vod:findAll")
//    @Log(title = "视频服务", businessType = BusinessType.OTHER)
    @GetMapping
    public AjaxResult findAll() {
        return AjaxResult.success(iDeviceInfoService.findAllByType());
    }


    //    @RequiresPermissions("vod:vod:bind")
//    @Log(title = "视频服务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult bind(@Validated @RequestBody TblMonitorPreset tblMonitorPreset) {
        iMonitorPresetService.bind(tblMonitorPreset);
        return AjaxResult.success();
    }

    //    @RequiresPermissions("vod:vod:findByUserId")
//    @Log(title = "视频服务", businessType = BusinessType.OTHER)
    @GetMapping("/findByUserId")
    public AjaxResult findByUserId() {
        return AjaxResult.success(iMonitorPresetService.findByUserId());
    }

    //    @RequiresPermissions("vod:vod:updateCameraList")
//    @Log(title = "视频服务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult updateCameraList() {
        JSONArray cameraList = iMonitorPresetService.getCameraList();
        iDeviceInfoService.updateCameraList(cameraList);
        return AjaxResult.success();
    }

    @GetMapping("getStreamList")
    public AjaxResult getStreamList(Integer roadId) {
        return AjaxResult.success(iMonitorPresetService.getCameraStreamList(roadId));
    }
    @GetMapping("stop")
    public AjaxResult stop(Integer roadId) {
        iMonitorPresetService.stop(roadId);
        return AjaxResult.success();
    }
}
