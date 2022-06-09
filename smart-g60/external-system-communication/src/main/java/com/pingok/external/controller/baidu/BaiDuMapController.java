package com.pingok.external.controller.baidu;

import com.pingok.external.domain.baidu.TblBaiDuMapRecord;
import com.pingok.external.service.baidu.IBaiDuMapService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 百度地图 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/baidu")
public class BaiDuMapController extends BaseController {

    @Autowired
    private IBaiDuMapService iBaiDuMapService;


    @PostMapping()
    @Log(title = "百度地图", businessType = BusinessType.OTHER)
    public AjaxResult eventPublish(@RequestBody TblBaiDuMapRecord tblBaiDuMapRecord) {
        iBaiDuMapService.eventPublish(tblBaiDuMapRecord);
        return AjaxResult.success();
    }

    @PutMapping()
    @Log(title = "百度地图", businessType = BusinessType.OTHER)
    public AjaxResult eventRelieve(@RequestParam Long id) {
        iBaiDuMapService.eventRelieve(id);
        return AjaxResult.success();
    }
}
