package com.pingok.datacenter.controller.software;

import com.pingok.datacenter.domain.software.TblSoftwareHeartbeat;
import com.pingok.datacenter.service.software.ISoftwareInfoService;
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
    public AjaxResult heartbeat(@Validated @RequestBody TblSoftwareHeartbeat tblSoftwareHeartbeat) {
        if (tblSoftwareHeartbeat.getNum() == null) {
            return AjaxResult.error("num不能为空");
        }
        if (tblSoftwareHeartbeat.getName() == null) {
            return AjaxResult.error("name不能为空");
        }
        if (tblSoftwareHeartbeat.getIp() == null) {
            return AjaxResult.error("ip不能为空");
        }
        if (tblSoftwareHeartbeat.getStatus() == null) {
            return AjaxResult.error("status不能为空");
        }
        if (tblSoftwareHeartbeat.getUploadFlag() == null) {
            return AjaxResult.error("uploadFlag不能为空");
        }
        if (tblSoftwareHeartbeat.getDownloadFlag() == null) {
            return AjaxResult.error("downloadFlag不能为空");
        }
        iSoftwareInfoService.heartbeat(tblSoftwareHeartbeat);
        return AjaxResult.success();
    }
}
