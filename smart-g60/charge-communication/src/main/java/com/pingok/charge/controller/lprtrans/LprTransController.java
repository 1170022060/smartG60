package com.pingok.charge.controller.lprtrans;

import com.pingok.charge.domain.lprtrans.TblEnLprTrans;
import com.pingok.charge.domain.lprtrans.TblExLprTrans;
import com.pingok.charge.service.lprtrans.ILprTransService;
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
 * 车道牌识 信息操作处理
 *
 * @author ruoyi
 */
@RestController
@Slf4j
@RequestMapping("/lprTrans")
public class LprTransController extends BaseController {
    @Autowired
    private ILprTransService lprTransService;

    @PostMapping("/en")
    public AjaxResult updateEn(@Validated @RequestBody TblEnLprTrans tblEnLprTrans) {
        log.info("tblEnLprTrans--update----请求参数-----" + tblEnLprTrans);
        lprTransService.addEn(tblEnLprTrans);
        lprTransService.updateEn(tblEnLprTrans);
        return AjaxResult.success();
    }

    @PostMapping("/ex")
    public AjaxResult updateEx(@Validated @RequestBody TblExLprTrans tblExLprTrans) {
        log.info("tblExLprTrans--update----请求参数-----" + tblExLprTrans);
        lprTransService.addEx(tblExLprTrans);
        lprTransService.updateEx(tblExLprTrans);
        return AjaxResult.success();
    }
}
