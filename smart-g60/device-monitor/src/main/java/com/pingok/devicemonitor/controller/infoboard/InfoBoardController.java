package com.pingok.devicemonitor.controller.infoboard;

import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.service.infoboard.IInfoBoardService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @time 2022/5/2 8:46
 */
@RestController
@Slf4j
@RequestMapping("/infoBoard")
public class InfoBoardController {

    @Autowired
    private IInfoBoardService iInfoBoardService;

    @PostMapping("/publish")
    public AjaxResult publish(@RequestBody JSONObject vmsPublishInfo) {
        iInfoBoardService.publish(vmsPublishInfo);
        return AjaxResult.success();
    }
}
