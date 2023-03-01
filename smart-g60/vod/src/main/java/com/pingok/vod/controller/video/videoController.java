package com.pingok.vod.controller.video;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.shaded.com.google.gson.JsonArray;
import com.pingok.vod.service.video.IVideoService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@RestController
@RequestMapping("/video")
public class videoController extends BaseController {
    @Autowired
    private IVideoService iVideoService;

    @Log(title = "获取车道实时视频url", businessType = BusinessType.OTHER)
    @RequiresPermissions("vod:video:playVideo")
    @PostMapping("/videoUrl")
    public AjaxResult getUrl(String deviceId, String protocol) {
        String cameraPreviewURL = null;
        try {
            //获取实时预览URL
            cameraPreviewURL = iVideoService.getVideoPreviewUrl(deviceId,protocol);
            JSONObject json = JSONObject.parseObject(cameraPreviewURL);
            String resultData = json.getString("data");
            JSONObject da = JSONObject.parseObject(resultData);
            if (json.getString("code").equals("0")) {
                return AjaxResult.success(da);
            } else {
                return AjaxResult.error();
            }
        } catch (Exception e) {
            return AjaxResult.error("获取视频url失败！");
        }
    }
    @Log(title = "获取车道录像",businessType = BusinessType.OTHER)
//    @RequiresPermissions("vod:video:playBackVideo")
    @PostMapping("/videoPlayBackUrl")
    public AjaxResult getPlayBackUrl(String deviceId, String protocol, String beginTime, String endTime) throws ParseException {
        String playBackUrl = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 =  df2.parse(beginTime);
        Date date2 =  df2.parse(endTime);
        try{
            playBackUrl = iVideoService.getCamerasPlayBackUrl(deviceId,protocol,df.format(date1),df.format(date2));
            JSONObject url = JSONObject.parseObject(playBackUrl);
            String resultData = url.getString("data");
            JSONObject da = JSONObject.parseObject(resultData);
            List<Map> list = null;
            if (url.getString("code").equals("0")) {
                da.put("beginTime",beginTime);
                da.put("endTime",endTime);
//                list.add(da);
                return AjaxResult.success(da);
            } else {
                return AjaxResult.error();
            }
        }catch (Exception e) {
            return AjaxResult.error("获取视频回放url失败！");
        }
    }
}
