package com.pingok.external.controller.artemis;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.shaded.com.google.gson.JsonObject;
import com.pingok.external.service.artemis.IArtemisService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 海康 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/artemis")
public class ArtemisController extends BaseController {

    @Autowired
    private IArtemisService iArtemisService;

    @GetMapping()
    public AjaxResult updateStatus() {
        iArtemisService.updateStatus();
        return AjaxResult.success();
    }

    @PostMapping("/getUrl")
    public AjaxResult getVideoUrl(String deviceId){
        try {
            //获取实时预览URL
            String result = iArtemisService.getVideoUrl(deviceId);
            JSONObject json = JSONObject.parseObject(result);
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
}
