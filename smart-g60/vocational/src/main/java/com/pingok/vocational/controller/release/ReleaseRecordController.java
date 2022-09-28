package com.pingok.vocational.controller.release;

import com.pingok.vocational.domain.release.TblReleaseRecord;
import com.pingok.vocational.service.release.IReleaseRecordService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 信息发布记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/releaseRecord")
public class ReleaseRecordController extends BaseController {

    @Autowired
    private IReleaseRecordService releaseRecordService;

    @PostMapping()
    public AjaxResult add(@RequestBody TblReleaseRecord tblReleaseRecord) {
        return AjaxResult.success();
    }


    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "deviceId", required = false) String deviceId, @RequestParam(name = "deviceName", required = false) String deviceName, @RequestParam(name = "pileNo", required = false) String pileNo, @RequestParam(name = "startTime", required = false) String startTime, @RequestParam(name = "endTime", required = false) String endTime) {
        startPage();
        List<Map> info = releaseRecordService.selectReleaseRecord(deviceId, deviceName, pileNo, startTime, endTime);
        return getDataTable(info);
    }
}
