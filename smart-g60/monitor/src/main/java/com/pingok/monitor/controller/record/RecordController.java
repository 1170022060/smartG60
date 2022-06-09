package com.pingok.monitor.controller.record;

import com.pingok.monitor.domain.vo.Record;
import com.pingok.monitor.service.record.IRecordService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流量 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/record")
public class RecordController extends BaseController {

    @Autowired
    private IRecordService iRecordService;

    @Log(title = "监控大屏服务", businessType = BusinessType.OTHER)
    @GetMapping
    public AjaxResult getRecord(@RequestParam String startTime, @RequestParam String endTime) {
        Record record = new Record();
        record.setGantryRecord(iRecordService.getGantryRecord(startTime, endTime));
        record.setSectionRecord(iRecordService.getSectionRecord(startTime, endTime));
        return AjaxResult.success(record);
    }
}
