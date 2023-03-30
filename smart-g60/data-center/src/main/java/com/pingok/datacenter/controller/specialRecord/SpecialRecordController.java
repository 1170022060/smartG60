package com.pingok.datacenter.controller.specialRecord;

import com.pingok.datacenter.domain.specialRecord.TblSpecialRecord;
import com.pingok.datacenter.service.specialRecord.ISpecialRecordService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 特情 信息操作处理
 *
 * @author qiumin
 */
@RestController
@Slf4j
@RequestMapping("/specialRecord")
public class SpecialRecordController extends BaseController {

    @Autowired
    private ISpecialRecordService iSpecialRecordService;


    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblSpecialRecord tblSpecialRecord) {
//        log.info("specialRecord--add----请求参数-----" + tblSpecialRecord);
        iSpecialRecordService.add(tblSpecialRecord);
        return AjaxResult.success();
    }

    @RequiresPermissions("dataCenter:specialRecord:handleSpecial")
    @Log(title = "特情管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult handleSpecial(@RequestParam Long id, @RequestParam Integer optType, @RequestParam String message, @RequestParam String ip, @RequestParam String port) {
        iSpecialRecordService.handleSpecial(id, optType, message,ip,port);
        return AjaxResult.success();
    }
}
