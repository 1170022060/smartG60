package com.pingok.external.controller.amap;

import com.pingok.external.domain.amap.TblAutoNaviMapRecord;
import com.pingok.external.service.amap.IAutoNaviMapService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 高德地图 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/amap")
public class AutoNaviMapController extends BaseController {

    @Autowired
    private IAutoNaviMapService iAutoNaviMapService;

    @GetMapping()
    public AjaxResult callback(@RequestParam String sourceid, @RequestParam Long id, @RequestParam Integer status) {
        iAutoNaviMapService.callback(sourceid, id, status);
        return AjaxResult.success();
    }

    @PostMapping()
    @Log(title = "高德地图", businessType = BusinessType.OTHER)
    public AjaxResult eventPublish(@RequestBody TblAutoNaviMapRecord tblAutoNaviMapRecord) {
        iAutoNaviMapService.eventPublish(tblAutoNaviMapRecord);
        return AjaxResult.success();
    }

    @PutMapping()
    @Log(title = "高德地图", businessType = BusinessType.OTHER)
    public AjaxResult eventRelieve(@RequestParam Long id) {
        iAutoNaviMapService.eventRelieve(id);
        return AjaxResult.success();
    }
}
