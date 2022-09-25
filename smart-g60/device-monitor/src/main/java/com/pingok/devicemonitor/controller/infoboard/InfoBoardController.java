package com.pingok.devicemonitor.controller.infoboard;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.service.infoboard.IInfoBoardService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/** 已废弃（2022-08-24）
 * @author
 * @time 2022/5/2 8:46
 */
@RestController
@Slf4j
@RequestMapping("/infoBoard")
public class InfoBoardController {

    @Autowired
    private IInfoBoardService iInfoBoardService;

    @GetMapping
    public AjaxResult collect() {
        iInfoBoardService.collect();
        return AjaxResult.success();
    }

    @PostMapping("/publish")
    public AjaxResult publish(@RequestBody JSONObject pubInfo) {
//        return AjaxResult.error();
        int ret = iInfoBoardService.publish(pubInfo);
        return ret == 200 ? AjaxResult.success() : AjaxResult.error();
    }

    @PostMapping("/notifyResult")
    public AjaxResult notifyResult(@RequestBody JSONObject result) {
        return AjaxResult.success(iInfoBoardService.notifyResult(result));
    }

    @PostMapping("/notifyPing")
    public AjaxResult notifyPing(@RequestBody JSONArray result) {
        return AjaxResult.success(iInfoBoardService.notifyPing(result));
    }
}
