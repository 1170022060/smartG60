package com.pingok.external.controller.maintain;

import com.pingok.external.domain.maintain.vo.DiseaseDataVo;
import com.pingok.external.service.maintain.IMaintainService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 养护一张网 信息操作处理
 *
 * @author xia
 */
@RestController
@RequestMapping("/maintain")
public class MaintainController extends BaseController {

    @Autowired
    private IMaintainService iMaintainService;

    @RequiresPermissions("external-system:maintain:push")
    @Log(title = "养护一张网病害数据推送", businessType = BusinessType.UPDATE)
    @PostMapping("/push")
    public AjaxResult push(@Validated @RequestBody DiseaseDataVo diseaseDataVo) {
         iMaintainService.push(diseaseDataVo);
        return AjaxResult.success();
    }

    @RequiresPermissions("external-system:maintain:status")
    @Log(title = "养护一张网病害状态推送", businessType = BusinessType.UPDATE)
    @GetMapping("/status")
    public AjaxResult status(@RequestParam(name = "id") Long id,@RequestParam(name = "status") String status,@RequestParam(name = "note",required = false) String note) {
        iMaintainService.addDiseaseProc(id,status,note);
        return AjaxResult.success();
    }

    @RequiresPermissions("external-system:maintain:pic")
    @Log(title = "养护一张网病害图片推送", businessType = BusinessType.UPDATE)
    @GetMapping("/pic")
    public AjaxResult pic(@RequestParam(name = "id") Long id,@RequestParam(name = "picType") String picType,@RequestParam(name = "fileType") String fileType) {
        iMaintainService.addDiseasePic(id,picType,fileType);
        return AjaxResult.success();
    }

    @RequiresPermissions("external-system:maintain:back")
    @Log(title = "取退单工单号列表", businessType = BusinessType.OTHER)
    @GetMapping("/back")
    public AjaxResult back() {
        return AjaxResult.success(iMaintainService.getBackOrderNums());
    }
}
