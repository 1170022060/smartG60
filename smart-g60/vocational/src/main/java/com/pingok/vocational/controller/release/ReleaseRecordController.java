package com.pingok.vocational.controller.release;

import com.pingok.vocational.domain.release.TblReleaseRecord;
import com.pingok.vocational.domain.release.vo.ReleasePresetEnum;
import com.pingok.vocational.service.release.IReleaseRecordService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
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
    public AjaxResult add(@RequestBody TblReleaseRecord tblReleaseRecord)
    {
        return AjaxResult.success();
    }

    @RequiresPermissions("vocational:releaseRecord:info")
    @Log(title = "信息发布记录-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "infoType",required = false) Integer infoType,@RequestParam(name = "deviceId",required = false) String deviceId,@RequestParam(name = "deviceName",required = false) String deviceName,@RequestParam(name = "pileNo",required = false) String pileNo, @RequestParam(name = "startTime",required = false) Date startTime, @RequestParam(name = "endTime",required = false) Date endTime)
    {
        startPage();
        List<Map> info = releaseRecordService.selectReleaseRecord(infoType, deviceId, deviceName, pileNo, startTime, endTime);
        return getDataTable(info);
    }
}
