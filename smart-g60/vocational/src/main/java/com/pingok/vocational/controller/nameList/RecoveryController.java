package com.pingok.vocational.controller.nameList;

import com.pingok.vocational.service.nameList.IRecoveryService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
@RestController
@RequestMapping("/recovery")
public class RecoveryController extends BaseController {

    @Autowired
    private IRecoveryService iRecoveryService;

    @GetMapping
    public TableDataInfo list(String sationName,String version){
        startPage();
        List<Map> info = iRecoveryService.getRecoveryList(sationName,version);
        return getDataTable(info);
    }

    @GetMapping("findById")
    public AjaxResult findById(@RequestParam Long id){
        return AjaxResult.success(iRecoveryService.findById(id));
    }
}
