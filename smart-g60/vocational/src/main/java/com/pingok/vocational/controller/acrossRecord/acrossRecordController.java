package com.pingok.vocational.controller.acrossRecord;

import com.pingok.vocational.service.acrossRecord.IAcrossRecordService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/acrossRecord")
public class acrossRecordController extends BaseController {

    @Autowired
    private IAcrossRecordService iAcrossRecordService;

    @GetMapping
    public TableDataInfo getAcrossRecord(@RequestParam(name = "vehPlate",required = false) String vehPlate,
                                         @RequestParam(name = "startTime") Date startTime,
                                         @RequestParam(name = "endTime") Date endTime){
        startPage();
        List<Map> info = iAcrossRecordService.selectAcrossRecord(vehPlate,startTime,endTime);
        return getDataTable(info);
    }

    @GetMapping("/getRecord")
    public AjaxResult getRecord(@RequestParam(name = "time") Date time,@RequestParam(name = "passId")String passId){
        return AjaxResult.success(iAcrossRecordService.selectRecord(time,passId));
    }
}
