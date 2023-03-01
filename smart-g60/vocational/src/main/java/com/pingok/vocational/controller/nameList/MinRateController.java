package com.pingok.vocational.controller.nameList;

import com.pingok.vocational.domain.nameList.TblRate;
import com.pingok.vocational.domain.nameList.TblRateProv;
import com.pingok.vocational.service.nameList.IRateService;
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
@RequestMapping("/minRate")
public class MinRateController extends BaseController {

    @Autowired
    private IRateService iRateService;

    @GetMapping
    public TableDataInfo list(String stationName,String version){
        startPage();
        List<Map> info = iRateService.getRateList(stationName,version);
        return getDataTable(info);
    }

    @GetMapping("/findRateById")
    public TableDataInfo findRateById(@RequestParam Long id){
        startPage();
        List<TblRate> info = iRateService.findRateById(id);
        return getDataTable(info);
    }

    @GetMapping("/findRateProvById")
    public TableDataInfo findRateProvById(@RequestParam Long id){
        startPage();
        List<TblRateProv> info = iRateService.findRateProvById(id);
        return getDataTable(info);
    }
}
