package com.pingok.charge.controller.lane.optdeal;

import com.pingok.charge.domain.lane.optdeal.TblOptInfo;
import com.pingok.charge.service.lane.optdeal.IOptDealService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author
 * @time 2022/5/30 18:08
 */
@RestController
@RequestMapping("/optdeal")
public class OptDealController extends BaseController {

    @Autowired
    private IOptDealService iOptDealService;

    @GetMapping("/query")
    public AjaxResult query(String startTime, String endTime, String belongStation)
    {
        return AjaxResult.success(iOptDealService.query(startTime, endTime, belongStation));
    }
}
