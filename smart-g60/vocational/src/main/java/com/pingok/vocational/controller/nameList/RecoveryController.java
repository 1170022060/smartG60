package com.pingok.vocational.controller.nameList;

import com.pingok.vocational.domain.nameList.TblAuditData;
import com.pingok.vocational.service.nameList.IRecoveryService;
import com.ruoyi.common.core.web.controller.BaseController;
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
@RequestMapping("/recoveryList")
public class RecoveryController extends BaseController {

    @Autowired
    private IRecoveryService iRecoveryService;

    @GetMapping
    public TableDataInfo list(String stationName,String version){
        startPage();
        List<Map> info = iRecoveryService.getRecoveryList(stationName,version);
        return getDataTable(info);
    }

    @GetMapping("/findById")
    public TableDataInfo findById(@RequestParam Long id){
        startPage();
        List<TblAuditData> info = iRecoveryService.findById(id);
        return getDataTable(info);
    }
}
