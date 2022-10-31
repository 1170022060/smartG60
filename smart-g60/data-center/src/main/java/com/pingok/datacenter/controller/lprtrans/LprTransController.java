package com.pingok.datacenter.controller.lprtrans;

import com.pingok.datacenter.domain.lprtrans.TblEnLprTrans;
import com.pingok.datacenter.domain.lprtrans.TblExLprTrans;
import com.pingok.datacenter.domain.lprtrans.vo.EnLprInfo;
import com.pingok.datacenter.domain.lprtrans.vo.ExLprInfo;
import com.pingok.datacenter.service.lprtrans.ILprTransService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 车道牌识流水入库
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/lprTrans")
public class LprTransController extends BaseController {

    @Autowired
    private ILprTransService lprTransService;

    @Transactional
    @PostMapping("/en")
    public AjaxResult en(@Validated @RequestBody TblEnLprTrans tblEnLprTrans)
    {
        EnLprInfo enLprInfo =new EnLprInfo();
        enLprInfo.setInsertEnLpr(lprTransService.insertEnLprTrans(tblEnLprTrans));
        enLprInfo.setInsertEnLprSummary(lprTransService.insertEnLprSummary(tblEnLprTrans));
        return AjaxResult.success();
    }

    @Transactional
    @PostMapping("/ex")
    public AjaxResult ex(@Validated @RequestBody TblExLprTrans tblExLprTrans)
    {
        ExLprInfo exLprInfo =new ExLprInfo();
        exLprInfo.setInsertExLpr(lprTransService.insertExLprTrans(tblExLprTrans));
        exLprInfo.setInsertExLprSummary(lprTransService.insertExLprSummary(tblExLprTrans));
        return AjaxResult.success();
    }

}
