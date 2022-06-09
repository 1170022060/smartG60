package com.pingok.charge.controller.software;

import com.pingok.charge.domain.software.vo.SoftwareHeartbeat;
import com.pingok.charge.service.software.ISoftwareInfoService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 软件 信息操作处理
 *
 * @author qiumin
 */
@RestController
@Slf4j
@RequestMapping("/softwareInfo")
public class SoftwareInfoController extends BaseController {
    @Autowired
    private ISoftwareInfoService iSoftwareInfoService;


    @PostMapping
    public AjaxResult heartbeat(@Validated @RequestBody SoftwareHeartbeat softwareHeartbeat) {
        log.info("heartbeat----请求参数-----" + softwareHeartbeat);
        if (softwareHeartbeat.getNum() == null) {
            return AjaxResult.error("num不能为空");
        }
        if (softwareHeartbeat.getName() == null) {
            return AjaxResult.error("name不能为空");
        }
        if (softwareHeartbeat.getIp() == null) {
            return AjaxResult.error("ip不能为空");
        }
        if (softwareHeartbeat.getStatus() == null) {
            return AjaxResult.error("status不能为空");
        }
        if (softwareHeartbeat.getUploadFlag() == null) {
            return AjaxResult.error("uploadFlag不能为空");
        }
        if (softwareHeartbeat.getDownloadFlag() == null) {
            return AjaxResult.error("downloadFlag不能为空");
        }
        iSoftwareInfoService.heartbeat(softwareHeartbeat);
        return AjaxResult.success();
    }
}
