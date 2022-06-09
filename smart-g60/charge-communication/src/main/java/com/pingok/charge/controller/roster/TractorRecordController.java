package com.pingok.charge.controller.roster;

import com.pingok.charge.service.roster.ITractorRecordService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 牵引车信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/tractorRecord")
public class TractorRecordController extends BaseController {

    @Autowired
    private ITractorRecordService iTractorRecordService;

    @GetMapping(value = "/selectByTime")
    public AjaxResult selectByTime(String startTime, String endTime) {
        return AjaxResult.success(iTractorRecordService.selectByTime(startTime, endTime));
    }


}
