package com.pingok.charge.controller.trans;

import com.pingok.charge.domain.trans.vo.EnTransEnum;
import com.pingok.charge.domain.trans.vo.ExTransEnum;
import com.pingok.charge.service.trans.ITransService;
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
 * 车道流水 信息操作处理
 *
 * @author ruoyi
 */
@RestController
@Slf4j
@RequestMapping("/trans")
public class TransController extends BaseController {
    @Autowired
    private ITransService transService;

    @PostMapping("/en")
    public AjaxResult updateEn(@Validated @RequestBody EnTransEnum enTransEnum) {
        log.info("enTransEnum--update----请求参数-----" + enTransEnum);
        transService.addEn(enTransEnum);
        transService.updateEn(enTransEnum);
        return AjaxResult.success();
    }

    @PostMapping("/ex")
    public AjaxResult updateEx(@Validated @RequestBody ExTransEnum exTransEnum) {
        log.info("exTransEnum--update----请求参数-----" + exTransEnum);
        transService.addEx(exTransEnum);
        transService.updateEx(exTransEnum);
        return AjaxResult.success();
    }
}
