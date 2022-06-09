package com.pingok.charge.controller.specialRecord;

import com.pingok.charge.domain.specialRecord.TblSpecialRecord;
import com.pingok.charge.service.specialRecord.ISpecialRecordService;
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
    public AjaxResult update(@Validated @RequestBody TblSpecialRecord tblSpecialRecord) {
        log.info("specialRecord--update----请求参数-----" + tblSpecialRecord);
        iSpecialRecordService.add(tblSpecialRecord);
        iSpecialRecordService.update(tblSpecialRecord);
        return AjaxResult.success();
    }

}
